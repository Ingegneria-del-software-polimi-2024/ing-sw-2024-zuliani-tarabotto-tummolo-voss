package Chat.MessagesFromServer;

import Chat.MessagesFromClient.ChatMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.util.ArrayList;

/**
 * The type Chat history message.
 * A message from the server containing the history of the chat
 */
public class ChatHistoryMessage implements MessageFromServer {
    /**
     * A list containing chat messages in chronological order
     */
    private ArrayList<ChatMessage> history;
    /**
     * the receiver player
     */
    private String receiver;

    /**
     * Instantiates a new Chat history message.
     *
     * @param history  the chat history
     * @param receiver the receiver player
     */
    public ChatHistoryMessage(ArrayList<ChatMessage> history, String receiver) {
        this.history = history;
        this.receiver = receiver;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.resetChatHistory(history);
    }
}
