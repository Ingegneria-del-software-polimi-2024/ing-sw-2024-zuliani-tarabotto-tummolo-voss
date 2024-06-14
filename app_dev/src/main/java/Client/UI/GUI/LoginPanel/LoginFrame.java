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

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.gui = gui;
        setSize(new Dimension(width, height));
        getContentPane().setBackground(new Color(218, 211, 168));


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
                    img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Images/codexLogo.jpeg")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                int imgWidth = height;
                int imgHeight = imgWidth;
                g2d.drawImage(img, 0, 0, imgWidth, imgHeight, this);
            }
        };


        imagePanel.setPreferredSize(new Dimension(height, height));
        //imagePanel.setBackground(Color.LIGHT_GRAY);

        this.add(loginFields, BorderLayout.WEST);
        this.add(imagePanel, BorderLayout.CENTER);


        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * a window asking to choose a connection type(RMI/SOCKET) and the ip address
     * of the host is displayed
     */
    public void chooseConnection(){
        loginFields.chooseConnection();

    }

    /**
     * a window asking to select a nickname is displayed
     */
    public void chooseNickname(){
        loginFields.chooseNickname();

    }


    /**
     * a window asking to select one of the available game rooms is displayed,
     * the player is also given the possibility to create a new game
     * @param listOfGames
     */
    public void chooseGame(ArrayList<String> listOfGames){
        loginFields.chooseGame(listOfGames);

    }


    /**
     * method used to handle the case in which the chosen nickname is already being used
     */
    public void nicknameInUse(){
        loginFields.setNicknameInUse();
    }

    /**
     * method used to handle the case in which the player wants to join a room already full
     */
    public void cantJoinRoom(){
        loginFields.setCantJoinRoom();
    }

    /**
     * method used to handle the case in which the player wants to create a new room with a
     * number of players not supported by the game
     */
    public void cantCreateRoom(){
        loginFields.setCantCreateRoom();
    }
}
