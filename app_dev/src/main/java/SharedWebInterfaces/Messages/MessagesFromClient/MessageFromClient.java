package SharedWebInterfaces.Messages.MessagesFromClient;

import SharedWebInterfaces.Messages.Message;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

public interface MessageFromClient extends Message {
    public void execute(ControllerInterface controller);
}
