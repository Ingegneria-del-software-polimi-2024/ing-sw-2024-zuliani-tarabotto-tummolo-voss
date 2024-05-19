package Client.UI.TUI;

import Client.UI.UI;
import Client.View.ViewAPI;
import SharedWebInterfaces.Messages.MessagesToLobby.JoinGameMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.RequestAvailableGames;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;

import javax.swing.text.View;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class TUI implements UI {

    private ViewAPI view;
    private DispositionPrinter dispositionPrinter;
    private LoginPrinter loginPrinter;
    private Scanner sc = new Scanner(System.in);
    private HandPrinter handPrinter;
    private ObjectivesPrinter objectivesPrinter;
    private DrawCardPrinter drawCardPrinter;
    private CardBuilder cb;
    private final int color = 226;

    public TUI(ViewAPI view) {

        this.view = view;
        cb = new CardBuilder();
        dispositionPrinter = new DispositionPrinter(cb);
        loginPrinter = new LoginPrinter();
        handPrinter = new HandPrinter();
        objectivesPrinter = new ObjectivesPrinter();
        drawCardPrinter = new DrawCardPrinter(cb);

    }
///////////////////////////////////////<Lobby>//////////////////////////////////////////////////////////////////////////
    public void chooseConnection(){
        //TODO: ERASE SCREEN
        loginPrinter.print();
        System.out.print(ansi().fg(color).a("~> Welcome to Codex Naturalis, insert the techonolgy of connection (RMI/Socket): \n").reset());
        String connectionType;
        do{
            connectionType = sc.nextLine();
            if(!(connectionType.equalsIgnoreCase("RMI") || connectionType.equalsIgnoreCase("SOCKET")))
                System.out.print(ansi().fg(color).a("~> Insert a correct value \"RMI\" or \"Socket\":\n").reset());
        }while (!(connectionType.equalsIgnoreCase("RMI") || connectionType.equalsIgnoreCase("SOCKET")));

        String host;
        System.out.print(ansi().fg(color).a("~> Insert the host ip address: \n").reset());
        do{
            host = sc.nextLine();
            if (!validIP(host))
                System.out.print(ansi().fg(color).a("~> Insert a valid ip address (xxx.xxx.xxx.xxx): \n").reset());
        }while(!validIP(host));

        int port;
        System.out.print(ansi().fg(color).a("~> Insert the host port: \n").reset());
        do{
            port = Integer.parseInt(sc.nextLine());
            if (!validPort(port))
                System.out.print(ansi().fg(color).a("~> Insert a valid port: \n").reset());
        }while(!validPort(port));

        int localPort;
        System.out.print(ansi().fg(color).a("~> Insert the local port: \n").reset());
        do{
            localPort = Integer.parseInt(sc.nextLine());
            if (!validPort(localPort))
                System.out.print(ansi().fg(color).a("~> Insert a valid port: \n").reset());
        }while(!validPort(localPort));
        //debug
        System.out.println("Starting connection");
        view.startConnection(connectionType, host, port, localPort);
    }

    private boolean validIP(String ip){
        //todo control if the ip is valid
        return true;
    }
    private boolean validPort(int port){
        return(1024<=port && port <=49151);
    }

    public void askNickname(){
        System.out.print(ansi().fg(color).a("~> Insert your nickname: \n").reset());
        String nickname = sc.nextLine();
        view.setPlayerId(nickname);

    }
    public void displayAvailableGames(ArrayList<String> listOfGames){
        String game;


        if(listOfGames != null && !listOfGames.isEmpty()){
            System.out.print(ansi().fg(color).a("~> Available games:\n").reset());
            for(String g : listOfGames)
                System.out.print(ansi().fg(color).a("   "+g+"\n").reset());
            System.out.print(ansi().fg(color).a("~> Insert the name of the game you want to join or a new name if you want to create it, write -r to refresh the available games: \n").reset());
        }else
            System.out.print(ansi().fg(color).a("~> There are no available games, please create a new game by typing its name or write -r to refresh the available games: \n").reset());

        game = sc.nextLine();
        if(game.equals("-r")) {
            view.requestAvailableGames();
            //            try {
//                view.requestAvailableGames();
//            }catch (RemoteException e) {
//                throw new RuntimeException(e);
//            }
        }else{
            int nPlayers = 0;
            if(listOfGames == null || !listOfGames.contains(game)) {
                System.out.print(ansi().fg(color).a("~> You want to create a new game!\n~> Insert the number of players: ").reset());
                String players;
                boolean flag = false;
                do {
                    players = sc.nextLine();
                    try {
                        nPlayers = Math.abs(Integer.parseInt(players));
                    } catch (Exception e) {
                        flag = true;
                    }
                } while (flag);
            }
            view.joinGame(game, nPlayers);
        }

    }
    public void joinedGame(String id){
        System.out.print(ansi().fg(color).a("~> Correctly joined the game "+id+"\n").reset());
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void displayInitialization() {
        //TODO: ERASE SCREEN
//        loginPrinter.print();
        boolean enterPressed = false;

        // Continuously check if Enter is pressed until it's pressed
        while (!enterPressed) {
            System.out.print(ansi().fg(color).a("~> Press Enter to continue...\n").reset());

            // Wait for the player to press Enter
            String input = sc.nextLine();

            // Check if the input is empty, meaning Enter was pressed
            if (input.isEmpty()) {
                enterPressed = true;
                //System.out.println("Enter key was pressed.");
                view.readyToPlay();
            } else {
                System.out.print(ansi().fg(color).a(" ~>Enter key was not pressed.\n").reset());
            }
        }
        System.out.print(ansi().fg(color).a("~> Waiting for other players to be ready\n").reset());

    }

    @Override
    public void displayStarterCardSelection() {
        //TODO: ERASE SCREEN
        System.out.print(ansi().fg(color).a("~> This is your StarterCard, choose a face side: (true/false)\n").reset());
        handPrinter.printStarterCard(view.getStarterCard());
        boolean faceSide = sc.nextBoolean();
        view.getStarterCard().setFaceSide(faceSide);
        view.playStarterCard();
    }

    @Override
    public void displayObjectiveSelection() {
        //TODO: ERASE SCREEN
        System.out.print(ansi().fg(color).a("~> This is your hand:\n").reset());
        handPrinter.print(view.getHand());

        System.out.print(ansi().fg(color).a("~> this are the two commonObjectives:\n").reset());
        objectivesPrinter.printCommonObjectives(view.getCommonObjectives().get(0),view.getCommonObjectives().get(1));

        System.out.print(ansi().fg(color).a("~> choose your secretObjective: (0/1)\n").reset());
        //we use the same function also to print the two objectives the player has to choose from
        objectivesPrinter.printCommonObjectives(view.getChooseSecretObjectives().get(0), view.getChooseSecretObjectives().get(1));

        view.setSecretObjective(view.getChooseSecretObjectives().get(sc.nextInt()));

    }

    @Override
    public void displayPlacingCard() {
        //if it's myturn then i need to play a card, else i just wait
        //TODO: ERASE SCREEN
        if(view.getMyTurn()){
            dispositionPrinter.print(view.getDisposition(), view.getAvailablePlaces());
            ultimatePrint(view);
            System.out.print(ansi().fg(color).a("~> Choose a card to place from your hand. You can place card: ").reset());
            String line = new String();
            if(view.getCanBePlaced()[0]) line = "1, ";
            if(view.getCanBePlaced()[1]) line += "2, ";
            if(view.getCanBePlaced()[2]) line += "3";
            System.out.print(ansi().fg(color).a("(" + line + ")\n").reset());
            PlayableCard c = view.getHand().get(sc.nextInt() - 1);

            System.out.print(ansi().fg(color).a("~> Select a face side for the card: (true/false)\n").reset());
            boolean faceSide = sc.nextBoolean();
            c.setFaceSide(faceSide);

            System.out.print(ansi().fg(color).a("~> Choose one of the available coordinates to place the card: (x/y)\n").reset());

            int x = sc.nextInt();
            int y = sc .nextInt();

            view.playCard(c, x, y);
        }else{
            System.out.print(ansi().fg(color).a("~> " + view.getTurnPlayer() + " is placing a card\n").reset());
            dispositionPrinter.print(view.getDisposition());
            ultimatePrint(view);
        }
    }

    @Override
    public void displayCardDrawing() {
        //TODO: ERASE SCREEN

        if(view.getMyTurn()){
            drawCardPrinter.print(view.getGoldDeck().get(0), view.getResourceDeck().get(0), view.getOpenGold(), view.getOpenResource());
            System.out.print(ansi().fg(color).a("~> Draw a card: (1/2/3/4/5/6)\n").reset());
            view.drawCard(sc.nextInt());
        }else{
            System.out.print(ansi().fg(color).a("~> " + view.getTurnPlayer() + " is drawing a card\n").reset());
            dispositionPrinter.print(view.getDisposition());
            ultimatePrint(view);
        }

    }

    @Override
    public void displayCalculateObjectives() {
        //TODO: ERASE SCREEN

    }



    private void printPlayerInformation(){
        System.out.print(ansi().fg(color).a("POINTS: ").reset());
        System.out.print(ansi().a(view.getPoints().get(view.getPlayerId())+ "\n"));

        System.out.print(ansi().fg(color).a("ELEMENTS: \n").reset());
        for(Element e : view.getAvailableElements().keySet()){
            System.out.print(ansi().a("       " + e.getStringValue() + "->" + view.getAvailableElements().get(e)).reset());
        }

        System.out.println("\n");
        System.out.print(ansi().fg(color).a("ARTIFACTS: \n").reset());
        for(Artifact a : view.getAvailableArtifacts().keySet()){
            System.out.print(ansi().a("       " + a.getStringValue() + "->" + view.getAvailableArtifacts().get(a)).reset());
        }
        System.out.println("\n");
    }



    private List<String> getInfoField(){
        List<String> infoField = new ArrayList<>();

        infoField.add("\u2554\u2550\u2550\u2550"+ ansi().fg(color).bold().a(" RESOURCES ").reset() + "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557");
        infoField.add("\u2551                                                                   \u2551");
        infoField.add("\u2551   " + ansi().fg(color).a("POINTS:         ").reset() + view.getPoints().get(view.getPlayerId()) + "                                               \u2551");
        infoField.add("\u2551   " + ansi().fg(color).a("ELEMENTS:").reset() + ansi().a("       " + Element.mushrooms.getStringValue() + "->" + view.getAvailableElements().get(Element.mushrooms)).reset()  + ansi().a("       " + Element.animals.getStringValue() + "->" + view.getAvailableElements().get(Element.animals)).reset() + ansi().a("       " + Element.vegetals.getStringValue() + "->" + view.getAvailableElements().get(Element.vegetals)).reset() + ansi().a("       " + Element.insects.getStringValue() + "->" + view.getAvailableElements().get(Element.insects)).reset() + "           \u2551");
        infoField.add("\u2551   " + ansi().fg(color).a("ARTIFACTS:").reset() + ansi().a("      " + Artifact.feather.getStringValue() + "->" + view.getAvailableArtifacts().get(Artifact.feather)).reset() + ansi().a("       " + Artifact.paper.getStringValue() + "->" + view.getAvailableArtifacts().get(Artifact.paper)).reset() + ansi().a("       " + Artifact.ink.getStringValue() + "->" + view.getAvailableArtifacts().get(Artifact.ink)).reset() + "                      \u2551");
        infoField.add("\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D");

        return infoField;
    }

    private String buildStat(int stat){
        String line = new String();
        if(stat < 10) return line = stat + " ";
        else return line = String.valueOf(stat);
    }

    private void ultimatePrint(ViewAPI view){
        List<String> handField = handPrinter.getHandField(view.getHand());
        List<String> objFiel = objectivesPrinter.getObjField(view.getCommonObjectives().get(0), view.getCommonObjectives().get(1), view.getSecretObjective());
        List<String> infoField = getInfoField();
        for(int i = 0; i < 12; i++){
            System.out.println(handField.get(i) + objFiel.get(i));
        }
        for(int i = 12; i < 18 ; i++){
            System.out.println(handField.get(i) + infoField.get(i - 12));
        }
    }


}
