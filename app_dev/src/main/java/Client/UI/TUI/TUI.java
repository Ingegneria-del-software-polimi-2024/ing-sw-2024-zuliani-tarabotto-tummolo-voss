package Client.UI.TUI;

import Chat.MessagesFromClient.ChatMessage;
import Client.UI.TUI.Commands.*;
import Client.UI.UI;
import Client.View.ViewAPI;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.Coordinates;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fusesource.jansi.Ansi.ansi;


/**
 * The type Tui.
 * A class to interface with the functionalities of the TUI
 */
public class TUI implements UI {

    /**
     * The view
     */
    private ViewAPI view;
    /**
     * The printer of the disposition
     */
    private DispositionPrinter dispositionPrinter;
    /**
     * The printer of the login
     */
    private LoginPrinter loginPrinter;
    /**
     * The input scanner
     */
    private Scanner sc;
    /**
     * The printer of the hand
     */
    private HandPrinter handPrinter;
    /**
     * The printer of the objectives
     */
    private ObjectivesPrinter objectivesPrinter;
    /**
     * The printer of the drawing card phase
     */
    private DrawCardPrinter drawCardPrinter;
    /**
     * The builder of the cards
     */
    private CardBuilder cb;
    /**
     * The colour to print with
     */
    private final int color = 226;
    /**
     * The input string, as the player inserts values in input, they are all put in this variable
     */
    private String input;
    /**
     * A boolean that takes the value of true when a new input can be read
     */
    private volatile boolean inputPresent = false;
    /**
     * A lock regulating the access to the input
     */
    private final Object inputLock;
    /**
     * A hashmap mapping all the possiblecommands to their name
     */
    private final HashMap<String, Command> commandMap;
    /**
     * A runnable containing the command printing the last object printed
     */
    private Runnable rePrint;
    /**
     * A boolean that is true when enter is pressed
     */
    boolean enterPressed = false;
    /**
     * The nickname of the player
     */
    private String nickname;
    /**
     * The thread that reads from input
     */
    private boolean runningThread;
    /**
     * A boolean that is true when the player is in the waiting room, waiting to join a game
     */
    private boolean inWaitingRoom = false;
    /**
     * A boolean that is true when the chat is opened
     */
    private boolean chatOpened = false;
    /**
     * The name of the game
     */
    private String game;

    /**
     * Instantiates a new Tui.
     *
     * @param view the view
     */
    public TUI(ViewAPI view){

        this.view = view;
        cb = new CardBuilder();
        dispositionPrinter = new DispositionPrinter(cb);
        loginPrinter = new LoginPrinter();
        handPrinter = new HandPrinter(cb);
        objectivesPrinter = new ObjectivesPrinter();
        drawCardPrinter = new DrawCardPrinter(cb);
        inputLock = new Object();

        this.commandMap = new HashMap<>();
        commandMap.put("--help", new HelpCommand());
        commandMap.put("--disp", new DispositionCommand(view));
        commandMap.put("--quit", new EndGameCommand(view, this));
        commandMap.put("--chat", new ChatCommand(view, this));
        commandMap.put("--whoami", new WhoAmICommand(this));

        sc = new Scanner(System.in);
    }
///////////////////////////////////////<Lobby>//////////////////////////////////////////////////////////////////////////
    public void firstWelcome(){
        loginPrinter.print();
        System.out.println("~> Welcome to Codex Naturalis, follow the instructions to join a game.\n   Once in the game write --help for a guide, --quit to exit");
    }
    public void chooseConnection(){
        System.out.print(ansi().fg(color).a("~> Insert the techonolgy of connection (RMI/Socket): \n").reset());
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
                System.out.print(ansi().fg(color).a("~> Insert a valid ip address (xxxx.xxxx.xxxx.xxxx): \n").reset());
        }while(!validIP(host));

//        int port;
//        System.out.print(ansi().fg(color).a("~> Insert the host port: \n").reset());
//        do{
//            port = Integer.parseInt(sc.nextLine());
//            if (!validPort(port))
//                System.out.print(ansi().fg(color).a("~> Insert a valid port: \n").reset());
//        }while(!validPort(port));

//        int localPort;
//        System.out.print(ansi().fg(color).a("~> Insert the local port: \n").reset());
//        do{
//            localPort = Integer.parseInt(sc.nextLine());
//            if (!validPort(localPort))
//                System.out.print(ansi().fg(color).a("~> Insert a valid port: \n").reset());
//        }while(!validPort(localPort));

