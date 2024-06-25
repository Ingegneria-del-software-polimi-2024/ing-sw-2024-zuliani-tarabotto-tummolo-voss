package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelController;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

public class QuitGameMessage implements MessageFromViewToModelController{
    private final String playerID;

    public QuitGameMessage(String playerID) {
        this.playerID = playerID;
    }

    @Override
    public void execute(ControllerInterface controller) {
    }

    @Override
    public void execute(ModelController controller) {
        controller.quitGame(playerID);
    }

}
