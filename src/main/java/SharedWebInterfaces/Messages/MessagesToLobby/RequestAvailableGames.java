package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.MessagesFromLobby.AvailableGames;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;

/**
 * The type Request available games.
 * This message is sent by the client to the lobby to request the list of available games.
 */
public class RequestAvailableGames implements MessageToLobby{
    /**
     * The User's nickname.
     */
    private String user;
    @Override
    public void execute(Lobby lobby) {

        lobby.updateHeartBeat(user);
        try {
            lobby.sendToPlayer(user, new AvailableGames(lobby.getGameNames()));
        } catch (MsgNotDeliveredException e) {
            return;
        }
    }

    @Override
    public String getSender() {return null;}

    /**
     * Instantiates a new Request available games.
     *
     * @param user the user
     */
    public RequestAvailableGames(String user) {
        this.user = user;
    }
}
