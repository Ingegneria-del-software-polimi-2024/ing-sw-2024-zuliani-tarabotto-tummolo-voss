package Chat.MessagesFromServer;

import Chat.MessagesFromClient.ChatMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.sql.Timestamp;

public class ChatUpdateMessage implements MessageFromServer{
    private ChatMessage message;

    public ChatUpdateMessage(ChatMessage message) {
        this.message = message;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.deliverTextMessage(message);
    }
}
