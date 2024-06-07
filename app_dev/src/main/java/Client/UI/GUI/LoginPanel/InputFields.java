package Client.UI.GUI.LoginPanel;

import Client.UI.GUI.GUI;

import javax.swing.*;
import java.awt.*;

public class InputFields extends JPanel {

    private JPanel connection;
    private JPanel ip;
    private JPanel username;
    private JPanel game;
    private GUI gui;

    public InputFields(GUI gui){
        this.gui = gui;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    }

    public void chooseConnection(){
        // Create the connection type buttons
        JButton rmiButton = new JButton("RMI");
        JButton socketButton = new JButton("Socket");

        // Add the connection type buttons to a sub-panel
        JPanel connectionPanel = new JPanel();
        connectionPanel.setLayout(new FlowLayout());
        connectionPanel.add(rmiButton);
        connectionPanel.add(socketButton);

        this.add(connectionPanel);

        JLabel hostLabel = new JLabel("Host IP Address:");
        JTextField hostField = new JTextField(1);

        add(Box.createVerticalStrut(10)); // Add some space between components
        add(hostLabel);
        add(hostField);

    }
}
