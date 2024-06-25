package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelController;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

/**
 * The type Ready to play message.
 */
public class ReadyToPlayMessage implements  MessageFromClient {


    @Override
    public void execute(ModelController controller) {
        controller.setPlayerReady();
    }


    @Override
    public void execute(ControllerInterface controller) {
        System.out.println("sbagliato");
    }
}
