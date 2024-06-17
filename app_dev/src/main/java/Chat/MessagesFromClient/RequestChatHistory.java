package Chat.MessagesFromClient;

import Server.ModelController;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

public class RequestChatHistory implements MessageFromClient {
    private String sender;
    @Override
    public void execute(ControllerInterface controller) {

    }

    @Override
    public void execute(ModelController controller) {
        controller.sendChatHistory(sender);
    }

    public RequestChatHistory(String sender) {
        this.sender = sender;
    }
}
