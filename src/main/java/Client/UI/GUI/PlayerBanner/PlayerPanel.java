package Client.UI.GUI.PlayerBanner;




import Client.UI.GUI.Colors;
import Client.UI.GUI.GUI;
import Client.UI.GUI.Resources;
import model.enums.Artifact;
import model.enums.Element;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;


/**
 * class that contains game data about a player: icon image, points, resources, elements
 */
public class PlayerPanel extends JPanel {
    private BufferedImage playerImage;
    private int points;
    private int imageDim;
    private int imagePos;
    private String player;
    private PlayerInfo infoPanel;
    private GUI gui;


    /**
     * Instantiates a new Player panel.
     *
     * @param player the player
     * @param width  the width
     * @param height the height
     * @param gui    the gui
     */
    public PlayerPanel(String player, int width, int height, GUI gui ) {
        this.gui = gui;
        this.player = player;

        imageDim = (int)(height * 0.8);
        System.out.println("imageDim" + imageDim);
        imagePos = (height - imageDim) / 2;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createEmptyBorder(0, 2*imagePos + imageDim, 0, 0));
        setBackground(new Color(50, 84, 70));
        setOpaque(true);

        infoPanel = new PlayerInfo(player, gui, (imageDim - 1)/6);
        add(infoPanel);


        //loading the player image icon
        try {
            playerImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(Colors.valueOf(gui.getView().getPawnColor(player)).getIcon())));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define the bounds and properties for the rounded rectangle
        int arcWidth = 10;
        int arcHeight = 10;


        // Draw the player image within the rounded rectangle
        if (playerImage != null) {
            g2d.drawImage(playerImage, imagePos, imagePos, imageDim, imageDim , this); // Adjust padding as needed
        }


        //we draw the rounded rectangle around the image
        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(imagePos, imagePos, imageDim, imageDim, arcWidth, arcHeight);


        //we draw the rectangle around the banner
        int borderWidth = 3;
        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRect(borderWidth/2, borderWidth/2, getWidth() - borderWidth, getHeight() - borderWidth);

        // Draw points in a circle at the top left corner
        g2d.setColor(new Color(102, 22, 188));
        g2d.fillOval(imagePos - 5 , imagePos - 5, 30, 30);
        g2d.setColor(new Color(255, 248, 164));
        g2d.drawString(String.valueOf(points), imagePos  + 5, imagePos + 10 );

        if(gui.getCurrentDisposition().equals(player)){
            int border = 3;

            g2d.setColor(Color.green);
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.drawRect(borderWidth/2, borderWidth/2, getWidth() - borderWidth, getHeight() - borderWidth);

        }

   }

    /**
     * updates the player's points
     */
    public void updatePoints(){
        points = gui.getView().getPoints().get(getPlayer());
   }


    /**
     * updates the player's resources (Resources + Elements)
     */
    public void updateResources(){
       HashMap<Resources, Integer> res = new HashMap<>();
       HashMap<Element, Integer> availableElements = gui.getView().getAvailableElements(player);
       HashMap<Artifact, Integer> availableArtifacts = gui.getView().getAvailableArtifacts(player);
        for(Resources r : Resources.values()){
            res.put(r, 0);
        }
       for(Element e : availableElements.keySet()){
            for(Resources r : res.keySet()){
                if(String.valueOf(r).equals(String.valueOf(e))){
                    res.put(r, availableElements.get(e));
                }
            }
        }

       for(Artifact a  : availableArtifacts.keySet()){
           for(Resources r : res.keySet()){
               if(String.valueOf(r).equals(String.valueOf(a))){
                   res.put(r, availableArtifacts.get(a));
               }
           }
       }

       infoPanel.updateResources(res);
   }

    /**
     * Get player string.
     *
     * @return the string
     */
    public String getPlayer(){return  player;}
}

