package Client.UI.GUI;

import Client.UI.GUI.PlayerBanner.IconLabel;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;



public class PlayerPanel extends JPanel {
    private BufferedImage playerImage;
    private int points;
    private int resource;
    private int imageDim = (int)(getHeight() * 0.8);
    private int imagePos = (getHeight() - imageDim) / 2;
    BufferedImage[] icons;

    public PlayerPanel(List<String> paths, int[] numbers, int width, int height) {

        imageDim = (int)(height * 0.8);
        imagePos = (height - imageDim) / 2;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        //setBorder(BorderFactory.createEmptyBorder((int)(height * 0.05), (int)(height * 0.05), (int)(height * 0.05), (int)(height * 0.05)));
        setBorder(BorderFactory.createEmptyBorder(imagePos, 2*imagePos + imageDim, 0, 0));

        icons = new BufferedImage[7];

        test t = new test("/Images/playerIcon/icon2.jpeg",paths, numbers);
        //t.setPreferredSize(new Dimension((int)(width * 0.5), (int)(height* 0.7)));
        add(t);


        setBackground(new Color(218, 211, 168));

        setOpaque(true);



        try {
            playerImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/playerIcon/icon2.jpeg")));
            //playerImage = createRoundedImage(img, 100);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setPlayerImage(BufferedImage image) {
        //this.playerImage = createRoundedImage(image, 50);
        repaint();
    }

    public void setPoints(int points) {
        this.points = points;
        repaint();
    }

    public void setResources(int resources) {
        this.resource = resources;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define the bounds and properties for the rounded rectangle
        int rectX = 10;
        int rectY = 10;
        int rectWidth = 100;
        int rectHeight = 100;
        int arcWidth = 10;
        int arcHeight = 10;


        // Draw the player image within the rounded rectangle
        if (playerImage != null) {
            System.out.println("image ");
            //g2d.setClip(new RoundRectangle2D.Double(imagePos, imagePos, imageDim + 5, imageDim + 5, 20, 20));
            g2d.drawImage(playerImage, imagePos, imagePos, imageDim , imageDim , this); // Adjust padding as needed
        }


        //we draw the rounded rectangle around the image
        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(imagePos, imagePos, imageDim, imageDim, arcWidth, arcHeight);


        //we draw the rectangle around the banner
        int borderWidth = 2;
        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRect(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth, getHeight() - borderWidth);


        // Draw points in a circle at the top left corner
        g2d.setColor(new Color(102, 22, 188));
        g2d.fillOval(imagePos - 5 , imagePos - 5, 30, 30);
        g2d.setColor(new Color(171, 144, 76));
        g2d.drawString(String.valueOf(points), imagePos  + 5, imagePos + 10 );

   }




}

