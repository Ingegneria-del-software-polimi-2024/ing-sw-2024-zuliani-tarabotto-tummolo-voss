package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import SharedWebInterfaces.Messages.MessagesFromClient.toModelController.MessageFromViewToModelController;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

public class PlayCardMessage implements MessageFromViewToModelController {

    private int card;
    private int x;
    private int y;
    private boolean faceSide;

    public PlayCardMessage(int card, int x, int y, boolean faceSide) {
        this.card = card;
        this.x = x;
        this.y = y;
        this.faceSide = faceSide;
    }


    @Override
    public void execute(ServerControllerInterface controller) {
        controller.playCard(card, x, y, faceSide);
    }

    //ignore this
    @Override
    public void execute(ControllerInterface controller) {

    }
}
