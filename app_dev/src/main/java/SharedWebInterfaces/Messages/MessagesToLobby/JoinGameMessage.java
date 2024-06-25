package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_RoomChoice;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;

/**
 * The type Join game message.
 */
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

    /**
     * Instantiates a new Join game message.
     *
     * @param user     the user
     * @param game     the game
     * @param nPlayers the n players
     */
    public JoinGameMessage(String user, String game, int nPlayers) {
        this.user = user;
        this.game = game;
        this.nPlayers = nPlayers;
    }
}
