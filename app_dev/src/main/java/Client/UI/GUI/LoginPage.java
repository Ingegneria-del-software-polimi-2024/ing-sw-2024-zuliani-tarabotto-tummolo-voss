/*
 * Created by JFormDesigner on Tue May 28 11:13:46 CEST 2024
 */

package Client.UI.GUI;

import Client.View.ViewAPI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * @author nicol
 */
public class LoginPage extends JFrame {
    private GUI gui;

    public LoginPage(GUI gui) {
        this.gui = gui;
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Nicola Tummolo
        loginPadel = new JPanel();
        RMIButton = new JToggleButton();
        socketButton = new JToggleButton();
        welcomeLabel = new JLabel();
        enterButton = new JButton();
        nicknameTextField = new JTextField();
        ConnectionLabel = new JLabel();
        IPTextField = new JTextField();
        nicknameLabel = new JLabel();
        IPLabel = new JLabel();
        connectionButtonGroup = new ButtonGroup(); // Aggiungi questa riga

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== loginPadel ========
        {
            loginPadel.setBackground(new Color(0xd2cd89));
            loginPadel.setLayout(null);

            //---- RMIButton ----
            RMIButton.setText("RMI");
            RMIButton.setBackground(new Color(0xada038));
            loginPadel.add(RMIButton);
            RMIButton.setBounds(210, 160, 90, 30);

            //---- socketButton ----
            socketButton.setText("Socket");
            socketButton.setBackground(new Color(0xada038));
            loginPadel.add(socketButton);
            socketButton.setBounds(375, 160, 86, 30);

            // Aggiungi i pulsanti al ButtonGroup
            connectionButtonGroup.add(RMIButton);
            connectionButtonGroup.add(socketButton);

            //---- welcomeLabel ----
            welcomeLabel.setText("WELCOME in CODEX NATURALIS");
            welcomeLabel.setFont(new Font("Ravie", Font.BOLD, 18));
            loginPadel.add(welcomeLabel);
            welcomeLabel.setBounds(135, 35, 400, 50);

            //---- enterButton ----
            enterButton.setText("ENTER");
            enterButton.setFont(new Font("Inter", Font.BOLD, 16));
            enterButton.setBackground(new Color(0xada038));
            enterButton.setEnabled(false); // Inizia disabilitato
            loginPadel.add(enterButton);
            enterButton.setBounds(280, 410, 125, 45);
            loginPadel.add(nicknameTextField);
            nicknameTextField.setBounds(240, 325, 200, nicknameTextField.getPreferredSize().height);

            //---- ConnectionLabel ----
            ConnectionLabel.setText("Choose the connection");
            ConnectionLabel.setFont(new Font("Sigmar One", Font.BOLD, 13));
            loginPadel.add(ConnectionLabel);
            ConnectionLabel.setBounds(new Rectangle(new Point(245, 125), ConnectionLabel.getPreferredSize()));
            loginPadel.add(IPTextField);
            IPTextField.setBounds(240, 245, 200, IPTextField.getPreferredSize().height);

            //---- nicknameLabel ----
            nicknameLabel.setText("Enter your nickname");
            nicknameLabel.setFont(new Font("Sigmar One", Font.PLAIN, 13));
            loginPadel.add(nicknameLabel);
            nicknameLabel.setBounds(250, 300, 185, nicknameLabel.getPreferredSize().height);

            //---- IPLabel ----
            IPLabel.setText("Enter the server IP");
            IPLabel.setFont(new Font("Sigmar One", Font.PLAIN, 13));
            loginPadel.add(IPLabel);
            IPLabel.setBounds(new Rectangle(new Point(245, 220), IPLabel.getPreferredSize()));

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < loginPadel.getComponentCount(); i++) {
                    Rectangle bounds = loginPadel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = loginPadel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                loginPadel.setMinimumSize(preferredSize);
                loginPadel.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(loginPadel);
        loginPadel.setBounds(0, 0, 650, 505);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());

        // Aggiungi i listener per abilitare il tasto enter
        DocumentListener textFieldsListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateEnterButtonState(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateEnterButtonState(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateEnterButtonState(); }
        };
        nicknameTextField.getDocument().addDocumentListener(textFieldsListener);
        IPTextField.getDocument().addDocumentListener(textFieldsListener);

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEnterButtonState();
            }
        };

        RMIButton.addActionListener(buttonListener);
        socketButton.addActionListener(buttonListener);

        // Aggiungi il listener per il tasto enter
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                gui.chooseConnection();
                gui.askNickname();
                gui.nickNameAlreadyInUse();
                Lobby lobby = new Lobby();
                lobby.setVisible(true);
                dispose(); // Chiudi la finestra di login
            }
        });
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void updateEnterButtonState() {
        boolean isTextFieldsFilled = !nicknameTextField.getText().trim().isEmpty() && !IPTextField.getText().trim().isEmpty();
        boolean isButtonSelected = RMIButton.isSelected() || socketButton.isSelected();
        enterButton.setEnabled(isTextFieldsFilled && isButtonSelected);
    }

    public String getSelectedConnectionType() {
        return RMIButton.isSelected() ? "RMI" : "Socket";
    }

    public String getHost() {
        return IPTextField.getText().trim();
    }

    public String getNickname() {
        return nicknameTextField.getText().trim();
    }

    public void resetNicknameField() {
        nicknameTextField.setText("");
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Nicola Tummolo
    private JPanel loginPadel;
    private JToggleButton RMIButton; // Modificato a JToggleButton
    private JToggleButton socketButton; // Modificato a JToggleButton
    private JLabel welcomeLabel;
    private JButton enterButton;
    private JTextField nicknameTextField;
    private JLabel ConnectionLabel;
    private JTextField IPTextField;
    private JLabel nicknameLabel;
    private JLabel IPLabel;
    private ButtonGroup connectionButtonGroup; // Aggiungi questa riga
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public static void main(String[] args) {
        // Inizializzare ViewAPI come appropriato
        ViewAPI viewAPI = new ViewAPI(); // Questo deve essere sostituito con il codice appropriato per inizializzare ViewAPI

        // Creare un'istanza di GUI con ViewAPI
        GUI gui = new GUI(viewAPI);

        // Avviare l'applicazione Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.loginPage.setVisible(true);
            }
        });
    }

}
