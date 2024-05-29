package Client.UI.GUI;


import Client.UI.GUI.PlayerBanner.PlayerPanel;
import Client.UI.UI;
import Client.View.ViewAPI;

import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.placementArea.Coordinates;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;


public class GUI  implements UI {

    private JFrame frame;
    private ViewAPI view;
    private int screenWidth;
    private int screenHeight;
    private HashMap<String, PlayerPanel> banners;
    Scanner sc = new Scanner(System.in);
    private HashMap<Integer, BufferedImage> fronts;
    private HashMap<Integer, BufferedImage> backs;
    private HashMap<Resources, BufferedImage> resIcons;
    private HandPanel handPanel;
    private DeckPanel deckPanel;




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
            frame.setLayout(new BorderLayout());

        });
        loadImages();
    }

    @Override
    public void displayInitialization() {
        System.out.println("heki");
        createPlayerBanner();
        createHandPanel();
        deckPanel.updateDecks();
        handPanel.updateHand();
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
        JPanel bannerPanel = new JPanel();
        bannerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for(String p : view.getPlayers()){
            PlayerPanel player = new PlayerPanel( p, (int ) (screenWidth * 0.18), (int)(screenHeight * 0.13), this);
            player.setPreferredSize(new Dimension((int ) (screenWidth * 0.18), (int)(screenHeight * 0.13)));
            banners.put(p, player);
            bannerPanel.add(player);
        }
        frame.add(bannerPanel);
    }

    public void createDecksBanner(){

    }

    public void createHandPanel(){
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.handPanel = new HandPanel(this);
        this.deckPanel = new DeckPanel(this);
        bottomPanel.add(handPanel);
        bottomPanel.add(deckPanel);
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateBannerResources( ){
        for(String p : view.getPlayers()){
            banners.get(p).updateResources();
        }
    }

    private void loadImages(){
        String index;
        fronts = new HashMap<>();
        backs = new HashMap<>();
        resIcons = new HashMap<>();
        //we load the card images
        for(int i = 1; i <= 102; i++){

            if(i < 10) {index = "00" + i;}
            else if(i < 100) {index = "0" + i;}
            else {index = String.valueOf(i);}
            //System.out.println(index);

            String frontImagePath = "/Images/front/"+ index + ".png";
            String backImagePath = "/Images/back/" + index + ".png";
            try {
                fronts.put(i, makeRoundedCorner(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(frontImagePath)))) );
                backs.put(i, makeRoundedCorner(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(backImagePath)))) );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(Resources r : Resources.values()){
            try{
                resIcons.put(r, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(r.getImg()))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private BufferedImage makeRoundedCorner(BufferedImage image) {
        int cornerRadius = 50;
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();
        // Enable antialiasing for smoother corners
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded rectangle
        g2.setComposite(AlphaComposite.Src);
        g2.setColor(Color.WHITE); // The color doesn't matter, we use it to define the shape
        g2.fillRoundRect(0, 0, w, h, cornerRadius, cornerRadius);

        // Apply the mask to the image
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();
        return output;
    }

    public HashMap<Integer, BufferedImage> getFronts(){ return fronts;}
    public HashMap<Integer, BufferedImage> getBacks(){ return backs;}
    public ViewAPI getView(){
        return view;
    }
    public HashMap<Resources, BufferedImage> getResIcons(){return resIcons;}
}
