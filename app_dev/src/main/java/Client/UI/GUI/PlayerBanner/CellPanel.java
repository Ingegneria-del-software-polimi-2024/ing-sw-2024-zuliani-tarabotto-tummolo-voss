package Client.UI.GUI.PlayerBanner;


    import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CellPanel extends JPanel {
    private BufferedImage img;
    private String text;

    public CellPanel(BufferedImage img, String text) {
        setOpaque(false);
        this.text = text;
        //setPreferredSize(new Dimension(400, 200)); // Set preferred size of the panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing for smoother text and image rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the image on the left
        if (img != null) {
            g2d.drawImage(img, 0, 0, 100, 200, this); // Draw image at position (10, 10)
        }

        // Set the font and color for the text
        g2d.setFont(new Font("Serif", Font.BOLD, 24));
        g2d.setColor(Color.BLACK);

        // Draw the text on the right side of the image
        if (img != null) {
            g2d.drawString(text, img.getWidth() + 20, img.getHeight() / 2 + 10);
        } else {
            g2d.drawString(text, 20, 30); // Fallback position if the image is not loaded
        }
    }
}

