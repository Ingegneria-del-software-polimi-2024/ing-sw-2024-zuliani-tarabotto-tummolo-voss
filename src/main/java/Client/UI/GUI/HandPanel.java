package Client.UI.GUI;

import Client.UI.GUI.EventListeners.FlipCardListener;
import Client.UI.GUI.EventListeners.PlaceCardListener;
import Client.UI.GUI.EventListeners.PlaceStarterListener;
import model.cards.PlayableCards.PlayableCard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentListener;
import java.util.List;

public class HandPanel extends JPanel {
    private CardLabel c1;
    private CardLabel c2;
    private CardLabel c3;
    private GUI gui;
    private PlaceStarterListener placeStarterListener;
    private PlaceCardListener placeCardListener;
    private FlipCardListener flipCardListener;

    /**
     * a panel containing three CardLabels representing the player's hand
     * @param gui
     * @param panelHeight
     */
    public HandPanel(GUI gui, int panelHeight){
        this.gui = gui;
        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
        layout.setHgap(20); // Set horizontal gap
        setBackground(new Color(50, 84, 70));
        int cardLength = (int)this.getPreferredSize().getWidth() / 4;
        gui.setCardLength(cardLength);
        c1 = new CardLabel(cardLength);
        c2 = new CardLabel(cardLength);
        c3 = new CardLabel(cardLength);
        this.add(c1);
        this.add(c2);
        this.add(c3);
        this.setBorder(BorderFactory.createEmptyBorder((panelHeight - (int)c1.getPreferredSize().getHeight()) / 2, 0, 0, 0));

        //we create the listeners and we add the flipListener to each cardLabel
        placeCardListener = new PlaceCardListener(gui);
        placeStarterListener = new PlaceStarterListener(gui);
        flipCardListener = new FlipCardListener();

        c1.addMouseListener(flipCardListener);
        c2.addMouseListener(flipCardListener);
        c3.addMouseListener(flipCardListener);
    }

    /**
     * adds a single CardLabel to the panel representing the starter card.
     * This method is only used in the starter card placing phase
     */
    public void addStarterCard(){
        PlayableCard starter = gui.getView().getStarterCard();
        c2.addMouseListener(placeStarterListener);
        c2.updateCard(starter, gui.getFronts().get(starter.getId()), gui.getBacks().get(starter.getId()) );
    }


    @Override
    public Dimension getPreferredSize(){
        return new Dimension((int)(gui.getScreenWidth() * 0.33), (int)(gui.getScreenHeight() * 0.25));
    }
    public void updateHand(){
        c2.removeMouseListener(placeStarterListener);
        List<PlayableCard> hand = gui.getView().getHand();
        hand.get(0).setFaceSide(true);
        hand.get(1).setFaceSide(true);
        c1.updateCard(hand.get(0), gui.getFronts().get(hand.get(0).getId()), gui.getBacks().get(hand.get(0).getId()) );
        c2.updateCard(hand.get(1), gui.getFronts().get(hand.get(1).getId()), gui.getBacks().get(hand.get(1).getId()) );

        if(hand.size() == 3) {
            hand.get(2).setFaceSide(true);
            c3.updateCard(hand.get(2), gui.getFronts().get(hand.get(2).getId()), gui.getBacks().get(hand.get(2).getId()));
        }else{
            c3.updateCard(null, gui.getFronts().get(null), gui.getBacks().get(null));
        }
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int borderWidth = 4;
        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRect(borderWidth/2, borderWidth/2, getWidth() - borderWidth, getHeight() - borderWidth);

        int innerBorder = borderWidth/2;

        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(innerBorder));
        g2d.drawRect((borderWidth*2), (borderWidth*2), getWidth() - 2*(borderWidth*2), getHeight() - 2*(borderWidth*2));

    }


    /**
     * enables the listeners on the CardLabels for selecting a card to place
     */
    public void enableListeners(){
        c1.addMouseListener(placeCardListener);
        c2.addMouseListener(placeCardListener);
        c3.addMouseListener(placeCardListener);
    }

    /**
     * disables the listeners on the CardLabels for selecting a card to place
     */
    public void disableListeners(){
        c1.removeMouseListener(placeCardListener);
        c2.removeMouseListener(placeCardListener);
        c3.removeMouseListener(placeCardListener);
    }

}
