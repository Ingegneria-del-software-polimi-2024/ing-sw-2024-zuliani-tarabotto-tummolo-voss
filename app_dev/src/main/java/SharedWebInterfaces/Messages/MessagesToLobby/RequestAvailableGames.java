package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.MessagesFromLobby.AvailableGames;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;

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

    public RequestAvailableGames(String user) {
        this.user = user;
    }
}
