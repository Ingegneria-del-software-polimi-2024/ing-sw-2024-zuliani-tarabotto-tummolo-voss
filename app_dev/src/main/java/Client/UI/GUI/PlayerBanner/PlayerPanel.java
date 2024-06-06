package Client.UI.GUI.PlayerBanner;




import Client.UI.GUI.GUI;
import Client.UI.GUI.PlayerBanner.test;
import Client.UI.GUI.Resources;
import Client.View.ViewAPI;
import model.enums.Artifact;
import model.enums.Element;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;



public class PlayerPanel extends JPanel {
    private BufferedImage playerImage;
    private int points;
    private int resource;
    private int imageDim = (int)(getHeight() * 0.8);
    private int imagePos = (getHeight() - imageDim) / 2;
    private String player;
    private test infoPanel;
    //private ViewAPI view;
    private GUI gui;


    public PlayerPanel(String player, int width, int height, GUI gui ) {
        this.gui = gui;
        this.player = player;
        imageDim = (int)(height * 0.8);
        imagePos = (height - imageDim) / 2;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        //setBorder(BorderFactory.createEmptyBorder((int)(height * 0.05), (int)(height * 0.05), (int)(height * 0.05), (int)(height * 0.05)));
        setBorder(BorderFactory.createEmptyBorder(0, 2*imagePos + imageDim, 0, 0));

        infoPanel = new test(player, gui);
        add(infoPanel);
        setBackground(new Color(50, 84, 70));

        setOpaque(true);



        try {
            playerImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/playerIcon/icon2.jpeg")));
            //playerImage = createRoundedImage(img, 100);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setPlayerImage(BufferedImage image) {
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
        int arcWidth = 10;
        int arcHeight = 10;


        // Draw the player image within the rounded rectangle
        if (playerImage != null) {
            //System.out.println("image ");
            //g2d.setClip(new RoundRectangle2D.Double(imagePos, imagePos, imageDim + 5, imageDim + 5, 20, 20));
            g2d.drawImage(playerImage, imagePos, imagePos, imageDim, imageDim , this); // Adjust padding as needed
        }


        //we draw the rounded rectangle around the image
        g2d.setColor(new Color(171, 144, 76));
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(imagePos, imagePos, imageDim, imageDim, arcWidth, arcHeight);


        //we draw the rectangle around the banner
        int borderWidth = 2;
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
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.drawRect(borderWidth/2, borderWidth/2, getWidth() - borderWidth, getHeight() - borderWidth);

        }

   }

   public void updatePoints(int points){
        infoPanel.updatePoints(points);
   }

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

   public String getPlayer(){return  player;}
}

