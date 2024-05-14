package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ListSelectionEvent;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

public class Lobby extends JFrame {

    private JLabel titleLabel;
    private JList<String> gameList;

    public Lobby() {
        super("Lobby");

        // Create components
        titleLabel = new JLabel("Card Game Lobby");
        gameList = new JList<>(new String[]{"Game 1", "Game 2", "Game 3"});

        // Set layout
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add components to main container
        getContentPane().add(titleLabel, BorderLayout.NORTH);
        getContentPane().add(gameList, BorderLayout.CENTER);

        // Customize components
        gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gameList.addListSelectionListener(e -> {
            // Handle game selection
            if (e.getValueIsAdjusting()) return;
            String selectedGame = gameList.getModel().getElementAt(e.getFirstIndex());
            // Update game panel or initiate game based on selection
        });

        // ... (rest of the code)
    }

    public static void main(String[] args) {
        Lobby lobby = new Lobby();
        lobby.setSize(800, 600);
        lobby.setLocationRelativeTo(null);
        lobby.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lobby.setVisible(true);
    }
}

/*public class Lobby extends JFrame implements ActionListener {

    private DefaultListModel<String> gamesListModel;
    private JList<String> gamesList;
    private JButton createGameButton;

    public Lobby() {
        super("Lobby");

        // Create and configure components
        gamesListModel = new DefaultListModel<>();
        gamesList = new JList<>(gamesListModel);
        createGameButton = new JButton("Create Game");

        // Add components to the panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(gamesList);
        panel.add(scrollPane, BorderLayout.CENTER);

        panel.add(createGameButton, BorderLayout.SOUTH);

        add(panel);

        // Set layout, size, and other properties of the frame
        setSize(400, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add action listener to the button
        createGameButton.addActionListener(this);

        // Update the games list on startup (example)
        updateGamesList();
    }

    private void updateGamesList() {
        // Update the games list with available games (implement your logic)
        gamesListModel.clear(); // Clears the list

        // Example of adding dummy games
        gamesListModel.addElement("Room 1 - 2/4 players");
        gamesListModel.addElement("Room 2 - 1/4 players");
        gamesListModel.addElement("Room 3 - 0/4 players");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createGameButton) {
            // Create a new game (implement your logic)
            JOptionPane.showMessageDialog(null, "Creating new game...");
            // Close the Lobby window (optional)


        }
    }

    /*@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JList.ListSelectionModel) {
            // Clic su una stanza della lista
            int selectedIndex = gamesList.getSelectedIndex();
            if (selectedIndex != -1) {
                String gameName = gamesListModel.getElementAt(selectedIndex);
                // Apri la finestra di gioco corrispondente alla stanza selezionata
                new GameWindow(gameName);
            }
        } else if (e.getSource() == createGameButton) {
            // Clic sul pulsante "Crea Partita"
            // Apri la finestra di gioco per creare una nuova partita
            new GameWindow("Nuova Partita");
        }
    }*/

   /* public static void main(String[] args) {
        new Lobby();
    }
}*/