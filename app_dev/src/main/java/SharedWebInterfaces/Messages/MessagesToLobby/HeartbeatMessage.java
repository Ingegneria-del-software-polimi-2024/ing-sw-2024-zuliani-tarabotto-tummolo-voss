
package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;

/**
 * The type Heartbeat message.
 * This message is sent by the client to the server to indicate that the client is still connected.
 */
public class HeartbeatMessage implements MessageToLobby {
    /**
     * The Player's id.
     */
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







