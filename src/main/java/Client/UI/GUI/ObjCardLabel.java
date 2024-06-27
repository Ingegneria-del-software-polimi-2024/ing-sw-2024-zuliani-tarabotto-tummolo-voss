package Client.UI.GUI;

import model.cards.ObjectiveCard;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The type Obj card label.
 */
public class ObjCardLabel extends JLabel {

    private ObjectiveCard c;
    private BufferedImage front;
    private BufferedImage back;
    private final int cardLength;
    private final float heightLengthRatio =   (float) 2 /3;
    private boolean select = false;
    private final int borderWidth = 4;


    /**
     * JLabel used for representing an ObjectiveCard.
     * The label has the pointer to the specified ObjectiveCard and the corresponding pngs
     *
     * @param cardLength the card length
     */
    public ObjCardLabel(int cardLength){
        this.cardLength = cardLength;
        setOpaque(false);
    }

    /**
     * method that changes the card that the JLabel is representing
     *
     * @param c     the c
     * @param front the front
     * @param back  the back
     */
    public void updateCard(ObjectiveCard c, BufferedImage front, BufferedImage back){
        this.c = c;
        this.back = back;
        this.front = front;
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


        g2d.drawImage(front, 0, 0 , getWidth(), getHeight() , this);

        if(select){
            g2d.setColor(Color.green);
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.drawRoundRect(borderWidth/2 - 1, borderWidth/2 - 1, getWidth() - borderWidth + 1, getHeight() - borderWidth + 1, 13, 13);
        }
    }

    /**
     * returns the card represented by this label at this moment
     *
     * @return objective card
     */
    public ObjectiveCard getCard(){return c;}

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
