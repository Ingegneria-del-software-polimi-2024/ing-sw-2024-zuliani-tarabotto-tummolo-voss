package Client.UI.GUI.PlayerBanner;



import Client.UI.GUI.Resources;
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

public class test extends JPanel {

    private int points;
    private HashMap<Resources, Integer> resources;
    private JPanel tablePanel;
    private HashMap<Resources, JPanel> resourcePanel;
    public test( String name) {

        resourcePanel = new HashMap<>();
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //this.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        Font customFont = new Font("Serif", Font.BOLD, 150); // Font name, style, size
        JLabel nickname = new JLabel(name);
        //nickname.setFont(nickname.getFont().deriveFont(Font.BOLD));

        add(nickname);
        // Create the table panel with 2 columns and 4 rows
        tablePanel = new JPanel(new GridLayout(4, 2, 25, -5));
        tablePanel.setOpaque(false);


        HashMap<Resources, ImageIcon> resourcesImg = new HashMap<>();


        for(Resources r : Resources.values()){
            BufferedImage img = null;
            try{
                img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(r.getImg())));

                ImageIcon image= new ImageIcon(img.getScaledInstance(15,15,Image.SCALE_SMOOTH));
                //ImageIcon image= new ImageIcon(img);
                resourcesImg.put(r, image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            JPanel cellPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
            cellPanel.setOpaque(false);
            JLabel iconLabel = new JLabel(resourcesImg.get(r));
            iconLabel.setOpaque(false);
            JLabel numberLabel = new JLabel("0");
            numberLabel.setOpaque(false);

            // Add image and number to cell panel
            cellPanel.add(iconLabel);
            cellPanel.add(numberLabel);
            resourcePanel.put(r, cellPanel);

            // Add cell panel to the table panel
            tablePanel.add(cellPanel);

        }

        add(tablePanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    }

    public void updatePoints(int points){

    }

    public void updateResources(HashMap<Resources, Integer> res){
        resources = res;
        for(Resources r : Resources.values()) {
            ((JLabel)(resourcePanel.get(r).getComponent(1))).setText(String.valueOf(res.get(r)));

        }

    }


}
