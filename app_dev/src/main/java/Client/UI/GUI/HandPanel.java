package Client.UI.GUI;

import Client.UI.GUI.EventListeners.FlipCardListener;
import model.cards.PlayableCards.PlayableCard;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HandPanel extends JPanel {
    private CardLabel c1;
    private CardLabel c2;
    private CardLabel c3;
    private GUI gui;

    public HandPanel(GUI gui, int panelHeight){
        System.out.println(panelHeight);
        this.gui = gui;
        c1 = new CardLabel();
        c2 = new CardLabel();
        c3 = new CardLabel();
        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
        layout.setHgap(20); // Set horizontal gap
        //setLayout(layout);
        //setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(new Color(243, 238, 205));

        this.setBorder(BorderFactory.createEmptyBorder((panelHeight - (int)c1.getPreferredSize().getHeight()) / 2, 0, 0, 0));
        c1.addMouseListener(new FlipCardListener());
        c2.addMouseListener(new FlipCardListener());
        c3.addMouseListener(new FlipCardListener());
        this.add(c1);
        this.add(c2);
        this.add(c3);

    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension((int)(gui.getScreenWidth() * 0.33), (int)(gui.getScreenHeight() * 0.25));
    }
    public void updateHand(){
        List<PlayableCard> hand = gui.getView().getHand();
        hand.get(0).setFaceSide(true);
        hand.get(1).setFaceSide(true);
        hand.get(2).setFaceSide(true);
        c1.updateCard(hand.get(0), gui.getFronts().get(hand.get(0).getId()), gui.getBacks().get(hand.get(0).getId()) );
        c2.updateCard(hand.get(1), gui.getFronts().get(hand.get(1).getId()), gui.getBacks().get(hand.get(1).getId()) );
        c3.updateCard(hand.get(2), gui.getFronts().get(hand.get(2).getId()), gui.getBacks().get(hand.get(2).getId()) );
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int borderWidth = 4;
        g2d.setColor(new Color(164, 153, 59));
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRect(borderWidth/2, borderWidth/2, getWidth() - borderWidth, getHeight() - borderWidth);

        int innerBorder = borderWidth/2;

        g2d.setColor(new Color(45, 33, 24));
        g2d.setStroke(new BasicStroke(innerBorder));
        g2d.drawRect((borderWidth*2), (borderWidth*2), getWidth() - 2*(borderWidth*2), getHeight() - 2*(borderWidth*2));

    }

}
