package Client.UI.GUI.PlayerBanner;

import Client.UI.GUI.Colors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class IconLabel extends JLabel {

    private Colors color;
    private BufferedImage icon;

    public IconLabel(Colors color){
        setOpaque(false);
        this.color = color;

        try {
            icon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/playerIcon/icon2.jpeg")));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public IconLabel(BufferedImage img) {
        //this.icon =  img;
        setOpaque(false);
        try {
            icon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/playerIcon/icon2.jpeg")));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the player image within the rounded rectangle
        if (icon != null) {
            System.out.println("image ");
            g2d.drawImage(icon, 0, 0, 200, 200, this ); // Adjust padding as needed
        }
    }

}
