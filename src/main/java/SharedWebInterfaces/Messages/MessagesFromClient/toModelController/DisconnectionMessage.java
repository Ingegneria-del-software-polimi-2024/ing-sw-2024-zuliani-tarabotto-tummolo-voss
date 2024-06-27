package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.SharedInterfaces.Traslator;

/**
 * The type Disconnection message.
 * This message is used to communicate the disconnection of a player, it is self-sent by the client's handler (server side)
 * to the server itself
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