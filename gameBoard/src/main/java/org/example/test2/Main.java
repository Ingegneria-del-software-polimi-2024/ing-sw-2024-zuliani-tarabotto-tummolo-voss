package org.example.test2;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create and initialize the game panel
            GamePanel gamePanel = new GamePanel();

            // Add pieces to the game panel
            gamePanel.setPieces();

            // Create a JFrame to hold the game panel
            JFrame frame = new JFrame("Chess Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(gamePanel);
            frame.pack();
            frame.setLocationRelativeTo(null); // Center the JFrame
            frame.setVisible(true);

            // Launch the game loop
            gamePanel.launchGame();
        });
    }
}
