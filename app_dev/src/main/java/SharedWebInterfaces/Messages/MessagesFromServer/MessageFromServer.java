package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.Messages.Message;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

public interface MessageFromServer extends Message {
    public void execute(ViewAPI_Interface view);

   // void execute(SharedWebInterfaces.Messages.ViewAPI_Interface view);

}
