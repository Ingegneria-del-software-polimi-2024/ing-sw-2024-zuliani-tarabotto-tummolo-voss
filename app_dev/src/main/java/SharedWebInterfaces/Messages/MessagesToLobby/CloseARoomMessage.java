package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.Message;

/**
 * The type Close a room message.
 * This message is used to close a room in the lobby. It is self sent by the room to the lobby when all the players
 * leave the room or when a game ends.
 */
public class CloseARoomMessage implements MessageToLobby {
    /**
     * The Room name.
     */
    private String roomName;

    /**
     * Instantiates a new Close a room message.
     *
     * @param roomName the room name
     */
    public CloseARoomMessage(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public void execute(Lobby lobby) {
        lobby.closeRoom(roomName);
    }

    @Override
    public String getSender() {
        return null;
    }
}
