
package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.ModelController;
import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;

/**
 * The type Heartbeat message.
 */
public class HeartbeatMessage implements MessageToLobby {
    private String playerId;

    /**
     * Instantiates a new Heartbeat message.
     *
     * @param playerId the player id
     */
    public HeartbeatMessage(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public void execute(Lobby lobby) {
        lobby.updateHeartBeat(playerId);
    }

    @Override
    public String getSender() {
        return playerId;
    }
}







