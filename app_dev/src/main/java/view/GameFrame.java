package view;

import view.panels.BoardPanel;
import view.panels.HandPanel;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private HandPanel handPanel;
    private BoardPanel boardPanel;


    public GameFrame() {
        setTitle("Card Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        handPanel = new HandPanel();
        //add(handPanel);
        boardPanel = new BoardPanel(3,3);
        //add(boardPanel);
        // Create container panel
        JPanel containerPanel = new JPanel(new BorderLayout());//un panel che contiene i due panel BoardPanel e HandPanel
        containerPanel.add(handPanel, BorderLayout.SOUTH);
        containerPanel.add(boardPanel, BorderLayout.CENTER);

        // Add container panel to the frame
        add(containerPanel);
        setVisible(true);

    }

}
