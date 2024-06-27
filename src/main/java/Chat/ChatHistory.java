package Chat;

import Chat.MessagesFromClient.ChatMessage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Chat history.
 * Contains in a chronological order all the messages (both private and public) exchanged in a game
 */
public class ChatHistory {
    /**
     * A list containing chat messages in chronological order
     */
    private final ArrayList<ChatMessage> history;

    /**
     * Instantiates a new Chat history.
     */
    public ChatHistory() {
        this.history = new ArrayList<>();
    }

    /**
     * Add a new chat message to the list.
     *
     * @param msg the message to be inserted
     * @return the timestamp at which the message was added
     */
    public Timestamp add(ChatMessage msg){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        msg.setTimestamp(now);
        history.add(msg);
        return now;
    }

    /**
     * Get history array list.
     *
     * @return a copu of the chat history
     */
    public ArrayList<ChatMessage> getHistory(){
        ArrayList<ChatMessage> copy = new ArrayList<>();
        for(ChatMessage msg : history) {
            System.out.println(msg.getReceiver() + " receiver");
            System.out.println(msg.getSender() + " sender");
            copy.add(msg.clone());
        }
        return copy;
    }
}
