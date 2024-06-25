package Chat.MessagesFromClient;

import Server.ModelController;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

import java.sql.Timestamp;

/**
 * The type Chat message.
 */
public class ChatMessage implements MessageFromClient {
    private String sender;
    private String content;
    private String receiver;
    private Timestamp timestamp;

    /**
     * Instantiates a new Chat message.
     *
     * @param sender  the sender
     * @param content the content
     */
    public ChatMessage(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.receiver = null;
    }

    /**
     * Instantiates a new Chat message.
     *
     * @param sender   the sender
     * @param content  the content
     * @param receiver the receiver
     */
    public ChatMessage(String sender, String content, String receiver) {
        this.sender = sender;
        this.content = content;
        this.receiver = receiver;
    }

    @Override
    public void execute(ControllerInterface controller) {

    }

    @Override
    public void execute(ModelController controller) {
        controller.enqChatMsg(this);
    }

    /**
     * Get sender string.
     *
     * @return the string
     */
    public String getSender(){
        return sender;
    }

    /**
     * Get content string.
     *
     * @return the string
     */
    public String getContent(){
        return content;
    }

    public ChatMessage clone(){

        if(receiver == null){
            return new ChatMessage(sender, content);
        }else {
            return new ChatMessage(sender, content, receiver);
        }
    }

    /**
     * Set timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(Timestamp timestamp){
        this.timestamp = timestamp;
    }

    /**
     * Get receiver string.
     *
     * @return the string
     */
    public String getReceiver(){
        return receiver;
    }
}
