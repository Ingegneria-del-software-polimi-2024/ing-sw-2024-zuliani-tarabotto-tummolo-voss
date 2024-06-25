package Chat.MessagesFromServer;

import Chat.MessagesFromClient.ChatMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Chat update message.
 * A message from the  server notifying the player of the arrival of a new chat message
 */
public class ChatUpdateMessage implements MessageFromServer{
    /**
     * The new chat  message
     */
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
