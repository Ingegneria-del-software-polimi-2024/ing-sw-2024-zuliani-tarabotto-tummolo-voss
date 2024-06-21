package Client.UI.GUI;

import model.cards.PlayableCards.PlayableCard;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class CardLabel extends JLabel {
    private PlayableCard c;
    private BufferedImage front;
    private BufferedImage back;
    private int cardLength;
    private int cardHeight;
    private final float heightLengthRatio =   (float) 2 /3;
    private int cardSource;
    private final int borderWidth = 4;
    private boolean select;


    /**
     * JLabel used for representing a PlayableCard.
     * The label has the pointer to the specified PlayableCard and the corresponding pngs
     * @param cardLength
     */
    public CardLabel(int cardLength){
        this.cardLength = cardLength;
        cardHeight = (int)(cardLength * heightLengthRatio);
        setOpaque(false);
        setMaximumSize(new Dimension(cardLength, cardHeight));
        setMinimumSize(new Dimension(cardLength, cardHeight));
    }

    /**
     * method used to change the JLabel's corresponding card
     * @param c
     * @param front
     * @param back
     */
    public void updateCard(PlayableCard c, BufferedImage front, BufferedImage back){
        this.c = c;
        this.back = back;
        this.front = front;
        select = false;
    }

    /**
     * changes the card faceSide and consequently the displayed image
     */
    public void flipCard(){
        if(c == null) return;
        c.setFaceSide(!c.getFaceSide());
    }

    /**
     * method used to set the card's position in the deckPanel
     * @param cardSource
     */
    public void setCardSource(int cardSource){
        this.cardSource = cardSource;
    }

    /**
     * method used to get the card's position in the deck Panel
     * @return
     */
    public int getCardSource(){return cardSource;}


    /**
     * returns the card represented by this label at this moment
     * @return
     */
    public PlayableCard getCard(){
        return c;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(cardLength, (int)(cardLength * heightLengthRatio));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(c == null) return;
        if(c.getFaceSide()) g2d.drawImage(front, 0, 0 , getWidth(), getHeight(), this);
        else g2d.drawImage(back, 0,  0, getWidth(), getHeight(), this);

        if(select){
            g2d.setColor(Color.green);
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.drawRoundRect(borderWidth/2 - 1, borderWidth/2 - 1, getWidth() - borderWidth + 1, getHeight() - borderWidth + 1, 13, 13);
        }

    }

    /**
     * when the card is in the handPanel and the mouse goes over it, this method is called to surround the card with
     * a green border
     */
    public void highLight(){
        this.select = true;
    }

    /**
     * when the mouse exits the card label, the border is eliminated
     */
    public void unHighLight(){
        this.select = false;
    }

}
