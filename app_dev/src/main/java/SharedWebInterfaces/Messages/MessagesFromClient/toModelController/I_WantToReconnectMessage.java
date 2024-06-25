package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelController;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;

/**
 * The type Want to reconnect message.
 */
public class I_WantToReconnectMessage implements MessageFromClient {
    /**
     * The Player id.
     */
    String playerID;
    /**
     * The Room name.
     */
    String roomName;

    /**
     * Instantiates a new Want to reconnect message.
     *
     * @param playerID the player id
     * @param roomName the room name
     */
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
