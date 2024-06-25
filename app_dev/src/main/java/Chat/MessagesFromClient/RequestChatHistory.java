package Chat.MessagesFromClient;

import Server.ModelController;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

/**
 * The type Request chat history.
 */
public class RequestChatHistory implements MessageFromClient {
    private String sender;
    @Override
    public void execute(ControllerInterface controller) {

    }

    @Override
    public void execute(ModelController controller) {
        controller.sendChatHistory(sender);
    }

    /**
     * Instantiates a new Request chat history.
     *
     * @param sender the sender
     */
    public RequestChatHistory(String sender) {
        this.sender = sender;
    }
}
