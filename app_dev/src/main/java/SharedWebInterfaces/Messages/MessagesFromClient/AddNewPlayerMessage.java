package SharedWebInterfaces.Messages.MessagesFromClient;

import SharedWebInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.ServerControllerInterface;

public class AddNewPlayerMessage implements MessageFromClient{
    String nickname;
    ClientHandlerInterface handler;
    @Override
    public void execute(ServerControllerInterface controller) {
        //TODO solve this shit
    }
    public AddNewPlayerMessage(String nickname, ClientHandlerInterface handler){
        this.nickname = nickname;
        this.handler = handler;
    }
}
