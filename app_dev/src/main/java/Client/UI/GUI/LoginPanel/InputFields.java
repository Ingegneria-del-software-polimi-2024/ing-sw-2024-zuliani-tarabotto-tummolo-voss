package Client.UI.GUI.LoginPanel;

import Client.UI.GUI.GUI;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
    private Font font = new Font("Beaufort", Font.BOLD, 70);
    private boolean cantCreateRoom = false;
    private boolean cantJoinRoom = false;





    public InputFields(GUI gui, Frame frame){
        this.gui = gui;
        this.frame = frame;
        panel = this;

        setLayout(new GridBagLayout());
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);
    }





    public void chooseConnection(){
        // Create the connection type buttons
        JToggleButton rmiButton = new JToggleButton("RMI");
        JToggleButton socketButton = new JToggleButton("Socket");

        rmiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectionType = "rmi";
                connectionSelected = true;
            }
        });
        socketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectionType = "socket";
                connectionSelected = true;
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


        mainPanel.add(connectionPanel);
        JButton nextButton = new JButton("next");
        nextButton.setEnabled(false);

        JLabel hostLabel = new JLabel("Host IP Address:");
        JTextField hostField = new JTextField(1);
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


        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gui.validIP(hostField.getText())){

                    try {
                        gui.getView().startConnection(connectionType, host);
                        //panel.removeAll();
                    } catch (StartConnectionFailedException er) {
                        System.out.println("~> An error during the connection occurred\n   Check your internet connection and retry\n");
                        messageLabel.setText("An error during the connection occurred\n   Check your internet connection and retry\n");
                        gui.chooseConnection();
                    }
                }else{
                    messageLabel.setText("IP address not valid");
                }

            }
        });

        connectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        hostLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        hostField.setAlignmentX(Component.LEFT_ALIGNMENT);
        nextButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(10)); // Add some space between components
        mainPanel.add(hostLabel);
        mainPanel.add(messageLabel);
        mainPanel.add(hostField);
        mainPanel.add(Box.createVerticalStrut(10)); // Add some space between components
        mainPanel.add(nextButton);

        panel.revalidate();
    }





    public void chooseNickname(){
        mainPanel.removeAll();
        panel.revalidate();
        panel.repaint();

        JLabel nicknameLabel = new JLabel("Insert your nickname:");

        JTextField nicknameField = new JTextField(1);
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
                System.out.println("Updated text: " + host);
            }
        });


        // Create a JLabel to display messages
        JLabel messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        if(nicknameInUse){
            messageLabel.setText("Nickname is already in use.");
            mainPanel.revalidate();
            mainPanel.repaint();
        }


        JButton nextButton = new JButton("next");
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
    }







    public void chooseGame(List<String> listOfGames){
        mainPanel.removeAll();
        panel.revalidate();
        panel.repaint();
        if(listOfGames == null){
            this.listOfGames = new ArrayList<>();
        }else{this.listOfGames = listOfGames;}



        JButton createNewGame_JButton = new JButton();
        JScrollPane jScrollPane2;
        JList<String> games_JList = new JList<>();
        JButton refresh_JButton = new JButton();
        JButton join_JButton = new JButton();


        createNewGame_JButton.setText("CREATE NEW GAME");

        createNewGame_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNewGame();
            }
        });



        games_JList.setModel(new AbstractListModel<String>() {
            public int getSize() { return panel.listOfGames.size(); }
            public String getElementAt(int i) { return panel.listOfGames.get(i); }
        });

        // Add a ListSelectionListener to handle game selection
        games_JList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedGame = games_JList.getSelectedValue();
                    if (selectedGame != null) {
                        System.out.println("Selected game: " + selectedGame);
                    }
                }
            }
        });

        // Add the JList to a JScrollPane
        jScrollPane2 = new JScrollPane(games_JList);


        refresh_JButton.setText("Refresh");

        refresh_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui.getView().requestAvailableGames();
                panel.repaint();
            }
        });


        join_JButton.setText("join");

        join_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui.getView().joinGame(selectedGame, 0);
                panel.repaint();
            }
        });

        JLabel errorMessageLabel = new JLabel();
        errorMessageLabel.setForeground(Color.RED);
        if(cantJoinRoom){
            errorMessageLabel.setText("Room is already full");
            cantJoinRoom = false;
        }

        JPanel buttonPanel = new JPanel(new FlowLayout());
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

    }





    public void createNewGame(){
        mainPanel.removeAll();
        panel.revalidate();
        panel.repaint();


        JLabel nameLabel = new JLabel("Choose the name of the game:");

        JTextField name = new JTextField(1);
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
                System.out.println("Updated text: " + host);
            }
        });



        // Create the JComboBox
        String[] playerOptions = {"2", "3", "4"};
        JComboBox<String> playerComboBox = new JComboBox<>(playerOptions);
        numPlayers = 2; //we set the default value for numPlayers

        // Add an ActionListener to the JComboBox to handle selection changes
        playerComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String num = (String)playerComboBox.getSelectedItem();
                if(num.equals("2") ) numPlayers = 2;
                if(num.equals("3") ) numPlayers = 3;
                if(num.equals("4") ) numPlayers = 4;
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        JButton nextButton = new JButton("create");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(listOfGames == null || !listOfGames.contains(gameName)){
                    System.out.println("new game created" + gameName +" " + numPlayers);
                    gui.getView().joinGame(gameName, numPlayers);
                    waitingForPlayers();

                }
            }
        });

        JButton backButton = new JButton("back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseGame(listOfGames);
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        JLabel errorMessageLabel = new JLabel();
        errorMessageLabel.setForeground(Color.RED);
        if(cantCreateRoom){
            errorMessageLabel.setText("cant create room");
            cantCreateRoom = false;
        }

        mainPanel.add(nameLabel); // insert name of the game label
        mainPanel.add(name); // field for the name
        mainPanel.add(errorMessageLabel); // error message label
        mainPanel.add(playerComboBox); // box for the selection of the number of players
        mainPanel.add(buttonPanel);
        //mainPanel.add(nextButton); // next button

        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        errorMessageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        playerComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        //nextButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.revalidate();

    }

    private void waitingForPlayers(){
        mainPanel.removeAll();

        JLabel waitingLabel = new JLabel("Waiting for other players to join the game ...");
        JButton backButton = new JButton("back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.getView().quitGame();
                chooseGame(listOfGames);
            }
        });

        mainPanel.add(waitingLabel);
        mainPanel.add(backButton);


        panel.revalidate();
        panel.repaint();
    }


    public void setNicknameInUse(){
        nicknameInUse = true;
    }

    public void setCantCreateRoom(){
        cantCreateRoom = true;
        createNewGame();
    }

    public void setCantJoinRoom(){
        cantJoinRoom = true;
        chooseGame(listOfGames);
    }
}
