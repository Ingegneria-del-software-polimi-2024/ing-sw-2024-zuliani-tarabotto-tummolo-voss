package Client.UI.GUI;

import model.cards.ObjectiveCard;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ObjCardLabel extends JLabel {

    private ObjectiveCard c;
    private BufferedImage front;
    private BufferedImage back;

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
        return new Dimension(200, 120);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(front, 0, 0 , getWidth(), getHeight() , this);
    }

}
