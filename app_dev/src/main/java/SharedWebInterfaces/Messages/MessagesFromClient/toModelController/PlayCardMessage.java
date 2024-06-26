package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelController;
import SharedWebInterfaces.Messages.MessagesFromClient.toModelController.MessageFromViewToModelController;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

public class PlayCardMessage implements MessageFromViewToModelController {

    private final int card;
    private final int x;
    private final int y;
    private final boolean faceSide;

    public PlayCardMessage(int card, int x, int y, boolean faceSide) {
        this.card = card;
        this.x = x;
        this.y = y;
        this.faceSide = faceSide;
    }


    @Override
    public void execute(ModelController controller) {
        controller.playCard(card, x, y, faceSide);
    }

    //ignore this
    @Override
    public void execute(ControllerInterface controller) {

    }
}
