package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_RoomChoice;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;

public class JoinGameMessage implements MessageToLobby{
    private String user;
    private String game;
    private int nPlayers;

    @Override
    public void execute(Lobby lobby) {
        lobby.updateHeartBeat(user);
        lobby.enterRoom(user, game, nPlayers);
    }

    @Override
    public String getSender() {
        return user;
    }

    public JoinGameMessage(String user, String game, int nPlayers) {
        this.user = user;
        this.game = game;
        this.nPlayers = nPlayers;
    }
}
