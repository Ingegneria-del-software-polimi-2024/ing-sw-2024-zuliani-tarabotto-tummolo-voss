package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

public interface MessageFromViewToModelController extends MessageFromClient {

    public void execute(ServerControllerInterface controller);
}
