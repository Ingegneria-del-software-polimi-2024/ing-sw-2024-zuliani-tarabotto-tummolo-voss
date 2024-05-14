package view;

import model.player.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginPage() {
        super("Card Game Login");

        // Create and position components
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 20, 80, 25);
        usernameField.setBounds(90, 20, 150, 25);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        passwordField.setBounds(90, 50, 150, 25);

        loginButton.setBounds(10, 80, 80, 25);

        // Add components to the frame
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);

        // Set layout
        setLayout(null);

        // Set frame size and visibility
        setSize(300, 150);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add action listener to the button
        loginButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get user-entered values
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        // Check if credentials are valid (replace with your authentication logic)
        boolean validCredentials = username.equals("user") && password.equals("password");

        if (validCredentials) {
            // If credentials are valid, open the game's main page
            JOptionPane.showMessageDialog(null, "Welcome, " + username + "!");
            // Insert code here to open the game's main page
        } else {
            // If credentials are invalid, show an error message
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
