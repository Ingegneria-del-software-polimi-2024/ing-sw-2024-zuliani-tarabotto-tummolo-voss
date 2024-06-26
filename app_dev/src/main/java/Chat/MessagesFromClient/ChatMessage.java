package Chat.MessagesFromClient;

import Server.ModelTranslator;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.Traslator;

import java.sql.Timestamp;

/**
 * The type Chat message.
 * This class is used to represent a message belonging to the chat
 */
public class ChatMessage implements MessageFromClient {
    /**
     * the sender player
     */
    private String sender;
    /**
     * the content of the message
     */
    private String content;
    /**
     * the receiver player
     */
    private String receiver;
    /**
     * the timestamp at which the server receives the message
     */
    private Timestamp timestamp;

    /**
     * Instantiates a new Chat message.
     * Used for the messages to be broadcasted, initializes receiver to null
     *
     * @param sender  the sender of the message
     * @param content the content of the message
     */
    public ChatMessage(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.receiver = null;
    }

    /**
     * Instantiates a new Chat message.
     *
     * @param sender   the sender of the message
     * @param content  the content of the message
     * @param receiver the receiver of the message
     */
    public ChatMessage(String sender, String content, String receiver) {
        this.sender = sender;
        this.content = content;
        this.receiver = receiver;
    }

    @Override
    public void execute(Traslator controller) {

    }

    @Override
    public void execute(ModelTranslator controller) {
        controller.enqChatMsg(this);
    }

    /**
     * Get sender string.
     *
     * @return the sender of the message
     */
    public String getSender(){
        return sender;
    }

    /**
     * Get content string.
     *
     * @return the content of the message
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
     * @param timestamp the timestamp at which the server receives the message
     */
    public void setTimestamp(Timestamp timestamp){
        this.timestamp = timestamp;
    }

    /**
     * Get receiver string.
     *
     * @return the receiver of the message, null when the message is broadcasted to all the players
     */
    public String getReceiver(){
        return receiver;
    }
}
