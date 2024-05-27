package Client.UI.GUI;

import Client.UI.GUI.PlayerBanner.CellPanel;
import Client.UI.GUI.PlayerBanner.IconLabel;

import javax.imageio.ImageIO;
import javax.swing.*;
        import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class test extends JPanel {

    public test(String Icon, List<String> paths, int[] numbers) {


        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //this.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        Font customFont = new Font("Serif", Font.BOLD, 150); // Font name, style, size
        JLabel nickname = new JLabel("NickName");
        //nickname.setFont(nickname.getFont().deriveFont(Font.BOLD));

        add(nickname);
        // Create the table panel with 2 columns and 4 rows
        JPanel tablePanel = new JPanel(new GridLayout(4, 2, 25, -5));
        tablePanel.setOpaque(false);

        ImageIcon[] resources = new ImageIcon[7];
        int cont = 0;
        for(String s : paths){
            BufferedImage img = null;
            try{
                img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(s)));

                ImageIcon image= new ImageIcon(img.getScaledInstance(15,15,Image.SCALE_SMOOTH));
                //ImageIcon image= new ImageIcon(img);
                resources[cont] = image;
                cont++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Add images and numbers to the table
        for (int i = 0; i < resources.length; i++) {

            JPanel cellPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
            cellPanel.setOpaque(false);
            JLabel iconLabel = new JLabel(resources[i]);
            iconLabel.setOpaque(false);
            JLabel numberLabel = new JLabel(String.valueOf(numbers[i]));
            numberLabel.setOpaque(false);

            // Add image and number to cell panel
            cellPanel.add(iconLabel);
            cellPanel.add(numberLabel);

            // Add cell panel to the table panel
            tablePanel.add(cellPanel);
        }

        // Add icon panel and table panel to the main panel
        //add(iconPanel, BorderLayout.WEST);
        add(tablePanel);



    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    }

}
