package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;

/**
 * The type Quit game before start message.
 */
public class QuitGameBeforeStartMessage implements MessageToLobby{

    private String roomName;
    private String player;

    /**
     * Instantiates a new Quit game before start message.
     *
     * @param roomName the room name
     * @param player   the player
     */
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
