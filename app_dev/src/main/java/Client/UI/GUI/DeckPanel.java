package Client.UI.GUI;

import Client.UI.GUI.EventListeners.DrawCardListener;
import model.cards.PlayableCards.GoldCard;
import model.cards.PlayableCards.PlayableCard;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel that collects all the cards that can be drawn by the player
 */
public class DeckPanel extends JPanel {
    private GUI gui;
    private CardLabel goldDeck;
    private CardLabel resourceDeck;
    private CardLabel gold1;
    private CardLabel gold2;
    private CardLabel resource1;
    private CardLabel resource2;
    private DrawCardListener drawCardListener;


    /**
     * the cards are organized using a gridLayout
     * @param gui
     */
    public DeckPanel(GUI gui){
        this.gui = gui;
        int cardLength = (int)this.getPreferredSize().getWidth() / 4;
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        setBackground(new Color(50, 84, 70));
        mainPanel.setLayout(new GridLayout(2, 3,10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        goldDeck = new CardLabel(cardLength);
        gold1 = new CardLabel(cardLength);
        gold2 = new CardLabel(cardLength);
        resourceDeck = new CardLabel(cardLength);
        resource1 = new CardLabel(cardLength);
        resource2 = new CardLabel(cardLength);

        //gold deck
        mainPanel.add(goldDeck);
        goldDeck.setCardSource(1);
        //open gold 1
        mainPanel.add(gold1);
        gold1.setCardSource(2);
        //open gold 2
        mainPanel.add(gold2);
        gold2.setCardSource(3);
        //resources deck
        mainPanel.add(resourceDeck);
        resourceDeck.setCardSource(4);
        //open resources 1
        mainPanel.add(resource1);
        resource1.setCardSource(5);
        //open resources 2
        mainPanel.add(resource2);
        resource2.setCardSource(6);

        drawCardListener = new DrawCardListener(gui);
        this.add(mainPanel);

    }

    /**
     * all the cardLabel panels in the deckPanel are updated after a card was drawn
     */
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
    public Dimension getPreferredSize(){
        return new Dimension((int)(gui.getScreenWidth() * 0.33), (int)(gui.getScreenHeight() * 0.25));
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


    /**
     * enables each cardLabel's listener for drawing the card
     */
    public void enableListeners(){
        goldDeck.addMouseListener(drawCardListener);
        gold1.addMouseListener(drawCardListener);
        gold2.addMouseListener(drawCardListener);
        resourceDeck.addMouseListener(drawCardListener);
        resource1.addMouseListener(drawCardListener);
        resource2.addMouseListener(drawCardListener);
    }

    /**
     * disables each cardLabel's listener for drawing the card
     */
    public void disableListeners(){
        goldDeck.removeMouseListener(drawCardListener);
        gold1.removeMouseListener(drawCardListener);
        gold2.removeMouseListener(drawCardListener);
        resourceDeck.removeMouseListener(drawCardListener);
        resource1.removeMouseListener(drawCardListener);
        resource2.removeMouseListener(drawCardListener);
    }
}
