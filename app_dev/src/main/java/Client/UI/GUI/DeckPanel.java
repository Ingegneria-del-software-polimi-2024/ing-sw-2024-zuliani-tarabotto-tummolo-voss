package Client.UI.GUI;

import Client.UI.GUI.EventListeners.DrawCardListener;
import model.cards.PlayableCards.GoldCard;
import model.cards.PlayableCards.PlayableCard;

import javax.swing.*;
import java.awt.*;

public class DeckPanel extends JPanel {
    private GUI gui;
    private CardLabel goldDeck;
    private CardLabel resourceDeck;
    private CardLabel gold1;
    private CardLabel gold2;
    private CardLabel resource1;
    private CardLabel resource2;

    public DeckPanel(GUI gui){
        this.gui = gui;
        setBackground(new Color(50, 84, 70));
        setLayout(new GridLayout(2, 3,10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        goldDeck = new CardLabel();
        gold1 = new CardLabel();
        gold2 = new CardLabel();
        resourceDeck = new CardLabel();
        resource1 = new CardLabel();
        resource2 = new CardLabel();
        this.add(goldDeck);
        goldDeck.setCardSource(1);
        this.add(resourceDeck);
        resourceDeck.setCardSource(4);
        this.add(gold1);
        gold1.setCardSource(2);
        this.add(resource1);
        resource1.setCardSource(5);
        this.add(gold2);
        gold2.setCardSource(3);
        this.add(resource2);
        resource2.setCardSource(6);

        goldDeck.addMouseListener(new DrawCardListener(gui));

    }

    public void updateDecks(){
        PlayableCard c1 = gui.getView().getGoldDeck().get(0);
        PlayableCard c2 = gui.getView().getOpenGold().get(0);
        PlayableCard c3 = gui.getView().getOpenGold().get(1);
        PlayableCard c4 = gui.getView().getResourceDeck().get(0);
        PlayableCard c5 = gui.getView().getOpenResource().get(0);
        PlayableCard c6 = gui.getView().getOpenResource().get(1);

        c1.setFaceSide(false);
        c2.setFaceSide(true);
        c3.setFaceSide(true);
        c4.setFaceSide(false);
        c5.setFaceSide(true);
        c6.setFaceSide(true);

        goldDeck.updateCard(c1, gui.getFronts().get(c1.getId()), gui.getBacks().get(c1.getId()) );
        gold1.updateCard(c2, gui.getFronts().get(c2.getId()), gui.getBacks().get(c2.getId()) );
        gold2.updateCard(c3, gui.getFronts().get(c3.getId()), gui.getBacks().get(c3.getId()) );

        resourceDeck.updateCard(c4, gui.getFronts().get(c4.getId()), gui.getBacks().get(c4.getId()));
        resource1.updateCard(c5, gui.getFronts().get(c5.getId()), gui.getBacks().get(c5.getId()));
        resource2.updateCard(c6, gui.getFronts().get(c6.getId()), gui.getBacks().get(c6.getId()));

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

        g2d.setStroke(new BasicStroke(innerBorder));
        g2d.drawRect((borderWidth*2), (borderWidth*2), getWidth() - 2*(borderWidth*2), getHeight() - 2*(borderWidth*2));

    }
}
