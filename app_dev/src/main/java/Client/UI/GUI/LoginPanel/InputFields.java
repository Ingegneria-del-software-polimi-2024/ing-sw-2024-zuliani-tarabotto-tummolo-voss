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

    private JPanel connection;
    private JPanel ip;
    private JPanel username;
    private JPanel game;
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
                try {
                    gui.getView().startConnection(connectionType, host);
                    //panel.removeAll();
                } catch (StartConnectionFailedException er) {
                    System.out.println("~> An error during the connection occurred\n   Check your internet connection and retry\n");
                    gui.chooseConnection();
                }

            }
        });

        connectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        hostLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        hostField.setAlignmentX(Component.LEFT_ALIGNMENT);
        nextButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(10)); // Add some space between components
        mainPanel.add(hostLabel);
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
        mainPanel.add(nicknameLabel) ;

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
                nickname = hostField.getText();
                System.out.println("Updated text: " + host);
            }
        });


        // Create a JLabel to display messages
        JLabel messageLabel = new JLabel("");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setForeground(Color.RED);
        mainPanel.add(messageLabel);

        if(nicknameInUse){
            messageLabel.setText("Nickname is already in use.");
            mainPanel.revalidate();
            mainPanel.repaint();
        }

        mainPanel.add(hostField);
        JButton nextButton = new JButton("next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.getView().setPlayerId(nickname);
            }
        });

        mainPanel.add(nextButton);

        panel.revalidate();
    }







    public void chooseGame(ArrayList<String> listOfGames){
        mainPanel.removeAll();
        panel.revalidate();
        panel.repaint();
        this.listOfGames = listOfGames;


        JLabel lobby_JLabel = new JLabel();
        JButton createNewGame_JButton = new JButton();
        JScrollPane jScrollPane2;
        JList<String> games_JList = new JList<>();
        JButton refresh_JButton = new JButton();
        JButton join_JButton = new JButton();



        lobby_JLabel.setFont(new java.awt.Font("Poor Richard", 1, 24)); // NOI18N
        lobby_JLabel.setText("LOBBY");

        createNewGame_JButton.setBackground(new java.awt.Color(82, 168, 167));
        createNewGame_JButton.setFont(new java.awt.Font("Ravie", 0, 14)); // NOI18N
        createNewGame_JButton.setText("CREATE NEW GAME");

        createNewGame_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNewGame();
            }
        });



        games_JList.setModel(new AbstractListModel<String>() {
            public int getSize() { return listOfGames.size(); }
            public String getElementAt(int i) { return listOfGames.get(i); }
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

        refresh_JButton.setBackground(new java.awt.Color(82, 168, 167));
        refresh_JButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        refresh_JButton.setText("Refresh");

        refresh_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui.getView().requestAvailableGames();
                panel.repaint();
            }
        });

        join_JButton.setBackground(new java.awt.Color(82, 168, 167));
        join_JButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        join_JButton.setText("join");

        join_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui.getView().joinGame(selectedGame, 0);
                panel.repaint();
            }
        });

        mainPanel.add(lobby_JLabel);
        mainPanel.add(createNewGame_JButton);
        mainPanel.add(jScrollPane2);
        mainPanel.add(refresh_JButton);
        mainPanel.add(join_JButton);

        panel.revalidate();

    }

    private void createNewGame(){
        mainPanel.removeAll();
        panel.revalidate();
        panel.repaint();



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
                gameName = hostField.getText();
                System.out.println("Updated text: " + host);
            }
        });

        mainPanel.add(hostField);


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
        mainPanel.add(playerComboBox);


        JButton nextButton = new JButton("create");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(listOfGames == null || !listOfGames.contains(gameName)){
                    System.out.println("new game created" + gameName +" " + numPlayers);
                    gui.getView().joinGame(gameName, numPlayers);
                }
            }
        });
        mainPanel.add(nextButton);

        panel.revalidate();

    }


    public void setNicknameInUse(){
        nicknameInUse = true;
    }
}
