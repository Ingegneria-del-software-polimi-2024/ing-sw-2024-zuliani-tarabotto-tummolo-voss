package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.SharedInterfaces.Traslator;

/**
 * The type Disconnection message.
 */
public class DisconnectionMessage implements MessageFromViewToModelController {


    /**
     * Instantiates a new Disconnection message.
     */
    public DisconnectionMessage() {
    }

    @Override
    public void execute(ModelTranslator controller) {
        controller.handleDisconnection();
    }

    @Override
    public void execute(Traslator controller) {
    }
}