package Client.UI.GUI.PlayerBanner;

import Client.UI.GUI.Colors;

import javax.swing.*;
import java.awt.*;

public class NameLabel extends JLabel {

    public NameLabel(Colors color){

    }


    public void draw(Graphics g)  {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /*
        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(0,0, getWidth(), getHeight(), arcWidth, arcHeight);
*/

        /*
        int borderWidth = 10;
        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRect(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth, getHeight() - borderWidth);
*/

        // Draw points in a circle at the top left corner
        /*
        g2d.setColor(new Color(102, 22, 188));
        g2d.fillOval(0,0, 30, 30);
        g2d.setColor(new Color(171, 144, 76));
        g2d.drawString(String.valueOf("10"), 0, 0);


        // Draw resources next to the player image
        g2d.setColor(Color.BLACK);
*/
        //g2d.setColor(Color.BLACK);
        //g2d.drawString("sticazzi", 0, 0);
    }

}
