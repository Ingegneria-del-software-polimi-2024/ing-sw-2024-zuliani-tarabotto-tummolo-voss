package Client.UI.GUI.chat;

import Chat.MessagesFromClient.ChatMessage;
import Client.UI.GUI.GUI;
import com.beust.ah.A;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The type Chat panel.
 */
public class ChatPanel extends JPanel {

    private GUI gui;
    /**
     * contains the messages
     */
    private HashMap<String, JTextArea> chatAreas;
    /**
     * scrollPane containing the chatArea
     */
    private JScrollPane chatScrollPane;

    private String messageReceiver;
    private JPanel multipleChatPanel;


    /**
     * JScrollPane containing a JTextArea where new chat messages are appended.
     *
     * @param gui the gui
     */
    public ChatPanel(GUI gui){
        this.gui = gui;
        setLayout(new BorderLayout());
        setOpaque(false);


        chatAreas = new HashMap<>();
        multipleChatPanel = new JPanel(new CardLayout());

        createChatArea("To everyone");
        multipleChatPanel.add(chatAreas.get("To everyone"), "To everyone");
        for(String p : gui.getView().getPlayers()){
            if(!p.equals(gui.getView().getPlayerId())){
                createChatArea(p);
                multipleChatPanel.add(chatAreas.get(p), p);
            }
        }

        chatScrollPane = new JScrollPane(multipleChatPanel);
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
                if(messageReceiver.equals("To everyone")){
                    gui.getView().sendChatMessage(message);
                }else{
                    gui.getView().sendPrivateChatMessage(message, messageReceiver);
                }
                chatInput.setText("");
            }
        });



        List<String> playerOptions = new ArrayList<>();
        messageReceiver = "To everyone";
        playerOptions.add("To everyone");
        for(String p : gui.getView().getPlayers()){
            if(!p.equals(gui.getView().getPlayerId())) playerOptions.add(p);
        }

        String[] stringArray = playerOptions.toArray(new String[0]);
        JComboBox<String> playerComboBox = new JComboBox<>(stringArray);

        playerComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageReceiver = (String)playerComboBox.getSelectedItem();
                CardLayout cl = (CardLayout) (multipleChatPanel.getLayout());
                cl.show(multipleChatPanel, messageReceiver);
            }
        });

        inputPanel.add(playerComboBox, BorderLayout.NORTH);

        // Add the input panel to the bottom of the chat panel
        add(inputPanel, BorderLayout.SOUTH);

    }


    /**
     * appends a new message to the chat
     *
     * @param sender   the sender
     * @param content  the content
     * @param receiver the receiver
     */
    public void updateChat(String sender, String content, String receiver){
        String from;
        JTextArea chatArea;
        if(sender.equals(gui.getView().getPlayerId())){
            from = "You";
        }else from = sender;

        if(receiver == null){
            chatArea = chatAreas.get("To everyone");
        }else if(from.equals("You")){
            chatArea = chatAreas.get(receiver);
        }else {
            chatArea = chatAreas.get(sender);
        }
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
        JTextArea chatArea;
        synchronized (chatHistory) {
            for (ChatMessage msg : chatHistory) {
                System.out.println(msg.getReceiver());
                System.out.println(msg.getSender());
                if(msg.getReceiver() == null){
                    chatArea = chatAreas.get("To everyone");
                }else if(msg.getSender().equals(gui.getView().getPlayerId())){
                    chatArea = chatAreas.get(msg.getReceiver());
                }else {
                    chatArea = chatAreas.get(msg.getSender());
                }

                if(msg.getSender().equals(gui.getView().getPlayerId())) sender = "You";
                else sender = msg.getSender();
                chatArea.append("\n");
                chatArea.append("\n" + sender + ": " + msg.getContent());
            }
        }


    }

    private void createChatArea(String receiver){
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setBorder(new EmptyBorder(10,10,10,10));
        chatArea.setBackground(new Color(50, 84, 70));
        chatArea.setForeground(new Color(255, 248, 164));

        chatAreas.put(receiver, chatArea);
    }

}



