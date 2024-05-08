package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import SharedWebInterfaces.Messages.MessagesFromClient.toModelController.MessageFromViewToModelController;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

public class PlayStarterCardMessage implements MessageFromViewToModelController {
    private boolean starterCardFace;
    private String playerId;

    public PlayStarterCardMessage(boolean starterCardFace, String playerId){
        this.starterCardFace = starterCardFace;
        this.playerId = playerId;
    }

    @Override
    public void execute(ServerControllerInterface controller) {
        controller.playStarterCard(starterCardFace, playerId);
    }

    //ignore this
    @Override
    public void execute(ControllerInterface controller) {

    }
}
