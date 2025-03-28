package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.Traslator;

/**
 * The type Want to reconnect message.
 * This message is sent by the client when he wants to reconnect to a room after exiting or being disconnected.
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
     * Instantiates a new I Want to reconnect message.
     *
     * @param playerID the player id
     * @param roomName the room name
     */
    public I_WantToReconnectMessage(String playerID, String roomName) {
        this.playerID = playerID;
        this.roomName = roomName;
    }

    @Override
    public void execute(Traslator controller) {

    }

    @Override
    public void execute(ModelTranslator controller) {
        controller.reconnect(playerID);
    }
}
