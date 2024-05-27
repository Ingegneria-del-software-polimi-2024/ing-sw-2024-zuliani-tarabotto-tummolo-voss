package Client.UI.GUI;


import Client.UI.GUI.PlayerBanner.PlayerPanel;
import Client.UI.UI;
import Client.View.ViewAPI;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;


public class GUI  implements UI {

    private JFrame frame;
    private ViewAPI view;
    private int screenWidth;
    private int screenHeight;
    private HashMap<String, PlayerPanel> banners;
    Scanner sc = new Scanner(System.in);



    public GUI(ViewAPI view){
        this.view = view;
        this.banners = new HashMap<>();

        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("CODEX NATURALIS");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            screenWidth = (int)screenSize.getWidth();
            screenHeight = (int)screenSize.getHeight();
            frame.setSize(screenWidth, screenHeight);
            frame.setLayout(new FlowLayout(FlowLayout.LEADING));

        });
    }

    @Override
    public void displayInitialization() {
        System.out.println("heki");
        createPlayerBanner();
        frame.setVisible(true);
        sc.next();
        view.getAvailableArtifacts("fra").put(Artifact.paper, 3);
        updateBannerResources();

    }

    @Override
    public void displayStarterCardSelection() {

    }

    @Override
    public void displayObjectiveSelection() {

    }

    @Override
    public void displayPlacingCard() {

    }

    @Override
    public void displayCardDrawing() {

    }

    @Override
    public void displayEndGame() {

    }

    @Override
    public void printDisposition(HashMap<Coordinates, PlayableCard> disposition) {

    }

    @Override
    public void displayCalculateObjectives() {

    }

    @Override
    public void chooseConnection() {
        System.out.println("~> Welcome to Codex Naturalis, insert the techonolgy of connection (RMI/Socket):");
        String connectionType;
        do{
            connectionType = sc.nextLine();
            if(!(connectionType.equalsIgnoreCase("RMI") || connectionType.equalsIgnoreCase("SOCKET")))
                System.out.println("~> Insert a correct value RMI or Socket:");
        }while (!(connectionType.equalsIgnoreCase("RMI") || connectionType.equalsIgnoreCase("SOCKET")));

        String host;
        System.out.println("~> Insert the host ip address: ");
        do{
            host = sc.nextLine();
            if (!validIP(host))
                System.out.println("~> Insert a valid ip address (xxx.xxx.xxx.xxx): ");
        }while(!validIP(host));

        int port;
        System.out.println("~> Insert the host port: ");
        do{
            port = Integer.parseInt(sc.nextLine());
            if (!validPort(port))
                System.out.println("~> Insert a valid port: ");
        }while(!validPort(port));

        int localPort;
        System.out.println("~> Insert the local port: ");
        do{
            localPort = Integer.parseInt(sc.nextLine());
            if (!validPort(localPort))
                System.out.println("~> Insert a valid port: ");
        }while(!validPort(localPort));
        System.out.println("ciao");
        view.startConnection(connectionType, host, port, localPort);
    }

    @Override
    public void askNickname() {
        System.out.println("~> Insert your nickname: ");
        String nickname = sc.nextLine();
        view.setPlayerId(nickname);

    }

    @Override
    public void displayAvailableGames(ArrayList<String> listOfGames) {
        String game;


        if(listOfGames != null && !listOfGames.isEmpty()){
            System.out.println("~> Available games: ");
            for(String g : listOfGames)
                System.out.println("   "+g+" ");
            System.out.println("~> Insert the name of the game you want to join or a new name if you want to create it, write -r to refresh the available games: ");
        }else
            System.out.print("~> There are no available games, please create a new game by typing its name or write -r to refresh the available games: ");

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
                System.out.println("~> You want to create a new game! \n~> Insert the number of players: ");
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

    @Override
    public void joinedGame(String id) {
        System.out.println("~> Correctly joined the game "+id+"");
    }

    @Override
    public void run() {

    }

    private boolean validIP(String ip){
        //todo control if the ip is valid
        return true;
    }

    private boolean validPort(int port){
        return(1024<=port && port <=49151);
    }

    private void createPlayerBanner(){
        for(String p : view.getPlayers()){
            PlayerPanel player = new PlayerPanel( p, (int ) (screenWidth * 0.18), (int)(screenHeight * 0.13));
            player.setPreferredSize(new Dimension((int ) (screenWidth * 0.18), (int)(screenHeight * 0.13)));
            banners.put(p, player);
            frame.add(player);
        }
    }

    private void updateBannerResources( ){
        for(String p : view.getPlayers()){
            banners.get(p).updateResources(view.getAvailableElements(p), view.getAvailableArtifacts(p));
        }
    }
}
