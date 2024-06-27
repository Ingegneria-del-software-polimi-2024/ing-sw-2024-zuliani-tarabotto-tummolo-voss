package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;

/**
 * The type Quit game before start message.
 * This message is sent to the lobby when a player quits the game before it starts (in the waiting room).
 */
public class QuitGameBeforeStartMessage implements MessageToLobby{
    /**
     * The Room name.
     */
    private String roomName;
    /**
     * The Player's name.
     */
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
