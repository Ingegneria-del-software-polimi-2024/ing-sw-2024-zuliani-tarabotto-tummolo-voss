package SharedWebInterfaces.Messages.MessagesFromClient;

import Server.ModelController;
import SharedWebInterfaces.Messages.Message;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

import java.io.Serializable;

public interface MessageFromClient extends Message, Serializable {
    void execute(ControllerInterface controller);
    void execute(ModelController controller);
}
