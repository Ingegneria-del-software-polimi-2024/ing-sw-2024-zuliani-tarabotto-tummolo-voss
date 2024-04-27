package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.Messages.GeneralMessage;
import SharedWebInterfaces.Messages.ViewAPI_Interface;

public interface MessageFromServer extends GeneralMessage {
    public void execute(ViewAPI_Interface view);
}
