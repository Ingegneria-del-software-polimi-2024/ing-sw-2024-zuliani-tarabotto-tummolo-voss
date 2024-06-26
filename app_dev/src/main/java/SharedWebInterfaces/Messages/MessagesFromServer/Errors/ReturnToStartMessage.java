package SharedWebInterfaces.Messages.MessagesFromServer.Errors;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

public class ReturnToStartMessage implements MessageFromServer {
    @Override
    public void execute(ViewAPI_Interface view) {
        view.returnToStart();
    }
}
