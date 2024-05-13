package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelController;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

public interface MessageFromViewToModelController extends MessageFromClient {

    void execute(ModelController controller);
}
