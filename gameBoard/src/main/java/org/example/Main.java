package org.example;

import org.example.GamePanel;
import org.example.HandDeckPanel;

import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Board");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main container panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create the top panel (non-scrollable)
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(800, 100));
        topPanel.setBackground(Color.LIGHT_GRAY); // Just for visualization

        // Create the bottom panel (non-scrollable)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(800, 100));
        bottomPanel.setBackground(Color.LIGHT_GRAY); // Just for visualization

        // Create the game panel (scrollable)
        JPanel gamePanel = new JPanel() {
            private Point dragStart; // Store the starting point of the drag

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 400);
            }

            @Override
            public void update(Graphics g) {
                paint(g);
            }
        };

        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

        // Add some components to the game panel (just for visualization)
        for (int i = 0; i < 20; i++) {
            JButton piece = new JButton("Piece " + (i + 1));
            piece.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    gamePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    gamePanel.setCursor(Cursor.getDefaultCursor());
                }
            });
            gamePanel.add(piece);
        }

        // Create the scroll pane and add the game panel to it
        JScrollPane scrollPane = new JScrollPane(gamePanel);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        // Create the dock panel for pieces (non-scrollable)
        JPanel dockPanel = new JPanel();
        dockPanel.setPreferredSize(new Dimension(100, 600));
        dockPanel.setBackground(Color.GRAY); // Just for visualization

        // Add some components to the dock panel (just for visualization)
        for (int i = 0; i < 10; i++) {
            dockPanel.add(new JButton("Docked Piece " + (i + 1)));
        }

        // Add the top panel and bottom panel to the main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Create a container panel for the scrollable and non-scrollable sections
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Add the dock panel to the left side (non-scrollable)
        contentPanel.add(dockPanel, BorderLayout.WEST);

        //MAIN GAME PANEL
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the content panel to the main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add the main panel to the window
        window.add(mainPanel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
