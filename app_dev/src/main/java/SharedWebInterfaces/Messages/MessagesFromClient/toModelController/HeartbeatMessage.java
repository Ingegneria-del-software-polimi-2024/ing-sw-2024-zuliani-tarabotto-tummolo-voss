
package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelController;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

public class HeartbeatMessage implements MessageFromViewToModelController{
    private String playerId;

    public HeartbeatMessage(String playerId){
        this.playerId = playerId;
    }

    @Override
    public void execute(ModelController controller) {
        controller.updateHeartBeat(playerId);
    }

    //ignore this





    @Override
    public void execute(ControllerInterface controller)  {}
}