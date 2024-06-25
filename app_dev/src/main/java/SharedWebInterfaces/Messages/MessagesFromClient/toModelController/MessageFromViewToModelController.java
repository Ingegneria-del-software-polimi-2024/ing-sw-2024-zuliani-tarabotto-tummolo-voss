package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;

/**
 * The interface Message from view to model controller.
 */
public interface MessageFromViewToModelController extends MessageFromClient {

    void execute(ModelTranslator controller);
}
