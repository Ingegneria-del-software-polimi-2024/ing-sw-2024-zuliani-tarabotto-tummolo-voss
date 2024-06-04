package org.example;


import javax.swing.*;
import java.awt.*;

public class ChessApplication {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel2 gamePanel = new GamePanel2();
        OutsidePanel outsidePanel = new OutsidePanel();

        frame.setLayout(new BorderLayout());
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(outsidePanel, BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);

        gamePanel.launchGame();
    }
}
