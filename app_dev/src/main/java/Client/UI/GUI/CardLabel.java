package Client.UI.GUI;

import model.cards.Card;
import model.cards.PlayableCards.PlayableCard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class CardLabel extends JLabel {
    private PlayableCard c;
    private BufferedImage front;
    private BufferedImage back;


    public CardLabel(){
        setOpaque(false);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform when the label is clicked
                flipCard();
                repaint();
                System.out.println("flip");
            }
        });
    }

    public void updateCard(PlayableCard c, BufferedImage front, BufferedImage back){
        this.c = c;
        this.back = back;
        this.front = front;
    }

    private void flipCard(){
        c.setFaceSide(!c.getFaceSide());
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

        /*int borderWidth = 2;
        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRect(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth, getHeight() - borderWidth);
*/
        if(c.getFaceSide()) g2d.drawImage(front, 0, 0 , getWidth(), getHeight() , this);
        else g2d.drawImage(back, 0, 0 , getWidth(), getHeight() , this);
    }
}
