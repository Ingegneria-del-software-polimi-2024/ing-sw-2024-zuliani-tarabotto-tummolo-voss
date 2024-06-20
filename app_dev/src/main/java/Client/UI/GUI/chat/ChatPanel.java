package Client.UI.GUI.chat;

import Chat.MessagesFromClient.ChatMessage;
import Client.UI.GUI.GUI;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChatPanel extends JPanel {

    private GUI gui;
    private JTextArea chatArea;
    private JScrollPane chatScrollPane;
    private JTextPane tPane;

    public ChatPanel(GUI gui){
        this.gui = gui;
        setLayout(new BorderLayout());
        setOpaque(false);
        // Create the chat area (non-editable text area inside a scroll pane)
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        //chatArea.setEnabled(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        //chatArea.setBackground(new Color(218, 211, 168));
        //chatArea.setForeground(new Color(53,31,23));


        chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setMaximumSize(chatScrollPane.getPreferredSize());
        chatScrollPane.setOpaque(false);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        add(chatScrollPane, BorderLayout.CENTER);



        // Create the input panel (text field and send button)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());


        JTextField chatInput = new JTextField();
        inputPanel.add(chatInput, BorderLayout.CENTER);


        JButton sendButton = new JButton("Send");
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Action listener for the send button
        sendButton.addActionListener(e -> {
            String message = chatInput.getText();
            if (!message.trim().isEmpty()) {
                gui.getView().sendChatMessage(gui.getView().getPlayerId(), message);
                chatInput.setText("");
            }
        });

        // Add the input panel to the bottom of the chat panel
        add(inputPanel, BorderLayout.SOUTH);

    }

    private static String getCurrentTimestamp() {
        // Get the current time
        LocalDateTime now = LocalDateTime.now();
        // Define the format of the timestamp (hours, minutes, seconds)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // Format the current time
        return now.format(formatter);
    }

    public void updateChat(String sender, String content){
        String from;
        if(sender.equals(gui.getView().getPlayerId())){
            from = "You";
        }else from = sender;
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
        chatArea.append("\n");
        chatArea.append("\n" + from + ": " + content);
        // Ensure the chat area auto-scrolls to the bottom
        JScrollBar vertical = chatScrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    public void restoreChatHistory() {
        List<ChatMessage> chatHistory = gui.getView().getChatHistory();
        String sender = "";
        synchronized (chatHistory) {
            for (ChatMessage msg : chatHistory) {
                if(msg.getSender().equals(gui.getView().getPlayerId())) sender = "You";
                else sender = msg.getSender();
                chatArea.append("\n");
                chatArea.append("\n" + sender + ": " + msg.getContent());
            }
        }


    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int borderWidth = 4;
        g2d.setColor(new Color(53,31,23));
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRect(borderWidth/2, borderWidth/2, getWidth() - borderWidth, getHeight() - borderWidth);

        int innerBorder = borderWidth/2;

        g2d.setStroke(new BasicStroke(innerBorder));
        g2d.drawRect((borderWidth*2), (borderWidth*2), getWidth() - 2*(borderWidth*2), getHeight() - 2*(borderWidth*2));

    }


}



