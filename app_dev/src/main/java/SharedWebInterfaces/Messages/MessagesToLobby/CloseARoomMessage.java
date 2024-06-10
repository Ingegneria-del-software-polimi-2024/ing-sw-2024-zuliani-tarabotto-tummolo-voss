package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.Message;

public class CloseARoomMessage implements MessageToLobby {

    private String roomName;

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
