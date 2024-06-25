package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;

public class QuitGameBeforeStartMessage implements MessageToLobby{

    private String roomName;
    private String player;
    public QuitGameBeforeStartMessage(String roomName, String player){
        this.roomName = roomName;
        this.player = player;
    }
    @Override
    public void execute(Lobby lobby) {
        lobby.quitGameBeforeStart(roomName, player);
    }

    @Override
    public String getSender() {
        return null;
    }
}
