package Client.UI.GUI;

import Client.UI.TUI.HandPrinter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class HandPanel extends JPanel {
    private String card;
    private CardLabel c1;
    private CardLabel c2;
    private CardLabel c3;

    public HandPanel(){
        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
        layout.setHgap(20); // Set horizontal gap
        setLayout(layout);
        //setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(new Color(50, 84, 70));
        this.setBorder(BorderFactory.createEmptyBorder(80, 20, 80, 20));
        c1 = new CardLabel("fungi");
        c2 = new CardLabel("plant");
        c3 = new CardLabel("animal");

        this.add(c1);
        this.add(c2);
        this.add(c3);


    }

    public void updateCard(String c){
        this.card = c;
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

    }

}
