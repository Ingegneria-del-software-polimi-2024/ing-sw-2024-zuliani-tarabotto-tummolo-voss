package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelController;
import SharedWebInterfaces.Messages.MessagesFromClient.toModelController.MessageFromViewToModelController;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

public class SelectStarterCardMessage implements MessageFromViewToModelController {
    @Override
    public void execute(ModelController controller) {
        //good method
        System.out.println("HI");
    }

    @Override
    public void execute(ControllerInterface controller) {

    }
}
