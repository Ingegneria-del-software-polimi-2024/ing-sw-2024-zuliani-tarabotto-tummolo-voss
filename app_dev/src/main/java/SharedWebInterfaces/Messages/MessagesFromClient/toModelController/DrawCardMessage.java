package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelController;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

/**
 * The type Draw card message.
 */
public class DrawCardMessage implements MessageFromViewToModelController {

    private int cardSource;

    /**
     * Instantiates a new Draw card message.
     *
     * @param cardSource the card source
     */
    public DrawCardMessage(int cardSource) {
        this.cardSource = cardSource;
    }

    @Override
    public void execute(ModelController controller) {
        controller.drawCard(cardSource);
    }

    //ignore this
    @Override
    public void execute(ControllerInterface controller) {

    }
}
