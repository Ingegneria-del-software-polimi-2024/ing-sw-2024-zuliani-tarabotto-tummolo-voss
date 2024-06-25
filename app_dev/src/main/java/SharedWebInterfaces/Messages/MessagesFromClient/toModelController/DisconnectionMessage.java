package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelController;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

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
    public void execute(ModelController controller) {
        controller.handleDisconnection();
    }

    @Override
    public void execute(ControllerInterface controller) {
    }
}