package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelController;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

public class ReadyToPlayMessage implements  MessageFromClient {


    @Override
    public void execute(ModelController controller) {
        System.out.println("giusto");
        controller.setPlayerReady();
    }


    @Override
    public void execute(ControllerInterface controller) {
        System.out.println("sbagliato");
    }
}
