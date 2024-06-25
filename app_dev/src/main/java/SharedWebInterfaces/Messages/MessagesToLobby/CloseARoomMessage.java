package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.Message;

/**
 * The type Close a room message.
 */
public class CloseARoomMessage implements MessageToLobby {

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
