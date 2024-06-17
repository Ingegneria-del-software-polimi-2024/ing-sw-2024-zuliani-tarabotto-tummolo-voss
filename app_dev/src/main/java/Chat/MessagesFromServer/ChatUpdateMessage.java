package Chat.MessagesFromServer;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.sql.Timestamp;

public class ChatUpdateMessage implements MessageFromServer{
    private String sender;
    private String content;
    private Timestamp timestamp;

    public ChatUpdateMessage(String sender, String content, Timestamp timestamp) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        //TODO daimplementare
    }
}
