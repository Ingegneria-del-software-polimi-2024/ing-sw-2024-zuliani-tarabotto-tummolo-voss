package Client.UI.GUI;

import Client.UI.GUI.EventListeners.FlipCardListener;
import Client.UI.TUI.HandPrinter;
import model.cards.PlayableCards.PlayableCard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HandPanel extends JPanel {
    private CardLabel c1;
    private CardLabel c2;
    private CardLabel c3;
    private GUI gui;

    public HandPanel(GUI gui){
        this.gui = gui;
        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
        layout.setHgap(20); // Set horizontal gap
        setLayout(layout);
        //setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(new Color(50, 84, 70));
        this.setBorder(BorderFactory.createEmptyBorder(80, 20, 80, 20));
        c1 = new CardLabel();
        c2 = new CardLabel();
        c3 = new CardLabel();
        c1.addMouseListener(new FlipCardListener());
        c2.addMouseListener(new FlipCardListener());
        c3.addMouseListener(new FlipCardListener());

        this.add(c1);
        this.add(c2);
        this.add(c3);
    }

    public void updateHand(){
        List<PlayableCard> hand = gui.getView().getHand();
        hand.get(0).setFaceSide(true);
        hand.get(1).setFaceSide(true);
        hand.get(2).setFaceSide(true);
        c1.updateCard(hand.get(0), gui.getFronts().get(hand.get(0).getId()), gui.getBacks().get(hand.get(0).getId()) );
        c2.updateCard(hand.get(1), gui.getFronts().get(hand.get(1).getId()), gui.getBacks().get(hand.get(1).getId()) );
        c3.updateCard(hand.get(2), gui.getFronts().get(hand.get(2).getId()), gui.getBacks().get(hand.get(2).getId()) );
        //repaint();
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
