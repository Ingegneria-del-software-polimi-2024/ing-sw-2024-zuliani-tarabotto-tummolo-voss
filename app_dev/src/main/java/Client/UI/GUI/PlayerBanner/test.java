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
    private HashMap<Resources, BufferedImage> icons;
    public test( String name) {

        resourcePanel = new HashMap<>();
        icons = new HashMap<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);
        JPanel nickPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nickPanel.setOpaque(false);

        Font customFont = new Font("Serif", Font.BOLD, 15); // Font name, style, size
        JLabel nickname = new JLabel(name);
        nickname.setFont(customFont);
        nickname.setForeground(new Color(255, 248, 164));
        //nickname.setAlignmentX(Component.LEFT_ALIGNMENT);
        nickPanel.add(nickname);
        //nickname.setFont(nickname.getFont().deriveFont(Font.BOLD));

        add(nickPanel);
        // Create the table panel with 2 columns and 4 rows
        tablePanel = new JPanel(new GridLayout(4, 2, 25, -5));
        tablePanel.setOpaque(false);




        for(Resources r : Resources.values()){
            BufferedImage img = null;
            try{
                img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(r.getImg())));

                //ImageIcon image= new ImageIcon(img);
                icons.put(r, img);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            JPanel cellPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
            cellPanel.setOpaque(false);
            Dimension size = new Dimension(16, 16);
            JLabel iconLabel = new JLabel();
            iconLabel.setPreferredSize(size);
            iconLabel.setPreferredSize(size);
            iconLabel.setMinimumSize(size);
            iconLabel.setMaximumSize(size);
            iconLabel.setBackground(Color.CYAN);
            //iconLabel.setOpaque(false);
            JLabel numberLabel = new JLabel("0");
            numberLabel.setForeground(new Color(255, 248, 164));
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


        for(Resources r : Resources.values()) {
            JLabel label;
            label = ((JLabel)(resourcePanel.get(r).getComponent(0)));
            Point labelPos = new Point(resourcePanel.get(r).getLocation());
            g2d.drawImage(icons.get(r),(int)( labelPos.getX() + tablePanel.getX()+ label.getX()) /*+ label.getX()*/, (int)( labelPos.getY() + tablePanel.getY() + label.getY()) /*+ label.getY() + label.getHeight()*/, resourcePanel.get(r).getComponent(0).getWidth(), resourcePanel.get(r).getComponent(0).getHeight() , this);

        }

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
