package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

public class DrawCardMessage implements MessageFromViewToModelController {

    private int cardSource;

    public DrawCardMessage(int cardSource) {
        this.cardSource = cardSource;
    }

    @Override
    public void execute(ServerControllerInterface controller) {
        controller.drawCard(cardSource);
    }

    //ignore this
    @Override
    public void execute(ControllerInterface controller) {

    }
}
