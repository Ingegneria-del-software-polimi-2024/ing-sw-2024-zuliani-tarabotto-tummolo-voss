package SharedWebInterfaces.Messages.MessagesFromServer.Errors;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Kick out of game message.
 * This message is sent to the client when the server kicks a player out of the game.
 * The client will then return to the lobby.
 * This message is sent to the client when the game ends and when a player quits a room.
 */
public class KickOutOfGameMessage implements MessageFromServer {
    /**
     * The Player's name.
     */
    private String playerName;
    @Override
    public void execute(ViewAPI_Interface view) {
        view.returnToLobby();
    }

    /**
     * Instantiates a new Kick out of game message.
     *
     * @param playerName the player name
     */
    public KickOutOfGameMessage(String playerName) {
        this.playerName = playerName;
    }
}
