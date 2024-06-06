package Client.UI.GUI;

import model.cards.ObjectiveCard;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ObjCardLabel extends JLabel {

    private ObjectiveCard c;
    private BufferedImage front;
    private BufferedImage back;
    private final int cardLength = 140;
    private final float heightLengthRatio =   (float) 2 /3;
    private boolean select = false;
    private final int borderWidth = 4;

    public ObjCardLabel(){
        setOpaque(false);
    }

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

    public ObjectiveCard getCard(){return c;}

    public void highLight(){
        this.select = true;
    }

    public void unHighLight(){
        this.select = false;
    }

}
