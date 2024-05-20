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
        lobby.enterRoom(user, game, nPlayers);
        //For debug purpose only
        System.out.println(user+" has entered the room "+game);

        //sending ACK
        try {
            lobby.sendToPlayer(user, new ACK_RoomChoice(user, game));
        } catch (MsgNotDeliveredException e) {
            throw new RuntimeException(e);
            //TODO verify this is handled correctly
        }
        lobby.verifyStart(game);
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
