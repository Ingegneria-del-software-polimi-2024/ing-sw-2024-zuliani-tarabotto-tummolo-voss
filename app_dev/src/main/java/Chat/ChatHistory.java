package Chat;

import Chat.MessagesFromClient.ChatMessage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatHistory {
    private final ArrayList<ChatMessage> history;

    public ChatHistory() {
        this.history = new ArrayList<ChatMessage>();
    }

    public Timestamp add(ChatMessage msg){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        msg.setTimestamp(now);
        history.add(msg);
        return now;
    }

    public ArrayList<ChatMessage> getHistory(){
        ArrayList<ChatMessage> copy = new ArrayList<ChatMessage>();
        for(ChatMessage msg : history) {
            copy.add(msg.clone());
        }
        return copy;
    }
}
