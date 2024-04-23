package view.panels;

import view.labels.CardLabel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class BoardPanel extends JPanel {
    //private CardLabel[] placementArea;
    private HashMap<Point, CardLabel> placementArea; // Map to store card positions
    private final double resize = 0.5;

    public BoardPanel(int rows, int cols) {
        setLayout(null); // disable layout manager
        placementArea = new HashMap<>(); // Initialize card map
        CardLabel card1 = new CardLabel("1", "prova", Color.blue);
        CardLabel card2 = new CardLabel("2", "prova", Color.RED);
        CardLabel card3 = new CardLabel("3", "prova", Color.green);
        addCard(card1,0, 0);
        addCard(card2,1, 1);
        addCard(card3,2, 0);
        setBackground(Color.gray);

    }

    public void addCard(CardLabel card, int row, int col) {
        placementArea.put(new Point(col, row), card); // Store card position in the map
        card.setBounds((int)(resize * col * (card.getPreferredSize().getWidth()- 60)), (int)(resize * row * (card.getPreferredSize().getHeight() - 60)), (int)(resize * card.getPreferredSize().getWidth()), (int)(resize * card.getPreferredSize().getHeight()));
        this.add(card); // Add card label to the panel
        //revalidate(); // Revalidate the panel to reflect changes
        //repaint(); // Repaint the panel
    }
}
