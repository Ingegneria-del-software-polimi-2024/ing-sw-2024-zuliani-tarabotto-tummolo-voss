package Client.UI.GUI.chat;

import Chat.MessagesFromClient.ChatMessage;
import Client.UI.GUI.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChatPanel extends JPanel {

    private GUI gui;
    /**
     * contains the messages
     */
    private JTextArea chatArea;
    /**
     * scrollPane containing the chatArea
     */
    private JScrollPane chatScrollPane;

    /**
     * JScrollPane containing a JTextArea where new chat messages are appended.
     * @param gui
     */
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
        chatArea.setBorder(new EmptyBorder(10,10,10,10));
        chatArea.setBackground(new Color(50, 84, 70));
        chatArea.setForeground(new Color(255, 248, 164));


        chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setBackground(new Color(50, 84, 70));
        chatScrollPane.setMaximumSize(chatScrollPane.getPreferredSize());
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        add(chatScrollPane, BorderLayout.CENTER);



        // Create the input panel (text field and send button)
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(new EmptyBorder(5,5,5,5));
        inputPanel.setBackground(new Color(171, 144, 76));
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


    /**
     * appends a new message to the chat
     * @param sender
     * @param content
     */
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

    /**
     * restores the past chat history when a player reconnects to the room after he disconnected
     */
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




}



