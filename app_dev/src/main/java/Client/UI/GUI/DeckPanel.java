package Client.UI.GUI;

import javax.swing.*;
import java.awt.*;

public class DeckPanel extends JPanel {
    private CardLabel goldDeck;
    private CardLabel resourceDeck;
    private CardLabel gold1;
    private CardLabel gold2;
    private CardLabel resource1;
    private CardLabel resource2;

    public DeckPanel(){
        /*setLayout(new FlowLayout(FlowLayout.CENTER));
        //setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(new Color(50, 84, 70));
        c1 = new CardLabel("fungi");
        c2 = new CardLabel("plant");
        c3 = new CardLabel("animal");
        this.add(c1);
        this.add(c2);
        this.add(c3);
*/
        setBackground(new Color(50, 84, 70));
        setLayout(new GridLayout(2, 3,10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        goldDeck = new CardLabel("fungi");
        gold1 = new CardLabel("plant");
        gold2 = new CardLabel("animal");
        resourceDeck = new CardLabel("animal");
        resource1 = new CardLabel("fungi");
        resource2 = new CardLabel("plant");
        add(goldDeck);
        add(gold1);
        add(gold2);
        add(resourceDeck);
        add(resource1);
        add(resource2);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int borderWidth = 2;
        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRect(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth, getHeight() - borderWidth);

    }
}
