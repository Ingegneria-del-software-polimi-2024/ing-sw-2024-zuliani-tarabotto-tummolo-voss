package view.panels;

import view.labels.CardLabel;

import javax.swing.*;
import java.awt.*;

public class HandPanel extends JPanel {

    private CardLabel[] cards;
    public HandPanel() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));//the layout for this panel is set
        cards = new CardLabel[3];//we create an array of three cardLabel
        //we initialize each CardLabel and add it to this HandPanel
        for (int i = 0; i < 3; i++) {
            cards[i] = new CardLabel(String.valueOf(i), "prova", Color.blue);
            //cards[i].setIcon(new ImageIcon("card_back.png")); // Placeholder image
            this.add(cards[i]);
        }
        setBackground(Color.red);

    }
}
