package SharedWebInterfaces.Messages.MessagesFromServer.Errors;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Return to start message.
 */
public class ReturnToStartMessage implements MessageFromServer {
    @Override
    public void execute(ViewAPI_Interface view) {
        view.returnToStart();
    }
}
