package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;

/**
 * The interface Message from view to model controller.
 * This interface is used to send messages from the view to the model controller.
 */
public interface MessageFromViewToModelController extends MessageFromClient {

    void execute(ModelTranslator controller);
}
