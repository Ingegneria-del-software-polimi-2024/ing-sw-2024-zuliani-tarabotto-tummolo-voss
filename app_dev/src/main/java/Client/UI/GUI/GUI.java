package Client.UI.GUI;


import Client.UI.GUI.EventListeners.BoardClickedListener;
import Client.UI.GUI.EventListeners.BoardMotionListener;
import Client.UI.GUI.LoginPanel.LoginPanel;
import Client.UI.UI;
import Client.View.ViewAPI;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    Scanner sc = new Scanner(System.in);
    private HashMap<Integer, BufferedImage> fronts;
    private HashMap<Integer, BufferedImage> backs;
    private HashMap<Resources, BufferedImage> resIcons;
    private HandPanel handPanel;
    private DeckPanel deckPanel;
    private ObjectivesPanel objPanel;
    private BannersPanel bannersPanel;
    private JPanel eastPanel;
    private JPanel bottomPanel;
    private JPanel centerPanel;
    private EndGamePanel endGamePanel;
    private LoginPanel login;
    private JPanel topPanel;
    final int FPS = 30;
    public boolean starterSelected = false;
    public boolean cardSelected = false;
    private PlacementArea board;
    private PlayableCard playCard;
    private String currentDisposition;
    private BoardClickedListener boardClickedListener;
    private BoardMotionListener boardMotionListener;
    private static String title;
    private static JPanel glassPane;
    private JLabel titleLabel;

    public GUI(ViewAPI view){
        this.view = view;
        title = "";

        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("CODEX NATURALIS");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            screenWidth = (int)screenSize.getWidth();
            screenHeight = (int)screenSize.getHeight();
            frame.getContentPane().setBackground(new Color(218, 211, 168));
            frame.setSize(screenWidth, screenHeight);
            frame.setLayout(new BorderLayout());
            frame.setLocationRelativeTo(null);


            /*titleLabel = new JLabel("", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Beaufort", Font.BOLD, 48));
            titleLabel.setForeground(Color.BLACK);
            titleLabel.setBounds(0, frame.getHeight() / 2 - 48, frame.getWidth(), 96);*/

            // Create a glass pane for the title
            glassPane = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (!title.isEmpty()) {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2d.setFont(new Font("Beaufort", Font.BOLD, 70));
                        FontMetrics fm = g2d.getFontMetrics();
                        int stringWidth = fm.stringWidth(title);
                        int stringHeight = fm.getAscent();
                        int x = (getWidth() - stringWidth) / 2;
                        int y = (getHeight() - stringHeight) / 2 + fm.getAscent();
                        g2d.setColor(new Color(200, 170, 110));
                        g2d.drawString(title, x, y);
                    }
                }
            };
            glassPane.setOpaque(false);
            frame.setGlassPane(glassPane);
            glassPane.setVisible(false);

        });
        loadImages();
    }

    private void createBorderPanels(){
        this.currentDisposition = view.getPlayerId();

        this.bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(screenWidth, (int)(screenHeight * 0.26)));
        frame.add(bottomPanel, BorderLayout.SOUTH);

        this.eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        eastPanel.setOpaque(false);
        frame.add(eastPanel, BorderLayout.EAST);

        this.centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new EmptyBorder(10,10,5,5));
        centerPanel.setOpaque(false);
        frame.add(centerPanel, BorderLayout.CENTER);


    }

    @Override
    public void displayInitialization() {
        createBorderPanels();
        createBannersPanel();
        createHandPanel();
        createDecksPanel();
        createObjectivesPanel();
        createBoardPanel();
        deckPanel.updateDecks();
        frame.setVisible(true);
        System.out.println("press something to start: ");
        sc.next();
        view.readyToPlay();

    }

    @Override
    public void displayStarterCardSelection() {
        showTitle("Place Starter Card");
        handPanel.addStarterCard();
    }

    @Override
    public void displayObjectiveSelection() {
        showTitle("Choose Secret Objective");
        bannersPanel.updateBanners();
        updateHand();
        objPanel.updateObjectivesPanel();
        objPanel.chooseObjectives();
    }

    @Override
    public void displayPlacingCard() {
        deckPanel.updateDecks();
        handPanel.updateHand();
        deckPanel.disableListeners();
        if(view.getMyTurn()){
            showTitle("Play a Card");
            currentDisposition = view.getPlayerId();
            handPanel.enableListeners();
        }

    }

    @Override
    public void displayCardDrawing() {
        bannersPanel.updateBanners();
        if(view.getMyTurn()){
            showTitle("Draw a Card");
            handPanel.disableListeners();
            //
            //board.setDisplayAvailable();
            handPanel.updateHand();
            System.out.println("yes");
            deckPanel.enableListeners();
        }

    }

    @Override
    public void displayEndGame() {
        showTitle("The End");

        System.out.println("fine");
        if(endGamePanel == null) endGamePanel = new EndGamePanel(this);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(endGamePanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();

    }


    @Override
    public void printDisposition(HashMap<Coordinates, PlayableCard> disposition) {

    }


    @Override
    public void chooseConnection() {
        login = new LoginPanel(this);
        login.chooseConnection();/*
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

        try {
            view.startConnection(connectionType, host);
        } catch (StartConnectionFailedException e) {
            System.out.println("~> An error during the connection occurred\n   Check your internet connection and retry\n");
            chooseConnection();
        }*/
    }

    @Override
    public void askNickname() {
        /*
        System.out.println("~> Insert your nickname: ");
        String nickname = sc.nextLine();
        view.setPlayerId(nickname);*/
        login.chooseNickname();


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
    public void firstWelcome() {

    }

    @Override
    public void nickNameAlreadyInUse() {

    }

    @Override
    public void cantPlaceACard(PlayableCard card, Coordinates coord) {

    }

    @Override
    public void cantDrawCard(int source) {

    }

    @Override
    public void cantCreateRoom() {

    }

    @Override
    public void cantJoinRoom() {

    }

    @Override
    public void returnToLobby() {

    }


    private boolean validIP(String ip){
        //todo control if the ip is valid
        return true;
    }

    private boolean validPort(int port){
        return(1024<=port && port <=49151);
    }

    private void createBannersPanel(){
        bannersPanel = new BannersPanel(this);
        eastPanel.add(bannersPanel);
    }


    public void createHandPanel(){
        this.handPanel = new HandPanel(this, (int)bottomPanel.getPreferredSize().getHeight());
        bottomPanel.add(handPanel);
    }


    public void createObjectivesPanel(){
        this.objPanel = new ObjectivesPanel(this, (int)bottomPanel.getPreferredSize().getHeight());
        objPanel.setPreferredSize(new Dimension((int)(screenWidth * 0.33), (int)(screenHeight * 0.25)));
        bottomPanel.add(objPanel);
        //frame.add(objPanel, BorderLayout.EAST);
    }
    private void createDecksPanel(){
        this.deckPanel = new DeckPanel(this);
        deckPanel.setPreferredSize(new Dimension((int)(screenWidth * 0.33), (int)(screenHeight * 0.25)));
        bottomPanel.add(deckPanel);
    }


    ////////////////////////////////// GETTER METHODS ////////////////////////////////////////////////////////////////

    public HashMap<Integer, BufferedImage> getFronts(){ return fronts;}
    public HashMap<Integer, BufferedImage> getBacks(){ return backs;}
    public ViewAPI getView(){
        return view;
    }
    public HashMap<Resources, BufferedImage> getResIcons(){return resIcons;}



    /////////////////////////////////////// IMAGE LOADING METHODS /////////////////////////////
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
        int cornerRadius = 100;
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

    @Override
    public void run() {
        //GAMELOOP
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (true) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                frame.repaint();
                delta--;
            }
        }
    }

    public int getScreenWidth(){
        return screenWidth;
    }
    public int getScreenHeight(){
        return screenHeight;
    }

    private void createBoardPanel(){
        board = new PlacementArea(this);
        board.setLayout(new BoxLayout(board,BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(board);
        scroll.setBackground(new Color(50, 84, 70));

        JScrollBar verticalScrollBar = scroll.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(5); // Adjust unit increment for smoother scrolling
        verticalScrollBar.setBlockIncrement(1); // Adjust block increment for smoother scrolling


        JScrollBar horizontalScrollBar = scroll.getHorizontalScrollBar();
        horizontalScrollBar.setUnitIncrement(5); // Adjust unit increment for smoother scrolling
        horizontalScrollBar.setBlockIncrement(1); // Adjust block increment for smoother scrolling

        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));



        centerPanel.add(scroll, BorderLayout.CENTER);
        frame.setVisible(true);
        SwingUtilities.invokeLater(() -> {
                    Dimension viewportSize = scroll.getViewport().getExtentSize();
                    Dimension panelSize = board.getPreferredSize();
                    System.out.println(panelSize.height + ", "+ panelSize.width);
                    System.out.println(viewportSize.width + ", "+ viewportSize.height);

                    // Calculate the top-left position to center the panel
                    int x = (panelSize.width - viewportSize.width) / 2;
                    int y = (panelSize.height - viewportSize.height) / 2;

                    // Adjust if the panel size is smaller than the viewport
                    x = Math.max(0, x);
                    y = Math.max(0, y);

                    // Set the view position to center the panel
                    scroll.getViewport().setViewPosition(new Point(x, y));
        });

        boardClickedListener = new BoardClickedListener(this, board);
        boardMotionListener = new BoardMotionListener(this, board);
    }

    public void enableBoardListeners(){
        board.addMouseListener(boardClickedListener);
        board.addMouseMotionListener(boardMotionListener);
    }

    public void disableBoardListeners(){
        board.removeMouseListener(boardClickedListener);
        board.removeMouseMotionListener(boardMotionListener);
    }

    public void setSelectedCard(PlayableCard c){
        this.cardSelected = !cardSelected;
        if(cardSelected){
            //System.out.println("enabled");
            this.playCard = c;
            board.setDisplayAvailable();
            enableBoardListeners();
        }else{
            disableBoardListeners();
            //System.out.println("disabled");
            board.setDisplayAvailable();
        }
    }


    public PlayableCard getPlayCard(){return  playCard;}

    public void setStarterSelected(){
        starterSelected = !starterSelected;
        if(starterSelected){
            board.addMouseListener(boardClickedListener);
        }else{
            board.removeMouseListener(boardClickedListener);
        }
    }

    public void updateHand(){
        handPanel.updateHand();
    }


    public void setCurrentDisposition(String player){
        this.currentDisposition = player;
    }

    public String getCurrentDisposition(){return currentDisposition;}




    public static void showTitle(String newTitle) {
        title = newTitle;
        glassPane.setVisible(true);
        glassPane.repaint();

        // Hide the title after a delay
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                glassPane.setVisible(false);
                title = ""; // Clear the title
                System.out.println("title cleared: " + title);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}
