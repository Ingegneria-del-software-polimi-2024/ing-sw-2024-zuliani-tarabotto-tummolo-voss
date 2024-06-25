package Chat.MessagesFromClient;

import Server.ModelController;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

import java.sql.Timestamp;

public class ChatMessage implements MessageFromClient {
    private String sender;
    private String content;
    private Timestamp timestamp;

    public ChatMessage(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    @Override
    public void execute(ControllerInterface controller) {

    }

    @Override
    public void execute(ModelController controller) {
        controller.enqChatMsg(this);
    }

    public String getSender(){
        return sender;
    }

    public String getContent(){
        return content;
    }

    public ChatMessage clone(){
        return new ChatMessage(sender, content);
    }

    public void setTimestamp(Timestamp timestamp){
        this.timestamp = timestamp;
    }
}
