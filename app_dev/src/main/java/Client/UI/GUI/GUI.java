package Client.UI.GUI;

import Client.UI.TUI.*;
import Client.UI.TUI.Commands.Command;
import Client.UI.TUI.Commands.DispositionCommand;
import Client.UI.TUI.Commands.EndGameCommand;
import Client.UI.TUI.Commands.HelpCommand;
import Client.UI.UI;
import Client.View.ViewAPI;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GUI implements UI{

    private ViewAPI view;
    private final Object lock;
    private final HashMap<String, Command> commandMap;
    private final Object lockForControllingCommands;
    private DefaultListModel<String> listModel;
    LoginPage loginPage;
    private Lobby lobby;

    public GUI(ViewAPI view) {
        this.view = view;
        lock = new Object();
        lockForControllingCommands = new Object();
        this.commandMap = new HashMap<>();
        this.loginPage = new LoginPage(this);
        this.loginPage.setVisible(true);
        this.listModel = new DefaultListModel<>();
        this.lobby.getGameList().setModel(listModel);

        initListeners();
    }
    @Override
    public void displayInitialization() {

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
        String connectionType = loginPage.getSelectedConnectionType();
        String host = loginPage.getHost();

        if (!validIP(host)) {
            JOptionPane.showMessageDialog(null, "Invalid IP address. Please enter a valid IP address (xxx.xxx.xxx.xxx).");
            return;
        }

        try {
            view.startConnection(connectionType, host);
            loginPage.dispose(); // Chiudi la finestra di login
            Lobby lobby = new Lobby();
            lobby.setVisible(true);
        } catch (StartConnectionFailedException e) {
            JOptionPane.showMessageDialog(null, "An error occurred during the connection. Check your internet connection and try again.");
        }
    }

    private boolean validIP(String ip) {
        if (ip.equals("localhost"))
            return true;
        String desiredFormat = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ip.matches(desiredFormat);
    }


    @Override
    public void askNickname() {
        String nickname = loginPage.getNickname();
        if (nickname != null && !nickname.trim().isEmpty()) {
            view.setPlayerId(nickname);
        } else {
            JOptionPane.showMessageDialog(null, "Nickname cannot be empty. Please enter a valid nickname.");
            askNickname();
        }
    }

    @Override
    public void nickNameAlreadyInUse() {
        JOptionPane.showMessageDialog(null, "This nickname is already in use, please choose a different nickname.");
        askNickname();
    }

    private void initListeners() {
        lobby.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.requestAvailableGames();
            }
        });

        lobby.getCreateNewGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewGame();
            }
        });

        lobby.getGameList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedGame = lobby.getGameList().getSelectedValue();
                    if (selectedGame != null) {
                        view.joinGame(selectedGame, 0);
                    }
                }
            }
        });

        lobby.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshGameList();
            }
        });
    }

    public void displayAvailableGames(ArrayList<String> listOfGames) {
        listModel.clear();
        if (listOfGames != null && !listOfGames.isEmpty()) {
            for (String game : listOfGames) {
                listModel.addElement(game);
            }
        }

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String game : listOfGames) {
            listModel.addElement(game);
        }
        lobby.getGameList().setModel(listModel);
    }

    private void createNewGame() {
        String game = JOptionPane.showInputDialog(lobby, "Enter the name of the new game:");
        if (game != null && !game.trim().isEmpty()) {
            int nPlayers = 0;
            String players;
            boolean flag;
            do {
                players = JOptionPane.showInputDialog(lobby, "Insert the number of players (2-4):");
                try {
                    nPlayers = Integer.parseInt(players);
                    flag = (nPlayers < 2 || nPlayers > 4);
                } catch (Exception e) {
                    flag = true;
                }
                if (flag) {
                    JOptionPane.showMessageDialog(lobby, "The game must contain at least 2 players and maximum 4 players.");
                }
            } while (flag);

            view.joinGame(game, nPlayers);
        }
    }

    public void showLobby() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                lobby.setVisible(true);
            }
        });
    }


    private void refreshGameList() {
        // Richiede al viewAPI di aggiornare la lista dei giochi disponibili
        view.requestAvailableGames();
    }

    @Override
    public void joinedGame(String gameID) {

    }

    @Override
    public void firstWelcome() {

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

    @Override
    public void run() {

    }
}