package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.Messages.Message;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.io.Serializable;

public interface MessageFromServer extends Serializable, Message {
    public abstract void execute(ViewAPI_Interface view);

}
