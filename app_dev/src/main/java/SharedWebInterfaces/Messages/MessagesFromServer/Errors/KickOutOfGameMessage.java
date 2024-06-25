package SharedWebInterfaces.Messages.MessagesFromServer.Errors;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Kick out of game message.
 */
public class KickOutOfGameMessage implements MessageFromServer {

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