        try {
            view.startConnection(connectionType, host);
        } catch (StartConnectionFailedException e) {
            System.out.print(ansi().fg(color).a("~> An error during the connection occurred\n   Check your internet connection and retry\n").reset());
            chooseConnection();
        }
    }

    /**
     * Validates the IP address
     * @param ip the string to validate
     * @return true when the ip has a right format
     */
    private boolean validIP(String ip){
        if(ip.toLowerCase().equals("localhost"))
            return true;
        String desiredFormat = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        Pattern pattern = Pattern.compile(desiredFormat);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    /**
     * Validates the Port
     * @param port the integer to validate
     * @return true when the port has a right format
     */
    private boolean validPort(int port){
        return(1024<=port && port <=49151);
    }

    public void askNickname(){
        System.out.print(ansi().fg(color).a("~> Insert your nickname: \n").reset());
        nickname = sc.nextLine();
        view.setPlayerID(nickname);

    }
    public void nickNameAlreadyInUse(){
        System.out.print(ansi().fg(color).a("~> This nickname is already in use or it is empty, please change nickname\n").reset());
        askNickname();
    }

    @Override
    public void cantPlaceACard(PlayableCard card, Coordinates coord) {
        System.out.print(ansi().fg(color).a("~> Can't place the card in the position ("+coord.getX()+";"+coord.getY()+")"+"\n   Choose a different card\n").reset());
        displayPlacingCard(false);
    }

    @Override
    public void cantDrawCard(int source) {
        System.out.print(ansi().fg(color).a("~> Can't draw the card from the selected source\n").reset());
        displayCardDrawing(false);
    }

    @Override
    public void cantCreateRoom() {
        System.out.print(ansi().fg(color).a("~> Can't create the room, control the number of players and the name of the room\n").reset());
        System.out.println("   Players must be between 2 and 4 and the room name must not be empty nor it must be already existing, try typing a different room name\n");
        view.displayAvailableGames();
    }

    @Override
    public void cantJoinRoom() {
        System.out.print(ansi().fg(color).a("~> Can't join the room, too many players are present\n").reset());
        view.displayAvailableGames();
    }

    public void displayAvailableGames(ArrayList<String> listOfGames){

        if(listOfGames != null && !listOfGames.isEmpty()){
            System.out.print(ansi().fg(color).a("~> Available games:\n").reset());
            for(String g : listOfGames)
                System.out.print(ansi().fg(color).a("   "+g+"\n").reset());
            System.out.print(ansi().fg(color).a("~> Insert the name of the game you want to join or a new name if you want to create it, write -r to refresh the available games: \n").reset());
        }else
            System.out.print(ansi().fg(color).a("~> There are no available games, please create a new game by typing its name or write --r to refresh the available games: \n").reset());
        //sc.reset();
        game = sc.nextLine();
        if(game.equals("--r")) {
            view.requestAvailableGames();
        }else{
            int nPlayers = 0;
            if(listOfGames == null || !listOfGames.contains(game)) {
                String players;
                boolean flag = false;
                System.out.print(ansi().fg(color).a("~> You want to create a new game!\n~> Insert the number of players (insert --back to go back): ").reset());
                do {
                    players = sc.nextLine();
                    if(players.equals("--back")){
                        view.requestAvailableGames();
                        return;
                    }
                    try {
                        nPlayers = Math.abs(Integer.parseInt(players));
                        flag = false;
                    } catch (Exception e) {
                        flag = true;
                    }
                    if(flag||nPlayers<2 || nPlayers>4){
                        System.out.print(ansi().fg(color).a("~> The game must contain at least 2 players and maximum 4 players\n~> Insert the number of players: ").reset());
                        flag = true;
                    }
                } while (flag);
            }
            view.joinGame(game, nPlayers);
        }

    }
    public void joinedGame(String id){
        inWaitingRoom = true;
        view.startUI();

        rePrint = () -> {
            clear();
            loginPrinter.print();
            System.out.print(ansi().fg(color).a("~> Correctly joined the game "+id+"\n   waiting for other players...\n").reset());
        };
        rePrint.run();

    }

    public void returnToLobby(){
        view.stopUI();
        displayReturnToLobby();
        clear();
        view.welcome();
        view.requestAvailableGames();
    }

    public void returnToChooseGame(){
        inputPresent = false;
        view.stopUI();
        clear();
        loginPrinter.print();
    }


    /**
     * Displays the "return to lobby" screen asking to press enter.
     */
    public void displayReturnToLobby(){
        String in = null;
        sc = new Scanner(System.in);
        do {
            if(input == null){
                clear();
                System.out.print(ansi().fg(color).a("~> Press enter to return to the lobby\n").reset());
                in = sc.nextLine();
            } else{
                synchronized (inputLock){
                    in = parseString();
                }

            }
        }while(in == null);
        inputPresent = false;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void displayInitialization() {
        inWaitingRoom = false;
        clear();
        loginPrinter.print();

        enterPressed = false;
        // Continuously check if Enter is pressed until it's pressed
        while (!enterPressed) {
            System.out.print(ansi().fg(color).a("~> Press Enter to continue...\n").reset());

            synchronized (inputLock) {
                while (!inputPresent) {
                    try {
                       inputLock.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }

                // Wait for the player to press Enter
                String in = input;
                // Check if the input is empty, meaning Enter was pressed
                if (in.isEmpty()) {
                    enterPressed = true;
                    //System.out.println("Enter key was pressed.");
                    view.readyToPlay();
                } else {
                    System.out.print(ansi().fg(color).a(" ~> Enter key was not pressed.\n").reset());
                }
                inputPresent = false;
                inputLock.notifyAll();
            }
        }
        rePrint = () -> {
            clear();
            loginPrinter.print();
            System.out.print(ansi().fg(color).a("~> Waiting for other players to be ready\n").reset());
        };
        rePrint.run();

    }

    @Override
    public void displayStarterCardSelection() {

        rePrint = () -> {
            clear();
            handPrinter.printStarterCard(view.getStarterCard());
            System.out.print(ansi().fg(color).a("~> This is your StarterCard, choose a face side: (front/back)\n").reset());
        };
        if (!chatOpened)
            rePrint.run();


        //we read from input the line and parse it as boolean
        synchronized (inputLock){
            Boolean faceSide = parseBoolean();

            if(faceSide == null)
                return;

            view.getStarterCard().setFaceSide(faceSide);
            view.playStarterCard();
            inputPresent = false;
            inputLock.notifyAll();
        }
        printStarterCard();
    }

    public void printStarterCard(){
        rePrint = () -> {
            clear();
            System.out.print(ansi().fg(color).a("~> This is your StarterCard: \n").reset());
            handPrinter.printStarterCard(view.getStarterCard());
        };
        rePrint.run();
    }

    @Override
    public void displayObjectiveSelection() {

        rePrint = () -> {
            clear();
            System.out.print(ansi().fg(color).a("~> This is your hand:\n").reset());
            handPrinter.print(view.getHand());
            System.out.print(ansi().fg(color).a("~> these are the two commonObjectives:\n").reset());
            objectivesPrinter.printCommonObjectives(view.getCommonObjectives().get(0),view.getCommonObjectives().get(1));

            System.out.print(ansi().fg(color).a("~> choose your secretObjective: (1/2)\n").reset());
            //we use the same function also to print the two objectives the player has to choose from
            objectivesPrinter.printCommonObjectives(view.getChooseSecretObjectives().get(0), view.getChooseSecretObjectives().get(1));
        };
        if(!chatOpened)
            rePrint.run();

        synchronized (inputLock){
            Integer secretObj = parseInt();
            if(secretObj  == null)
                return;
            while(!secretObj.equals(1)  && !secretObj.equals(2)){
                System.out.println("~> Please insert 1/2\n");
                secretObj = parseInt();
                if(secretObj == null) {
                    inputLock.notifyAll();
                    return;
                }
            }
            view.setSecretObjective(view.getChooseSecretObjectives().get(secretObj - 1));
            inputPresent = false;
            inputLock.notifyAll();
        }
        printSecretObjective();
    }

    public void printSecretObjective(){
        rePrint = () -> {
            clear();
            System.out.print(ansi().fg(color).a("~> These are your objectives\n").reset());
            objectivesPrinter.printObjectivesBoard(view.getCommonObjectives().get(0), view.getCommonObjectives().get(1), view.getSecretObjective());
        };
        rePrint.run();
    }

    public void displayPlacingCard(){
        displayPlacingCard(true);
    }
    private void displayPlacingCard(boolean clear) {
        Integer index;
        Integer x = 0;
        Integer y = 0;
        Boolean faceSide;
        //if it's my turn then i need to play a card, else i just wait
        if(view.getMyTurn()) {

            // we choose which card we want to place
            rePrint = () -> {
                if(clear)
                    clear();
                dispositionPrinter.print(view.getDisposition(), view.getAvailablePlaces());
                ultimatePrint(view);
                System.out.print(ansi().fg(color).a("~> Choose a card to place from your hand (1/2/3): \n").reset());
            };
            if(!chatOpened)
                rePrint.run();

            synchronized (inputLock){
                index = parseInt();
                if(index == null) {
                    inputLock.notifyAll();
                    return;
                }

                while(index != 1 && index != 2 && index != 3 ){
                    System.out.println("~> Please insert 1/2/3");
                    index = parseInt();
                    if(index == null){
                        inputLock.notifyAll();
                        return;
                    }
                }
                inputPresent = false;
                inputLock.notifyAll();
            }

            //we select the face for the card
            rePrint = () -> {
                clear();
                dispositionPrinter.print(view.getDisposition(), view.getAvailablePlaces());
                ultimatePrint(view);
                System.out.print(ansi().fg(color).a("~> Select a face side for the card: (front/back)\n").reset());
            };
            rePrint.run();

            synchronized (inputLock){
                faceSide = parseBoolean();
                if(faceSide == null){
                    inputLock.notifyAll();
                }
                if(faceSide && !view.getCanBePlaced()[index - 1]){
                    System.out.println("~> Sorry, card " + index + "can't be placed face up due to its placement constraint");
                    faceSide = false;
                }
                inputPresent = false;
                inputLock.notifyAll();
            }


            // we choose the coordinates where we want to place the card
            rePrint = () -> {
                clear();
                dispositionPrinter.print(view.getDisposition(), view.getAvailablePlaces());
                ultimatePrint(view);
                System.out.print(ansi().fg(color).a("~> Choose one of the available coordinates to place the card: (x/y)\n").reset());
            };
            rePrint.run();

            while (!view.checkAvailable(x, y)){
                synchronized (inputLock) {
                    x = parseInt();
                    if(x == null){
                        inputLock.notifyAll();
                        return;
                    }
                    inputPresent = false;
                    inputLock.notifyAll();
                }
                synchronized (inputLock){
                    y = parseInt();
                    if(y == null){
                        inputLock.notifyAll();
                        return;
                    }
                    //view.playCard(c, x, y);
                    if(!view.checkAvailable(x, y)) System.out.println("These coordinates are not available, please choose some valid ones");
                    else view.playCard(view.getHand().get(index - 1), faceSide, x, y);
                    //System.out.println(view.getHand().get(index - 1).getFaceSide());
                    inputPresent = false;
                    inputLock.notifyAll();
                }
            }

        }else{
            rePrint = () -> {
                clear();
                dispositionPrinter.print(view.getDisposition());
                ultimatePrint(view);
                System.out.print(ansi().fg(color).a("~> " + view.getTurnPlayer() + " is placing a card\n").reset());
            };
            rePrint.run();
        }

    }

    public void displayCardDrawing(){
        displayCardDrawing(true);
    }

    /**
     * Displays the interface for drawing cards
     * @param clear when true, the method clears the previously printed interfaces before printing its
     */
    private void displayCardDrawing(boolean clear) {

        if(view.getMyTurn()){

            rePrint= () -> {
                if(clear)
                    clear();
                drawCardPrinter.print(view.getGoldDeck().get(0), view.getResourceDeck().get(0), view.getOpenGold(), view.getOpenResource());
                System.out.print(ansi().fg(color).a("~> Draw a card: (1/2/3/4/5/6)\n").reset());
            };
            rePrint.run();


            synchronized (inputLock){
                Integer cardSource = parseInt();
                if(cardSource == null){
                    inputLock.notifyAll();
                    return;
                }
                while(!view.checkCanDrawFrom(cardSource)){
                    System.out.println("~> This card source is not available, choose a valid one\n");
                    cardSource = parseInt();
                    if(cardSource == null){
                        inputLock.notifyAll();
                        return;
                    }
                }
                view.drawCard(cardSource);
                inputPresent = false;
                inputLock.notifyAll();
            }
        }else{
            rePrint = () -> {
                clear();
                dispositionPrinter.print(view.getDisposition());
                ultimatePrint(view);
                System.out.print(ansi().fg(color).a("~> " + view.getTurnPlayer() + " is drawing a card\n").reset());

            };
        }

    }

    @Override
    public void displayEndGame() {
        clear();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(view.getPoints().entrySet());
        // Step 2: Sort the list with a comparator that compares the values in descending order
        entryList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        System.out.println(ansi().fg(226).a(
                "████████╗██╗  ██╗███████╗    ███████╗███╗   ██╗██████╗\n" +
                "╚══██╔══╝██║  ██║██╔════╝    ██╔════╝████╗  ██║██╔══██╗\n" +
                "   ██║   ███████║█████╗      █████╗  ██╔██╗ ██║██║  ██║\n" +
                "   ██║   ██╔══██║██╔══╝      ██╔══╝  ██║╚██╗██║██║  ██║\n" +
                "   ██║   ██║  ██║███████╗    ███████╗██║ ╚████║██████╔╝\n" +
                "   ╚═╝   ╚═╝  ╚═╝╚══════╝    ╚══════╝╚═╝  ╚═══╝╚═════╝\n"  +
                "                                                      ").reset());

        List<String> winners = view.getWinners();
        if(winners.size() == 1)
            System.out.print(ansi().fg(226).a("~> " + winners.get(0).toUpperCase() + " is the winner !!!\n").reset());
        else {
            System.out.print(ansi().fg(226).a("~> The winners are:\n").reset());
            for (String win : view.getWinners()) {
                System.out.print(ansi().fg(226).a("                   " + win.toUpperCase() + " !!!\n").reset());
            }
        }
        for (Map.Entry<String, Integer> stringIntegerEntry : entryList) {
            System.out.println("-> " + stringIntegerEntry.getKey() + ": " + stringIntegerEntry.getValue() + " points");
        }
        System.out.print(ansi().fg(color).a("~> Press enter to return to the lobby\n").reset());
    }

    private List<String> getInfoField(){
        List<String> infoField = new ArrayList<>();

        infoField.add("\u2554\u2550\u2550\u2550"+ ansi().fg(color).bold().a(" RESOURCES ").reset() + "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557");
        infoField.add("\u2551                                                                   \u2551");
        infoField.add("\u2551   " + ansi().fg(color).a("POINTS:         ").reset() + buildStat(view.getPoints().get(view.getPlayerId())) + "                                              \u2551");
        infoField.add("\u2551   " + ansi().fg(color).a("ELEMENTS:").reset() + ansi().a("       " + Element.mushrooms.getStringValue() + "->" +buildStat(view.getAvailableElements(nickname).get(Element.mushrooms))).reset()  + ansi().a("       " + Element.animals.getStringValue() + "->" + buildStat( view.getAvailableElements(nickname).get(Element.animals))).reset() + ansi().a("       " + Element.vegetals.getStringValue() + "->" + buildStat(view.getAvailableElements(nickname).get(Element.vegetals))).reset() + ansi().a("       " + Element.insects.getStringValue() + "->" + buildStat(view.getAvailableElements(nickname).get(Element.insects))).reset() + "       \u2551");
        infoField.add("\u2551   " + ansi().fg(color).a("ARTIFACTS:").reset() + ansi().a("      " + Artifact.feather.getStringValue() + "->" + buildStat(view.getAvailableArtifacts(nickname).get(Artifact.feather))).reset() + ansi().a("       " + Artifact.paper.getStringValue() + "->" + buildStat(view.getAvailableArtifacts(nickname).get(Artifact.paper))).reset() + ansi().a("       " + Artifact.ink.getStringValue() + "->" + buildStat(view.getAvailableArtifacts(nickname).get(Artifact.ink))).reset() + "                   \u2551");
        infoField.add("\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D");

        return infoField;
    }


    @Override
    public void updateResourcesInUI() {
        //TODO this method is empty! ~Andre
    }

    @Override
    public void returnToStart() {
        clear();
        System.out.print(ansi().fg(color).a("~> An error in the communication with the server occurred, press return to go back to the lobby\n").reset());
        view.stopUI();
        synchronized(inputLock) {
            sc = new Scanner(System.in);
            sc.reset();
            if (inputPresent) {
                String in = input;
            } else {
                String in = sc.nextLine();
            }
        }
        input = null;
        inputPresent = false;

        view.welcome();
        view.chooseConnection();
    }

    private String buildStat(int stat){
        if(stat < 10) return stat + " ";
        else return String.valueOf(stat);
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


    @Override
    public void run(){
        runningThread = true;
        while (runningThread) {
            synchronized (inputLock) {
                while (inputPresent) {
                    try {
                        inputLock.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }

                //thread safety
                if(Thread.currentThread().isInterrupted()){
                    return;
                }

                //System.out.println("command thread");
                try{
                    input = sc.nextLine();
                }catch (Throwable e){
                    e.printStackTrace();
                }
                if(chatOpened){
                    if(input.equals("q")){
                        closeChat();
                    }else{
                        sendMessage(input);
                    }
                }else if(input.equals("--chat")){
                    Command c = commandMap.get(input);
                    c.execute();
                }else if (input.equals("--quit")) {
                    runningThread = false;
                    commandMap.get(input).execute();
                    input = null;
                    inputPresent = true;
                    inputLock.notifyAll();
                }else if(input.startsWith("--")){
                    Command c = commandMap.get(input);
                    if(c != null) {
                        clear();
                        c.execute();
                        //System.out.println("command");
                        System.out.println("\n~> type q to go back to the game");
                        while (!sc.nextLine().equals("q")) {
                            System.out.println("~> type q to go back to the game");
                        }
                        rePrint.run();
                    }else{
                        System.out.println("Input type mismatch, please type --help for help with commands\n");
                    }
                }else {
                    inputPresent = true;
                    inputLock.notifyAll();
                }
            }
        }
    }


    /**
     * Manages the sending of a message
     * @param input
     */
    private void sendMessage(String input){
        String receiver;
        String content;
        if(input.startsWith("@")){
            receiver = extractWordAfterAt(input);
            content = input.substring(receiver.length() + 1).trim();
            view.sendPrivateChatMessage(content, receiver);
        }else{
            view.sendChatMessage(input);
        }
    }

    /**
     * Extracts the name of the player following the  @ command
     * @param line the entire line containing the message directed to a player
     * @return the name of the player
     */
    private String extractWordAfterAt(String line) {
        // Remove the @ character and trim leading spaces
        String trimmedLine = line.substring(1).trim();

        // Find the first space to isolate the word
        int spaceIndex = trimmedLine.indexOf(' ');

        if (spaceIndex == -1) {
            // No spaces, so the entire trimmedLine is the word
            return trimmedLine;
        } else {
            // Extract the word before the first space
            return trimmedLine.substring(0, spaceIndex);
        }
    }

    /**
     * Parses an integer from the input
     * @return the integer from the input
     */
    private Integer parseInt(){
        int value = 0;
        boolean validInput = false;


        while(!validInput){

            while (!inputPresent) {
                try {
                    inputLock.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }

            try{
                if(input == null) {
                    return null;
                }
                value = Integer.parseInt(input);
                validInput = true;
            } catch(NumberFormatException e){
                System.out.println("Input type mismatch, please insert an integer\n");
            }
            inputPresent = false;
            inputLock.notifyAll();
        }
        return value;
    }

    /**
     * Parses a boolean from the input
     * @return the boolean from the input
     */
    private Boolean parseBoolean(){
        boolean value = false;
        boolean validInput = false;

        while(!validInput){
            while (!inputPresent) {
                try {
                    inputLock.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }

            if(input == null){
                return null;
            }

            if(input.equals("back")) {
                value = false;
                validInput = true;
            }else if(input.equals("front")) {
                value = true;
                validInput = true;
            }else{
                System.out.println("Input type mismatch, please insert (front/back)\n");
            }
            inputPresent = false;
            inputLock.notifyAll();
        }
        return value;

    }
    /**
     * Parses a string from the input
     * @return the string from the input
     */
    private String parseString(){
        boolean validInput = false;

        String value = null;

        while(!validInput){

            while (!inputPresent) {
                try {
                    inputLock.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }

            try{
                if(input == null) {
                    return null;
                }
                value = input;
                validInput = true;
            } catch(NumberFormatException e){
                System.out.println("Input type mismatch, please insert an integer\n");
            }
            inputPresent = false;
            inputLock.notifyAll();
        }
        return value;
    }
    //we use this function to visualize the disposition of other players
    @Override
    public void printDisposition(HashMap<Coordinates, PlayableCard> disp){
        dispositionPrinter.print(disp);
    }

    /**
     * Clears the terminal
     */
    public void clear(){
        System.out.print(ansi().eraseScreen().cursor(0, 0));

        // Print a large number of new lines to push previous content out of view
        for (int i = 0; i < 200; i++) {
            System.out.println();
        }
        // Reset the cursor to the top-left corner again
        System.out.print(ansi().cursor(0, 0));
        System.out.flush();
    }

    /**
     * Displays a string expressing the happened reconnection to a game
     */
    public void displayReconnection(){
        clear();
        System.out.print(ansi().fg(color).a("~> "+view.getPlayerId()+", you have successfully reconnected to the game").reset());
    }

    /**
     * Setter for inWaitingRoom attribute.
     * @param b true if the player is in the waiting room, false otherwise
     */
    public void setInWaitingRoom(Boolean b){
        inWaitingRoom = b;
    }

    /**
     * Gets inWaitingRoom.
     * @return true if the player is in the waiting room, false otherwise
     */
    public boolean getInWaitingRoom(){
        return inWaitingRoom;
    }

    /**
     * Gets game name string.
     *
     * @return the name of the game
     */
    public String getGame(){
        return  game;
    }

    ///////////////////////////////////////////chat/////////////////////////////////////////////////////////////////////
    @Override
    public void displayNewTextMessage(ChatMessage message) {
        if(chatOpened)
            printChat();
    }


    /**
     * Prints the chat.
     */
    public void printChat(){
        chatOpened = true;
        clear();
        System.out.print(ansi().fg(color).a("––––––––––CHAT––––––––––\n").reset());
        List<ChatMessage> history = view.getChatHistory();
        for(int i = 0; i < history.size(); i++){
            String sender;
            if(history.get(i).getSender().equals(view.getPlayerId())) sender = "You";
            else sender = history.get(i).getSender();
            if(history.get(i).getReceiver() == null){
                System.out.print(ansi().fg(color).a("~> "+ sender +": ").reset());
            }else if(history.get(i).getReceiver().equals(view.getPlayerId())){
                System.out.print(ansi().fg(color).a("~> [Private] "+ sender +": ").reset());
            }else {
                System.out.print(ansi().fg(color).a("~> [To " + history.get(i).getReceiver()+ "] "+ sender +": ").reset());
            }
            System.out.println(history.get(i).getContent()+"\n");
        }
        System.out.print(ansi().fg(color).a("~> Insert your message or type q to quit:\n").reset());
    }

    /**
     * Closes the chat.
     */
    private void closeChat(){
        clear();
        chatOpened = false;
        rePrint.run();
    }

    /**
     * Prints the name of the player.
     */
    public void printName(){
        System.out.print(ansi().fg(color).a("~> You are"+nickname+"\n").reset());
    }
}
