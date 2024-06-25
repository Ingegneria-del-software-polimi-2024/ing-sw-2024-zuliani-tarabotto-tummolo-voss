package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.MessagesFromLobby.AvailableGames;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;

/**
 * The type Request available games.
 */
public class RequestAvailableGames implements MessageToLobby{
    private String user;
    @Override
    public void execute(Lobby lobby) {

        lobby.updateHeartBeat(user);
        try {
            lobby.sendToPlayer(user, new AvailableGames(lobby.getGameNames()));
        } catch (MsgNotDeliveredException e) {
            throw new RuntimeException();
            //TODO verify if handled correctly
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
