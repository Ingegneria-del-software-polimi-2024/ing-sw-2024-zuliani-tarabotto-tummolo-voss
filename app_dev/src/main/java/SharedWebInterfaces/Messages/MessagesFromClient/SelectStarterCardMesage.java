package SharedWebInterfaces.Messages.MessagesFromClient;

import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

public class SelectStarterCardMesage extends MessageFromClient {
    public void execute(ServerControllerInterface controller) {
        //good method
        System.out.println("HI");
    }

    @Override
    public void execute(ControllerInterface controller) {
        //doing things, wrong method
    }
}
