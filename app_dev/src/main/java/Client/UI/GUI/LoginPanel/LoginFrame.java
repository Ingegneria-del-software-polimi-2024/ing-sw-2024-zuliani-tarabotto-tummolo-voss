package Client.UI.GUI.LoginPanel;

import Client.UI.GUI.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class LoginFrame extends JFrame {
    private GUI gui;
    private int width = 900;
    private int height = 600;
    private InputFields loginFields;


    public LoginFrame(GUI gui ){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //loginFrame.setSize(900, 600);
        setLayout(new BorderLayout());

        this.gui = gui;
        setSize(new Dimension(width, height));
        setLayout(new BorderLayout());

        loginFields = new InputFields(gui, this);
        loginFields.setPreferredSize(new Dimension((width - height), 0));


        // Create the right panel for the image
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                BufferedImage img;
                try {
                    img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Screenshot 2024-06-07 at 13.57.54.png")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                int imgWidth = height;
                int imgHeight = imgWidth;
                int x = (getWidth() - imgWidth) / 2;
                int y = (getHeight() - imgHeight) / 2;
                g2d.drawImage(img, 0, 0, imgWidth, imgHeight, this);
            }
        };
        imagePanel.setPreferredSize(new Dimension(height, height));
        imagePanel.setBackground(Color.LIGHT_GRAY);

        this.add(loginFields, BorderLayout.WEST);
        this.add(imagePanel, BorderLayout.CENTER);


        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void chooseConnection(){
        String host;
        String connectionType;
        loginFields.chooseConnection();

    }

    public void chooseNickname(){
        loginFields.chooseNickname();

    }


    public void chooseGame(ArrayList<String> listOfGames){
        loginFields.chooseGame(listOfGames);

    }
}
