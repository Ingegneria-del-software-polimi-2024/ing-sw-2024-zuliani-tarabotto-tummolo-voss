package Chat.MessagesFromServer;

import Chat.MessagesFromClient.ChatMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.util.ArrayList;

/**
 * The type Chat history message.
 */
public class ChatHistoryMessage implements MessageFromServer {
    private ArrayList<ChatMessage> history;
    private String receiver;

    /**
     * Instantiates a new Chat history message.
     *
     * @param history  the history
     * @param receiver the receiver
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
