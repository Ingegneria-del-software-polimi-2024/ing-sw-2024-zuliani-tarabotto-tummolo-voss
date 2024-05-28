package Client.UI.GUI;

import model.cards.Card;

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

    private HashMap<String, String[]> imgPath;
    private String card;
    private boolean face;
    public CardLabel(String card){
        //setOpaque(true);
        this.card = card;
        face = true;
        imgPath = new HashMap<>();
        imgPath.put("fungi", new String[]{"/Images/cardsFront/fungiFront.png", "/Images/cardsBack/fungiBack.png"});
        imgPath.put("plant", new String[]{"/Images/cardsFront/plantFront.png", "/Images/cardsBack/plantBack.png"});
        imgPath.put("animal", new String[]{"/Images/cardsFront/animalFront.png", "/Images/cardsBack/animalBack.png"});

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


    private void flipCard(){
        face = !face;
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

        int borderWidth = 2;
        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRect(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth, getHeight() - borderWidth);

        BufferedImage img = null;
        try{
            int index;
            if(face) index = 0;
            else index = 1;

            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imgPath.get(card)[index])));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2d.drawImage(img, 0, 0 , getWidth(), getHeight() , this);
    }
}
