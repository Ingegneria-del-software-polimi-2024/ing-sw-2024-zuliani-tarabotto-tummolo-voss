package SharedWebInterfaces.Messages.MessagesFromClient;

import SharedWebInterfaces.ControllerInterface;
import SharedWebInterfaces.ServerControllerInterface;

public class SelectStarterCardMesage implements MessageFromClient{
    public void execute(ServerControllerInterface controller) {
        //good method
        System.out.println("HI");
    }

    @Override
    public void execute(ControllerInterface controller) {
        //doing things, wrong method
    }
}
