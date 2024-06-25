package Chat.MessagesFromServer;

import Chat.MessagesFromClient.ChatMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.sql.Timestamp;

/**
 * The type Chat update message.
 */
public class ChatUpdateMessage implements MessageFromServer{
    private ChatMessage message;

    /**
     * Instantiates a new Chat update message.
     *
     * @param message the message
     */
    public ChatUpdateMessage(ChatMessage message) {
        this.message = message;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.deliverTextMessage(message);
    }
}
