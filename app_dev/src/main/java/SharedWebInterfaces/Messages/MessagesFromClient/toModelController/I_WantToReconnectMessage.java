package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelController;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

public class I_WantToReconnectMessage implements MessageFromClient {
    String playerID;
    String roomName;

    public I_WantToReconnectMessage(String playerID, String roomName) {
        this.playerID = playerID;
        this.roomName = roomName;
    }

    @Override
    public void execute(ControllerInterface controller) {

    }

    @Override
    public void execute(ModelController controller) {
        controller.reconnect(playerID);
    }
}
