package SharedWebInterfaces.Messages.MessagesFromClient;

import SharedWebInterfaces.Messages.Message;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

import java.io.Serializable;

public interface MessageFromClient extends Message, Serializable {
    public abstract void execute(ControllerInterface controller);
}
