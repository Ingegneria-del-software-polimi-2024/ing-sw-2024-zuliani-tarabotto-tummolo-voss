package Client.UI.GUI;


import Chat.MessagesFromClient.ChatMessage;
import Client.UI.GUI.EventListeners.BoardClickedListener;
import Client.UI.GUI.EventListeners.BoardMotionListener;
import Client.UI.GUI.LoginPanel.LoginFrame;
import Client.UI.GUI.chat.ChatPanel;
import Client.UI.UI;
import Client.View.ViewAPI;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fusesource.jansi.Ansi.ansi;


/**
 * The type Gui.
 */
public class  GUI  implements UI {

    private JFrame frame;
    private ViewAPI view;
    private int screenWidth;
    private int screenHeight;
    private HashMap<Integer, BufferedImage> fronts;
    private HashMap<Integer, BufferedImage> backs;
    private HashMap<Resources, BufferedImage> resIcons;
    private ChatPanel chatPanel;
    private HandPanel handPanel;
    private DeckPanel deckPanel;
    private ObjectivesPanel objPanel;
    private BannersPanel bannersPanel; //player infos
    private JPanel eastPanel; //bannersPanel
    private JPanel bottomPanel; //hand, decks, objectives
    private JPanel centerPanel; //board
    private JPanel topPanel; //quit, rules, tutorial
    private EndGamePanel endGamePanel;
    private LoginFrame login;
    /**
     * The Fps.
     */
    final int FPS = 40;
    /**
     * The Starter selected.
     */
    public boolean starterSelected = false;
    /**
     * The Card selected.
     */
    public boolean cardSelected = false;
    private PlacementArea board;
    private PlayableCard playCard;
    private String currentDisposition;
    private BoardClickedListener boardClickedListener;
    private BoardMotionListener boardMotionListener;
    private static String title;
    private JPanel titlesPanel;
    private boolean desiredQuit = false;
    private CardLayout cardLayout;
    private int cardLength;
    private int currentPictureIndex = 0;
    private JLabel pictureLabel;
    private final String[] picturePaths = {
            "/Users/gabrielvoss/Documents/GitHub/ing-sw-2024-zuliani-tarabotto-tummolo-voss/app_dev/src/main/resources/Images/front/002.png",
            "/Users/gabrielvoss/Documents/GitHub/ing-sw-2024-zuliani-tarabotto-tummolo-voss/app_dev/src/main/resources/Images/front/003.png",
            "/Users/gabrielvoss/Documents/GitHub/ing-sw-2024-zuliani-tarabotto-tummolo-voss/app_dev/src/main/resources/Images/front/004.png",
            "/Users/gabrielvoss/Documents/GitHub/ing-sw-2024-zuliani-tarabotto-tummolo-voss/app_dev/src/main/resources/Images/front/005.png",
            "/Users/gabrielvoss/Documents/GitHub/ing-sw-2024-zuliani-tarabotto-tummolo-voss/app_dev/src/main/resources/Images/front/005.png"
    };

    /**
     * Instantiates a new Gui.
     *
     * @param view the view
     */
    public GUI(ViewAPI view){
        this.view = view;
        title = "";
        loadImages();
        createFrame();
    }

