package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_RoomChoice;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;

/**
 * The type Join game message.
 * This message is sent by the client to the lobby to join or create a game.
 * It contains the number of expected players and the name of the game.
 * When the number of expected players is 0 it means that the user wants to join the specified game,
 * else they want to create a new game with that name.
 */
public class JoinGameMessage implements MessageToLobby{
    /**
     * The player's name.
     */
    private String user;
    /**
     * The game's name.
     */
    private String game;
    /**
     * The number of expected players.
     */
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
     * @param nPlayers the number of expected players
     */
    public JoinGameMessage(String user, String game, int nPlayers) {
        this.user = user;
        this.game = game;
        this.nPlayers = nPlayers;
    }
}
