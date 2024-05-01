package SharedWebInterfaces.Messages.MessagesFromClient;

import SharedWebInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.ControllerInterface;
import SharedWebInterfaces.ServerControllerInterface;

public class AddNewPlayerMessage implements MessageFromClient{
    String nickname;
    ClientHandlerInterface handler;
    public void execute(ServerControllerInterface controller) {
        System.out.println("WTFF");
        //TODO solve this shit
    }
    public AddNewPlayerMessage(String nickname, ClientHandlerInterface handler){
        this.nickname = nickname;
        this.handler = handler;
    }

    @Override
    public void execute(ControllerInterface controller) {
        System.out.println("IDK");
        return;
    }
}