    private void createBorderPanels(){

        // Create MenuBar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Help");

        JMenuItem rulesItem = new JMenuItem("Rules");
        JMenuItem uiItem = new JMenuItem("How to use");


        rulesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRulesPopup(frame);
            }
        });
        uiItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUiPopup();
            }
        });

        menu.add(rulesItem);
        menu.add(uiItem);
        menuBar.add(menu);

        // Add spacing between Help menu and other items
        menuBar.add(Box.createHorizontalGlue());



        // Create Quit and Chat menu items
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desiredQuit = true;
                quitGame();
            }
        });

        JMenuItem chatItem = new JMenuItem("Chat");
        chatItem.addActionListener(new ActionListener() {
            private boolean showingGame = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) eastPanel.getLayout();
                if (showingGame) {
                    cl.show(eastPanel, "chat");
                    chatItem.setText("Players");
                } else {
                    cl.show(eastPanel, "banners");
                    chatItem.setText("Chat");
                }
                showingGame = !showingGame;
            }
        });

        // Add Quit and Chat items to the MenuBar
        menuBar.add(chatItem);
        menuBar.add(quitItem);


        frame.setJMenuBar(menuBar);
        this.currentDisposition = view.getPlayerId();
        this.topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        JButton quit = new JButton("quit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desiredQuit = true;
                quitGame();
            }
        });

        JButton chat = new JButton("chat");
        chat.addActionListener(new ActionListener() {
            private boolean showingGame = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) eastPanel.getLayout();
                if (showingGame) {
                    cl.show(eastPanel, "chat");
                    chat.setText("players");
                } else {
                    cl.show(eastPanel, "banners");
                    chat.setText("chat");
                }
                showingGame = !showingGame;
            }
        });




        this.bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(screenWidth, (int)(screenHeight * 0.26)));
        frame.add(bottomPanel, BorderLayout.SOUTH);


        //we create the eastPanel with a cardLayout to show both the players' banners and the chat
        this.eastPanel = new JPanel(new CardLayout());
        eastPanel.setOpaque(false);
        eastPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        frame.add(eastPanel, BorderLayout.EAST);



        //this.centerPanel = new JPanel(new BorderLayout());
        cardLayout = new CardLayout();
        this.centerPanel = new JPanel(cardLayout);
        centerPanel.setBorder(new EmptyBorder(10,10,5,5));
        centerPanel.setOpaque(false);
        frame.add(centerPanel, BorderLayout.CENTER);


    }


    private void showRulesPopup(JFrame frame) {
        // Create the rules dialog
        JDialog rulesDialog = new JDialog(frame, "Rules", true);
        rulesDialog.setSize(600, 400);
        rulesDialog.setLayout(new BorderLayout());

        // Initialize the picture label
        pictureLabel = new JLabel();
        pictureLabel.setHorizontalAlignment(JLabel.CENTER);
        updatePicture();

        // Create a button to navigate through pictures
        JButton nextButton = new JButton("next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPictureIndex = (currentPictureIndex + 1) % picturePaths.length;
                updatePicture();
            }
        });

        // Add components to the dialog
        rulesDialog.add(pictureLabel, BorderLayout.CENTER);
        rulesDialog.add(nextButton, BorderLayout.SOUTH);

        // Set dialog properties
        rulesDialog.setResizable(false);
        rulesDialog.setLocationRelativeTo(frame);
        rulesDialog.setVisible(true);
    }



    private void showUiPopup() {
        // Create the rules dialog
        JDialog rulesDialog = new JDialog(frame, "How to use", true);
        rulesDialog.setSize(600, 400);
        rulesDialog.setLayout(new BorderLayout());

        // Initialize the picture label
        pictureLabel = new JLabel();
        pictureLabel.setHorizontalAlignment(JLabel.CENTER);
        updatePicture();

        // Create a button to navigate through pictures
        JButton nextButton = new JButton("next tip");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPictureIndex = (currentPictureIndex + 1) % picturePaths.length;
                updatePicture();
            }
        });

        // Add components to the dialog
        rulesDialog.add(pictureLabel, BorderLayout.CENTER);
        rulesDialog.add(nextButton, BorderLayout.SOUTH);

        // Set dialog properties
        rulesDialog.setResizable(false);
        rulesDialog.setLocationRelativeTo(frame);
        rulesDialog.setVisible(true);
    }

    private void updatePicture() {
        ImageIcon icon = new ImageIcon(picturePaths[currentPictureIndex]);
        pictureLabel.setIcon(icon);
    }

    /**
     * Create frame.
     */
    public void createFrame(){
        System.out.println("new Frame");
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


        });
        login = new LoginFrame(this);
    }

    @Override
    public void displayInitialization() {
        login.dispose();
        login = null;
        createBorderPanels();
        createBannersPanel();
        createChatPanel();
        createHandPanel();
        createDecksPanel();
        createObjectivesPanel();
        createBoardPanel();
        deckPanel.updateDecks();
        frame.setVisible(true);

        view.readyToPlay();
        showTitle("Welcome to \n Codex Naturalis");

    }

    @Override
    public void displayStarterCardSelection() {

        showTitle("Place your Starter Card");
        handPanel.addStarterCard();
    }

    @Override
    public void displayObjectiveSelection() {
        showTitle("Choose a Secret Objective");
        bannersPanel.updateBanners();
        handPanel.updateHand();
        objPanel.addSecretObjectives();
        objPanel.updateObjectivesPanel();
        objPanel.chooseObjectives();
    }

    @Override
    public void displayPlacingCard() {
        deckPanel.updateDecks();
        handPanel.updateHand();
        deckPanel.disableListeners();
        if(view.getMyTurn()){
            showTitle("Place a Card");
            currentDisposition = view.getPlayerId();
            handPanel.enableListeners();
        }else{
            showTitle("It's " + getView().getTurnPlayer() + "'s turn");
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
            deckPanel.enableListeners();
        }

    }

    @Override
    public void displayEndGame() {
        System.out.println("fine");
    }


    @Override
    public void printDisposition(HashMap<Coordinates, PlayableCard> disposition) {

    }


    @Override
    public void chooseConnection() {
        login.chooseConnection();
    }


    @Override
    public void displayAvailableGames(ArrayList<String> listOfGames) {
        if(frame == null && login == null){
            createFrame();
        }

        login.chooseGame(listOfGames);

    }

    @Override
    public void joinedGame(String id) {
        System.out.println("~> Correctly joined the game "+id+"");
    }


    @Override
    public void firstWelcome() {

    }

    @Override
    public void askNickname() {
        login.chooseNickname();
    }

    @Override
    public void nickNameAlreadyInUse() {
        login.nicknameInUse();
        askNickname();
    }

    @Override
    public void cantPlaceACard(PlayableCard card, Coordinates coord) {
        displayPlacingCard();
    }

    @Override
    public void cantDrawCard(int source) {
        displayCardDrawing();
    }

    @Override
    public void cantCreateRoom() {
        login.cantCreateRoom();
    }

    @Override
    public void cantJoinRoom() {
        login.cantJoinRoom();
    }

    @Override
    public void returnToLobby() {
        if(desiredQuit){
            desiredQuit = false;
            goBackToLobby();
        }else {
            endGamePanel = new EndGamePanel(this);
            frame.getContentPane().removeAll();
            frame.getContentPane().add(endGamePanel, BorderLayout.CENTER);
            frame.setJMenuBar(null); // Remove the JMenuBar
            frame.revalidate();
            frame.repaint();
        }
    }

    /**
     * Go back to lobby.
     */
    public void goBackToLobby(){
        if(frame != null){
            frame.dispose();
            frame = null;
        }
        if(login != null){
            login.dispose();
            login = null;
        }
        view.welcome();
        view.requestAvailableGames();

    }

    @Override
    public void printStarterCard() {
        //we use this function only in tui
        //updateHand();
    }

    @Override
    public void printSecretObjective() {
        objPanel.printObjectives();
    }

    @Override
    public void displayReconnection() {
        System.out.println("displayreconnection");
        if(frame == null) createFrame();
        login.dispose();
        login = null;
        createBorderPanels();
        createBannersPanel();
        createChatPanel();
        chatPanel.restoreChatHistory();
        createHandPanel();
        createDecksPanel();
        createObjectivesPanel();
        createBoardPanel();
        deckPanel.updateDecks();
        if(bannersPanel != null) bannersPanel.updateBanners();
        if(view.getHand() != null) handPanel.updateHand();
        if(view.getSecretObjective() != null) objPanel.printObjectives();
        frame.setVisible(true);
    }

    @Override
    public void returnToChooseGame() {
        //we only use this method in tui
    }

    @Override
    public void displayNewTextMessage(ChatMessage message) {
        chatPanel.updateChat(message.getSender(), message.getContent(), message.getReceiver());
    }


    /**
     * Valid ip boolean.
     *
     * @param ip the ip
     * @return the boolean
     */
    public boolean validIP(String ip){

        if(ip.toLowerCase(Locale.ROOT).equals("localhost"))
            return true;
        String desiredFormat = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        Pattern pattern = Pattern.compile(desiredFormat);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    private boolean validPort(int port){
        return(1024<=port && port <=49151);
    }

    private void createBannersPanel(){
        bannersPanel = new BannersPanel(this);
        eastPanel.add(bannersPanel, "banners");
    }

    private void createChatPanel(){
        chatPanel = new ChatPanel(this);
        chatPanel.setMaximumSize(bannersPanel.getPreferredSize());
        eastPanel.add(chatPanel, "chat");
    }


    /**
     * Create hand panel.
     */
    public void createHandPanel(){
        this.handPanel = new HandPanel(this, (int)bottomPanel.getPreferredSize().getHeight());
        bottomPanel.add(handPanel);
    }


    /**
     * Create objectives panel.
     */
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

    @Override
    public void updateResourcesInUI(){
        if(bannersPanel != null) bannersPanel.updateBanners();
    }

    @Override
    public void returnToStart() {
        desiredQuit = false;
        if(frame != null){
            SwingUtilities.invokeLater(() -> {
                try {
                    frame.dispose();
                    System.out.println("Frame disposed.");
                } catch (Exception e) {
                    System.err.println("Disposal was interrupted: " + e.getMessage());
                }
            });
        }
        System.out.println("next");
        if(login == null){
            login = new LoginFrame(this);
        }
        login.chooseConnection();
    }


    private void createBoardPanel(){
        board = new PlacementArea(this, cardLength);
        //board.setLayout(new BoxLayout(board,BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(board);
        ;
        scroll.setBackground(new Color(50, 84, 70));


        JScrollBar verticalScrollBar = scroll.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(5); // Adjust unit increment for smoother scrolling
        verticalScrollBar.setBlockIncrement(1); // Adjust block increment for smoother scrolling


        JScrollBar horizontalScrollBar = scroll.getHorizontalScrollBar();
        horizontalScrollBar.setUnitIncrement(5); // Adjust unit increment for smoother scrolling
        horizontalScrollBar.setBlockIncrement(1); // Adjust block increment for smoother scrolling

        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));


        ///////// titles panel ///////////////////
        titlesPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("", SwingConstants.CENTER);
        title.setForeground(new Color(200, 170, 110));
        title.setFont(new Font("serif", Font.BOLD, 60));
        titlesPanel.add(title, BorderLayout.CENTER);
        titlesPanel.setBackground(new Color(50, 84, 70));

        //centerPanel.add(scroll, BorderLayout.CENTER);
        centerPanel.add(scroll, "BOARD");
        centerPanel.add(titlesPanel, "TITLES");
        cardLayout.show(centerPanel, "BOARD");

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

    /**
     * Enable board listeners.
     */
    public void enableBoardListeners(){
        board.addMouseListener(boardClickedListener);
        board.addMouseMotionListener(boardMotionListener);
    }

    /**
     * Disable board listeners.
     */
    public void disableBoardListeners(){
        board.removeMouseListener(boardClickedListener);
        board.removeMouseMotionListener(boardMotionListener);
    }

    /**
     * Set selected card.
     *
     * @param c the c
     */
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


    /**
     * Set starter selected.
     */
    public void setStarterSelected(){
        starterSelected = !starterSelected;
        if(starterSelected){
            board.enableBoardStarterListener();
        }else{
            board.disableBoardStarterListener();
        }
    }

    /**
     * Update hand.
     */
    public void updateHand(){
        handPanel.updateHand();
    }


    /**
     * Set current disposition.
     *
     * @param player the player
     */
    public void setCurrentDisposition(String player){
        this.currentDisposition = player;
    }

    /**
     * Get current disposition string.
     *
     * @return the string
     */
    public String getCurrentDisposition(){return currentDisposition;}


    /**
     * Show title.
     *
     * @param newTitle the new title
     */
    public  void showTitle(String newTitle) {

        JLabel label = (JLabel) titlesPanel.getComponent(0);
        label.setText(newTitle);
        cardLayout.show(centerPanel, "TITLES");


        // Create a Timer that waits for 2 seconds (2000 milliseconds)
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel, "BOARD");
            }
        });
        // Start the timer
        timer.setRepeats(false); // Ensure it only runs once
        timer.start();

    }

    /**
     * Quit game.
     */
    public void quitGame(){
        frame.setJMenuBar(null);  // Remove the menu bar
        frame.revalidate();  // Refresh the frame to apply changes

        frame.dispose();
        frame = null;
        login = null;
        view.quitGame();

    }

    /**
     * Check card is placeable boolean.
     *
     * @return the boolean
     */
    public boolean checkCardIsPlaceable(){
        int index = view.getHand().indexOf(playCard);
        if(!playCard.getFaceSide()) return true;
        else return view.getCanBePlaced()[index];
    }

    /**
     * Set card length.
     *
     * @param cardLength the card length
     */
    public void setCardLength(int cardLength){
        this.cardLength = cardLength;
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
                if(frame != null){

                    frame.repaint();
                }
                delta--;
            }
        }
    }


    ////////////////////////////////// GETTER METHODS ////////////////////////////////////////////////////////////////

    /**
     * Get fronts hash map.
     *
     * @return the hash map
     */
    public HashMap<Integer, BufferedImage> getFronts(){ return fronts;}

    /**
     * Get backs hash map.
     *
     * @return the hash map
     */
    public HashMap<Integer, BufferedImage> getBacks(){ return backs;}

    /**
     * Get view view api.
     *
     * @return the view api
     */
    public ViewAPI getView(){
        return view;
    }

    /**
     * Get res icons hash map.
     *
     * @return the hash map
     */
    public HashMap<Resources, BufferedImage> getResIcons(){return resIcons;}

    /**
     * Gets screen height.
     *
     * @return the screen height
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Gets screen width.
     *
     * @return the screen width
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Get play card playable card.
     *
     * @return the playable card
     */
    public PlayableCard getPlayCard(){return  playCard;}





    /////////////////////////////////////// IMAGE LOADING METHOD /////////////////////////////

    /**
     * method used to load all images
     */
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


    /**
     * method used on each card's image to round the corners
     * @param image
     * @return
     */
    private BufferedImage makeRoundedCorner(BufferedImage image) {
        int cornerRadius = 100;
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();
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
}
