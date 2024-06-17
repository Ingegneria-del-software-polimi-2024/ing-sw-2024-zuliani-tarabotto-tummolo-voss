package Chat.MessagesFromServer;

import Chat.MessagesFromClient.ChatMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatHistoryMessage implements MessageFromServer {
    private ArrayList<ChatMessage> history;
    private String receiver;

    public ChatHistoryMessage(ArrayList<ChatMessage> history, String receiver) {
        this.history = history;
        this.receiver = receiver;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        //TODO da implementare
    }
}
