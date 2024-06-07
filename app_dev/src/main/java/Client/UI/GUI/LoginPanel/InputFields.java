package Client.UI.GUI.LoginPanel;

import Client.UI.GUI.GUI;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputFields extends JPanel {

    private JPanel connection;
    private JPanel ip;
    private JPanel username;
    private JPanel game;
    private GUI gui;
    private String host;
    private String connectionType;
    private String nickname;

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

        rmiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectionType = "rmi";
            }
        });
        socketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectionType = "socket";
            }
        });


        // Add the connection type buttons to a sub-panel
        JPanel connectionPanel = new JPanel();
        connectionPanel.setLayout(new FlowLayout());
        connectionPanel.add(rmiButton);
        connectionPanel.add(socketButton);

        this.add(connectionPanel);

        JLabel hostLabel = new JLabel("Host IP Address:");
        JTextField hostField = new JTextField(1);
        hostField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            private void updateTextFieldContent() {
                host = hostField.getText();
                System.out.println("Updated text: " + host);
            }

        });




        JButton nextButton = new JButton("next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    gui.getView().startConnection(connectionType, host);
                } catch (StartConnectionFailedException er) {
                    System.out.println("~> An error during the connection occurred\n   Check your internet connection and retry\n");
                    gui.chooseConnection();
                }
            }
        });


        add(Box.createVerticalStrut(10)); // Add some space between components
        add(hostLabel);
        add(hostField);
        add(Box.createVerticalStrut(10)); // Add some space between components
        add(nextButton);


    }

    public void chooseNickname(){
        this.removeAll();
        JTextField hostField = new JTextField(1);
        hostField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTextFieldContent();
            }

            private void updateTextFieldContent() {
                nickname = hostField.getText();
                System.out.println("Updated text: " + host);
            }
        });
        add(hostField);
        JButton nextButton = new JButton("next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.getView().setPlayerId(nickname);
            }
        });
        add(nextButton);
    }
}
