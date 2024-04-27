package SharedWebInterfaces.Messages.MessagesFromClient;

import SharedWebInterfaces.Messages.GeneralMessage;
import SharedWebInterfaces.Messages.ServerControllerInterface;

public interface MessageFromClient extends GeneralMessage {
    public void execute(ServerControllerInterface controller);
}
