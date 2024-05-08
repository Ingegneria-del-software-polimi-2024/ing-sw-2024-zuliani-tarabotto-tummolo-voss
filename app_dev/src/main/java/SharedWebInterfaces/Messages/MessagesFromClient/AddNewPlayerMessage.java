package SharedWebInterfaces.Messages.MessagesFromClient;

import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

public class AddNewPlayerMessage extends MessageFromClient {
    String nickname;
    ClientHandlerInterface handler;
    public void execute(ServerControllerInterface controller) {
        System.out.println("WTFF");
        //TODO solve this
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
