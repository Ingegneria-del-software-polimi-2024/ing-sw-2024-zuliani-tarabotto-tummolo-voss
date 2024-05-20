package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

public class HeartbeatMessage implements MessageFromViewToModelController{
    private String playerId;

    public HeartbeatMessage(String playerId){
        this.playerId = playerId;
    }



    @Override
    public void execute(ServerControllerInterface controller) {
        controller.updateHeartBeat(playerId);
    }



    @Override
    public void execute(ControllerInterface controller)  {}
}
