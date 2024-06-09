/*
 * Created by JFormDesigner on Tue May 28 11:56:52 CEST 2024
 */

package Client.UI.GUI;

import java.awt.*;
import javax.swing.*;

/**
 * @author nicol
 */
public class Lobby extends JFrame {
    private JPanel panel1;
    private JScrollPane gameScrollPane;
    private JList<String> gameList;
    private JButton refreshButton;
    private JButton createNewGameButton;
    private JLabel lobbyLabel;

    public Lobby() {
        initComponents();
    }

    private void initComponents() {
        panel1 = new JPanel();
        gameScrollPane = new JScrollPane();
        gameList = new JList<>();
        refreshButton = new JButton();
        createNewGameButton = new JButton();
        lobbyLabel = new JLabel();

        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        panel1.setBackground(new Color(0xd2cd89));
        panel1.setLayout(null);

        gameList.setBackground(new Color(0xada038));
        gameScrollPane.setViewportView(gameList);
        panel1.add(gameScrollPane);
        gameScrollPane.setBounds(0, 85, 785, 265);

        refreshButton.setText("Refresh the Page");
        refreshButton.setBackground(new Color(0xada038));
        panel1.add(refreshButton);
        refreshButton.setBounds(new Rectangle(new Point(665, 55), refreshButton.getPreferredSize()));

        createNewGameButton.setText("CREATE NEW GAME");
        createNewGameButton.setBackground(new Color(0xada038));
        panel1.add(createNewGameButton);
        createNewGameButton.setBounds(320, 385, 165, createNewGameButton.getPreferredSize().height);

        lobbyLabel.setText("LOBBY");
        lobbyLabel.setFont(new Font("Ravie", Font.PLAIN, 18));
        panel1.add(lobbyLabel);
        lobbyLabel.setBounds(350, 40, 120, lobbyLabel.getPreferredSize().height);

        Dimension preferredSize = new Dimension();
        for (int i = 0; i < panel1.getComponentCount(); i++) {
            Rectangle bounds = panel1.getComponent(i).getBounds();
            preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
            preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
        }
        Insets insets = panel1.getInsets();
        preferredSize.width += insets.right;
        preferredSize.height += insets.bottom;
        panel1.setMinimumSize(preferredSize);
        panel1.setPreferredSize(preferredSize);

        contentPane.add(panel1);
        panel1.setBounds(0, 0, 790, 445);

        preferredSize = new Dimension();
        for (int i = 0; i < contentPane.getComponentCount(); i++) {
            Rectangle bounds = contentPane.getComponent(i).getBounds();
            preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
            preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
        }
        insets = contentPane.getInsets();
        preferredSize.width += insets.right;
        preferredSize.height += insets.bottom;
        contentPane.setMinimumSize(preferredSize);
        contentPane.setPreferredSize(preferredSize);

        pack();
        setLocationRelativeTo(getOwner());
    }

    public JList<String> getGameList() {
        return gameList;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JButton getCreateNewGameButton() {
        return createNewGameButton;
    }

    public static void main(String[] args) {
        // Esegui l'interfaccia grafica in un thread separato per evitare problemi di threading
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Crea un'istanza di Lobby
                    Lobby lobby = new Lobby();

                    // Mostra la finestra della lobby
                    lobby.setVisible(true);

                    // Per testare la visualizzazione dei giochi, possiamo aggiungere alcuni giochi alla lista
                    DefaultListModel<String> listModel = new DefaultListModel<>();
                    listModel.addElement("Game 1");
                    listModel.addElement("Game 2");
                    listModel.addElement("Game 3");
                    lobby.getGameList().setModel(listModel);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

