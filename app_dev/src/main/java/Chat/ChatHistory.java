package Chat;

import Chat.MessagesFromClient.ChatMessage;

import java.sql.Timestamp;
import java.util.HashMap;

public class ChatHistory {
    private HashMap<Timestamp, ChatMessage> history;

    public ChatHistory() {
        this.history = new HashMap<Timestamp, ChatMessage>();
    }

    public Timestamp add(ChatMessage msg){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        history.put(now, msg);
        return now;
    }

    public HashMap<Timestamp, ChatMessage> getHistory(){
        HashMap<Timestamp, ChatMessage> copy = new HashMap<Timestamp, ChatMessage>();
        for(Timestamp key : history.keySet()){
            ChatMessage put = history.get(key);
            copy.put((Timestamp) key.clone(), put.clone());
        }
        return copy;
    }
}
