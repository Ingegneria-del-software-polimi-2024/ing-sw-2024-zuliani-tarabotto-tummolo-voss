package Client.UI.GUI.PlayerBanner;

import Client.UI.GUI.Colors;
import Client.UI.GUI.PlayerPanel;
import Client.UI.TUI.Commands.Command;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PlayerBanner extends JPanel {

    private int width;
    private int height;
    private Colors color;;
    private int iconDimension;
    private int iconX;
    private BufferedImage icon;
    private int nameX;
    private int nameHeight;
    private int nameWidth;
    private int statHeight;


    public PlayerBanner(int frameWidth, int frameHeight){
        this.width = (int) (frameWidth * 0.18/*0.18*/);
        this.height = (int) (frameHeight * 0.12/*0.12*/);
        this.iconDimension = (int) (height * 0.8);
        this.iconX = (height - iconDimension)/2;

        nameX = iconX * 2 + iconDimension;
        nameHeight = height/ 4;
        nameWidth = width - nameX;
        statHeight = (height- nameHeight)/5;
        color = Colors.RED;

    }



    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int borderWidth = 3;
        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRect(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth, getHeight() - borderWidth);

        drawIcon(g2d);
        drawName(g2d);
        drawResources(g2d);
    }

    public void drawIcon(Graphics2D g2d){

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        try {
            icon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(color.getIcon())));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Draw the player image within the rounded rectangle
        if (icon != null) {
            System.out.println("image ");
            g2d.drawImage(icon, iconX, iconX, iconDimension, iconDimension, this ); // Adjust padding as needed
        }

    }


    public void drawName(Graphics2D g2d){
        g2d.setColor(Color.BLUE);
        //g2d.drawRect(nameX, getY(), nameWidth, nameHeight);
        Font customFont = new Font("Serif", Font.BOLD, nameHeight - 10); // Font name, style, size
        g2d.setFont(customFont);
        g2d.drawString("francesco", nameX, nameHeight);

    }

    public void drawResources(Graphics2D g2d){
        BufferedImage[] resources = new BufferedImage[7];

        ArrayList<String> paths= new ArrayList<>();
        paths.add("/Images/f.png");
        paths.add("/Images/animal.png");
        paths.add("/Images/plant.png");
        paths.add("/Images/insect.png");
        paths.add("/Images/feather.png");
        paths.add("/Images/inkwell.png");
        paths.add("/Images/manuscript.png");

        for(int i = 0; i < paths.size(); i++){
            try {
                resources[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(paths.get(i))));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        Font customFont = new Font("Serif", Font.BOLD, statHeight - 1); // Font name, style, size
        g2d.setFont(customFont);
        g2d.drawImage(resources[0], nameX, statHeight + iconX , statHeight, statHeight, this );
        g2d.drawString("1", nameX + 2*statHeight, nameHeight + statHeight);
        g2d.drawImage(resources[0], nameX, nameHeight + statHeight, statHeight, statHeight, this );
        g2d.drawImage(resources[0], nameX, nameHeight + 2*statHeight, statHeight, statHeight, this );
        g2d.drawImage(resources[0], nameX, nameHeight + 3*statHeight, statHeight, statHeight, this );


    }
}
