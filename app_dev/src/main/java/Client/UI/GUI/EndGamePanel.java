package Client.UI.GUI;

import javax.swing.*;
import java.awt.*;

public class EndGamePanel extends JPanel {

    private GUI gui;

    public EndGamePanel(GUI gui){
        this.gui = gui;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setBackground(new Color(218, 211, 168));;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel scoreLabel = new JLabel("Scores: Player 1 - 10, Player 2 - 8");
        scoreLabel.setFont(new Font("Serif", Font.BOLD, 70));

        JButton closeButton = new JButton("Close Game");
        closeButton.addActionListener(e -> System.exit(0));

        JButton lobbyButton = new JButton("Lobby");
        lobbyButton.addActionListener(e -> System.out.println("back to lobby"));

        add(scoreLabel, gbc);
        add(closeButton, gbc);
        add(lobbyButton, gbc);

    }
}
