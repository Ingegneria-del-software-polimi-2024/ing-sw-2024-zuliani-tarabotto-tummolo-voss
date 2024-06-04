package Client.UI.GUI;

import model.cards.PlayableCards.PlayableCard;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class CardLabel extends JLabel {
    private PlayableCard c;
    private BufferedImage front;
    private BufferedImage back;
    private final int cardLength = 140;
    private final float heightLengthRatio =   (float) 2 /3;
    private int cardSource;
    private final int borderWidth = 4;
    private boolean select;


    public CardLabel(){
        setOpaque(false);
    }

    public void updateCard(PlayableCard c, BufferedImage front, BufferedImage back){
        this.c = c;
        this.back = back;
        this.front = front;
        select = false;
    }

    public void flipCard(){
        c.setFaceSide(!c.getFaceSide());
    }

    public void setCardSource(int cardSource){
        this.cardSource = cardSource;
    }

    public int getCardSource(){return cardSource;}

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

    public void highLight(){
        this.select = true;
    }

    public void unHighLight(){
        this.select = false;
    }

}
