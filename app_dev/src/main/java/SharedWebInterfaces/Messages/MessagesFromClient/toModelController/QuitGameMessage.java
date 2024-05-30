package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelController;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

public class QuitGameMessage implements MessageFromViewToModelController{
    @Override
    public void execute(ModelController controller) {
        controller.endGame();
    }

    //ignore this
    @Override
    public void execute(ControllerInterface controller) {

    }

}
