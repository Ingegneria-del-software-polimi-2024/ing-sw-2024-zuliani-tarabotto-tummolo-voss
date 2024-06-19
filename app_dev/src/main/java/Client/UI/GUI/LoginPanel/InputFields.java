package Client.UI.GUI.LoginPanel;

import Client.UI.GUI.GUI;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InputFields extends JPanel {

    private GUI gui;
    private String host;
    private String connectionType;
    private String nickname;
    private Frame frame;
    private InputFields panel;
    private String gameName;
    private int numPlayers = 0;
    private List<String> listOfGames;
    private String selectedGame;
    private JPanel mainPanel;
    private boolean connectionSelected = false;
    private boolean nicknameInUse = false;
    private Font font = new Font("Serif", Font.BOLD, 70);
    private boolean cantCreateRoom = false;
    private boolean cantJoinRoom = false;
    private BufferedImage cranioLogo;
    private Dimension textFieldDimension;



    public InputFields(GUI gui, Frame frame){
        this.gui = gui;
        this.frame = frame;
        panel = this;

        setLayout(new GridBagLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);
        textFieldDimension = new Dimension(250, 20);

        try {
            cranioLogo = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/cranioLogo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * asks to choose a connection type(RMI/SOCKET) and a host ip,
     * if the ip is not valid an error message is displayed,
     * if the server is not reachable an error message is displayed
     */
    public void chooseConnection(){
        //rmi button
        JToggleButton rmiButton = new JToggleButton("RMI");

        //socket button
        JToggleButton socketButton = new JToggleButton("Socket");

        //welcome label
        JLabel welcome = new JLabel("Welcome");
        welcome.setFont(new Font("Serif", Font.BOLD, 60));
        welcome.setForeground(new Color(53,31,23));

        //host label
        JLabel hostLabel = new JLabel("Host IP Address:");

        //host ip address textField
        JTextField hostField = new JTextField();
        hostField.setColumns(40);

        //next button
        JButton nextButton = new JButton("next");
        nextButton.setEnabled(false);


        //RMI button listener
        rmiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectionType = "rmi";
                connectionSelected = true;
                rmiButton.setSelected(true);
                socketButton.setSelected(false);
                socketButton.repaint();
                nextButton.setEnabled(!hostField.getText().trim().isEmpty() && connectionSelected);
            }
        });

        //SOCKET button listener
        socketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectionType = "socket";
                connectionSelected = true;
                socketButton.setSelected(true);
                rmiButton.setSelected(false);
                rmiButton.repaint();
                nextButton.setEnabled(!hostField.getText().trim().isEmpty() && connectionSelected);
            }
        });


        // Add the connection type buttons to a sub-panel
        JPanel connectionPanel = new JPanel();
        connectionPanel.setLayout(new FlowLayout());
        connectionPanel.add(rmiButton);
        connectionPanel.add(socketButton);
        connectionPanel.setOpaque(false);



        // panel that notifies if the ip is not valid
        JLabel messageLabel = new JLabel("");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setForeground(Color.RED);


        // HostIP input field listener
        hostField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            private void updateTextFieldContent() {
                host = hostField.getText();
                nextButton.setEnabled(!hostField.getText().trim().isEmpty() && connectionSelected);
                System.out.println("Updated text: " + host);
            }

        });


        //next button listener
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gui.validIP(hostField.getText())){
                    try {
                        gui.getView().startConnection(connectionType, host);
                    } catch (StartConnectionFailedException er) {
                        System.out.println("~> An error during the connection occurred\n   Check your internet connection and retry\n");
                        messageLabel.setText("Impossible to reach the server");
                    }
                }else{
                    messageLabel.setText("IP address not valid");
                }

            }
        });

        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);
        connectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        hostLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        hostField.setAlignmentX(Component.LEFT_ALIGNMENT);
        nextButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(welcome);
        mainPanel.add(connectionPanel);
        mainPanel.add(Box.createVerticalStrut(10)); // Add some space between components
        mainPanel.add(hostLabel);
        mainPanel.add(messageLabel);
        mainPanel.add(hostField);
        mainPanel.add(Box.createVerticalStrut(10)); // Add some space between components
        mainPanel.add(nextButton);

        panel.revalidate();
        panel.repaint();
    }


    /**
     * the player is asked to choose a nickname,
     * if the nickname is already being used by another player a message error is displayed
     */
    public void chooseNickname(){
        mainPanel.removeAll();
        panel.revalidate();
        panel.repaint();

        // JLabel
        JLabel nicknameLabel = new JLabel("Insert your nickname:");

        // Error message label
        JLabel messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);

        // nickname input field
        JTextField nicknameField = new JTextField(1);

        //next button
        JButton nextButton = new JButton("next");
        nextButton.setEnabled(false);

        // if the nickname is already being used we display a message error
        if(nicknameInUse){
            messageLabel.setText("Nickname is already in use.");
            panel.revalidate();
            panel.repaint();
        }

        // nickname input field listener
        nicknameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            private void updateTextFieldContent() {
                nickname = nicknameField.getText();
                nextButton.setEnabled(!nickname.isEmpty());
                //System.out.println("Updated text: " + host);
            }
        });

        //next button listener
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.getView().setPlayerID(nickname);
            }
        });


        nicknameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nicknameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nextButton.setAlignmentX(Component.LEFT_ALIGNMENT);


        mainPanel.add(nicknameLabel); //insert nickname
        mainPanel.add(messageLabel); //error message label
        mainPanel.add(nicknameField); // field for the nickname
        mainPanel.add(nextButton); // next button

        panel.revalidate();
        panel.repaint();
    }


    /**
     * the player is asked to select a game to join,
     * the player can also create a new game with a number of players between 2 and 4
     * @param listOfGames
     */
    public void chooseGame(List<String> listOfGames){
        mainPanel.removeAll();
        panel.revalidate();
        panel.repaint();

        if(listOfGames !=null){
            this.listOfGames = listOfGames;
        }else{this.listOfGames = new ArrayList<>();}


        // button for creating a new game
        JButton createNewGame_JButton = new JButton();
        createNewGame_JButton.setText("CREATE NEW GAME");

        //scroll pane where the list of available games is displayed
        JList<String> games_JList = new JList<>();
        JScrollPane jScrollPane2;
        jScrollPane2 = new JScrollPane(games_JList);
        jScrollPane2.setOpaque(false);

        // button for refreshing the list of available games
        JButton refresh_JButton = new JButton("Refresh");

        // button for joining a game after having selected one
        JButton join_JButton = new JButton("Join");
        join_JButton.setEnabled(this.listOfGames.size() != 0 && selectedGame != null);

        // label for displaying error messages (room full)
        JLabel errorMessageLabel = new JLabel();
        errorMessageLabel.setForeground(Color.RED);


        // createNewGame button listener
        createNewGame_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNewGame();
            }
        });



        games_JList.setModel(new AbstractListModel<String>() {
            public int getSize() { return panel.listOfGames.size(); }
            public String getElementAt(int i) { return panel.listOfGames.get(i); }
        });


        // list of games listener
        games_JList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedGame = games_JList.getSelectedValue();
                    join_JButton.setEnabled(true);
                }
            }
        });

        //refresh button listener
        refresh_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui.getView().requestAvailableGames();

                panel.revalidate();
                panel.repaint();
            }
        });

        //join button listener
        join_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui.getView().joinGame(selectedGame, 0);
                waitingForPlayers();
                panel.revalidate();
                panel.repaint();
            }
        });

        //if the room is already full we display a message error
        if(cantJoinRoom){
            errorMessageLabel.setText("Room is already full");
            mainPanel.revalidate();
            mainPanel.repaint();
            //cantJoinRoom = false;
        }

        // panel that contains the join and refresh button
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(join_JButton);
        buttonPanel.add(refresh_JButton);


        mainPanel.add(createNewGame_JButton); //create new game button
        mainPanel.add(errorMessageLabel); // error message label
        mainPanel.add(jScrollPane2); // list of available games
        mainPanel.add(buttonPanel); // button panel with join and refresh button

        createNewGame_JButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        errorMessageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        jScrollPane2.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);


        panel.revalidate();
        panel.repaint();

    }


    /**
     * a window for creating a new game room is displayed
     */
    public void createNewGame(){
        mainPanel.removeAll();
        panel.revalidate();
        panel.repaint();


        // JLabel
        JLabel nameLabel = new JLabel("Choose the name of the game:");

        // JLabel for the game room name
        JTextField name = new JTextField(1);

        // ComboBox for selecting the number of the players
        String[] playerOptions = {"2", "3", "4"};
        JComboBox<String> playerComboBox = new JComboBox<>(playerOptions);
        numPlayers = 2; //we set the default value for numPlayers

        // button for creating the new room
        JButton createButton = new JButton("Create");
        createButton.setEnabled(false);

        // button for going back to the previous window
        JButton backButton = new JButton("Back");

        //label for displaying error messages( number of selected players not admitted)
        JLabel errorMessageLabel = new JLabel();
        errorMessageLabel.setForeground(Color.RED);

        // input field for the game room name
        name.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            private void updateTextFieldContent() {
                gameName = name.getText();
                createButton.setEnabled(!gameName.isEmpty());
            }
        });


        // comboBox listener
        playerComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String num = (String)playerComboBox.getSelectedItem();
                if(num.equals("2") ) numPlayers = 2;
                if(num.equals("3") ) numPlayers = 3;
                if(num.equals("4") ) numPlayers = 4;
            }
        });


        //next button listener
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(listOfGames == null || !listOfGames.contains(gameName)){
                    System.out.println("new game created" + gameName +" " + numPlayers);
                    selectedGame = gameName;
                    gui.getView().joinGame(gameName, numPlayers);
                    waitingForPlayers();

                }
            }
        });

        //back button listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseGame(listOfGames);
            }
        });

        // a panel that contains the back button and the next button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        buttonPanel.add(createButton);

        //if the number of players selected is not admitted a message error is displayed
        if(cantCreateRoom){
            errorMessageLabel.setText("can't create room");

            panel.revalidate();
            panel.repaint();
        }

        mainPanel.add(nameLabel); // insert name of the game label
        mainPanel.add(name); // field for the name
        mainPanel.add(errorMessageLabel); // error message label
        mainPanel.add(playerComboBox); // box for the selection of the number of players
        mainPanel.add(buttonPanel);


        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        errorMessageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        playerComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);


        panel.revalidate();
        panel.repaint();

    }




    /**
     * after joining/creating a game room a waiting window is displayed
     */
    private void waitingForPlayers(){
        mainPanel.removeAll();

        //JLabel
        JLabel waitingLabel = new JLabel("Waiting for other players to join the game ...");

        //button for going back to the previous window
        JButton backButton = new JButton("back");

        //back button listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.getView().quitGame(selectedGame);
            }
        });

        mainPanel.add(waitingLabel);
        mainPanel.add(backButton);


        panel.revalidate();
        panel.repaint();
    }


    /**
     * if nicknameInUse == true , then the corresponding error message is displayed
     */
    public void setNicknameInUse(){
        nicknameInUse = true;
    }

    /**
     * if cantCreateRoom == true , then the corresponding error message is displayed
     */
    public void setCantCreateRoom(){
        cantCreateRoom = true;
        createNewGame();
    }

    /**
     * if cantJoinRoom == true , then the corresponding error message is displayed
     */
    public void setCantJoinRoom(){
        cantJoinRoom = true;
        chooseGame(listOfGames);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(cranioLogo, 0, getHeight() - (int)(cranioLogo.getHeight() * 0.15), (int)(cranioLogo.getWidth() * 0.15), (int)(cranioLogo.getHeight() * 0.15), this);
    }
}
