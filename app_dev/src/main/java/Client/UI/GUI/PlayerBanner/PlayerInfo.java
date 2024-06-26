package Client.UI.GUI.PlayerBanner;



import Client.UI.GUI.GUI;
import Client.UI.GUI.Resources;

import javax.swing.*;
        import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * specific class that contains the JPanel structure for the nickname and resources
 */
public class PlayerInfo extends JPanel {

    private HashMap<Resources, Integer> resources;
    private JPanel tablePanel;
    private HashMap<Resources, JPanel> resourcePanel;
    private GUI gui;

    public PlayerInfo(String name, GUI gui, int cellHeight) {

        //System.out.println("cellheight:" + cellHeight);
        this.gui = gui;
        resourcePanel = new HashMap<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);
        JPanel nickPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nickPanel.setOpaque(false);

        Font customFont = new Font("Serif", Font.BOLD, cellHeight); // Font name, style, size
        JLabel nickname = new JLabel(name);
        nickname.setOpaque(false);
        nickname.setSize(new Dimension(0, cellHeight));
        nickname.setFont(customFont);
        nickname.setForeground(new Color(255, 248, 164));
        //nickname.setAlignmentX(Component.LEFT_ALIGNMENT);
        nickPanel.add(nickname);

        add(nickPanel);
        // Create the table panel with 2 columns and 4 rows
        tablePanel = new JPanel(new GridLayout(4, 2, cellHeight, - cellHeight/2)); //25, -5
        tablePanel.setOpaque(false);




        for(Resources r : Resources.values()){

            JPanel cellPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
            cellPanel.setOpaque(false);
            Dimension size = new Dimension(cellHeight, cellHeight); //16,16
            JLabel iconLabel = new JLabel();

            iconLabel.setPreferredSize(size);
            iconLabel.setMaximumSize(size);

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

        HashMap<Resources, BufferedImage> icons = gui.getResIcons();


        for(Resources r : Resources.values()) {
            JLabel label;
            label = ((JLabel)(resourcePanel.get(r).getComponent(0)));
            Point labelPos = new Point(resourcePanel.get(r).getLocation());
            g2d.drawImage(icons.get(r),(int)( labelPos.getX() + tablePanel.getX()+ label.getX()) /*+ label.getX()*/, (int)( labelPos.getY() + tablePanel.getY() + label.getY()) /*+ label.getY() + label.getHeight()*/, resourcePanel.get(r).getComponent(0).getWidth(), resourcePanel.get(r).getComponent(0).getHeight() , this);

        }

    }



    public void updateResources(HashMap<Resources, Integer> res){
        resources = res;
        for(Resources r : Resources.values()) {
            ((JLabel)(resourcePanel.get(r).getComponent(1))).setText(String.valueOf(res.get(r)));
        }

    }


}
