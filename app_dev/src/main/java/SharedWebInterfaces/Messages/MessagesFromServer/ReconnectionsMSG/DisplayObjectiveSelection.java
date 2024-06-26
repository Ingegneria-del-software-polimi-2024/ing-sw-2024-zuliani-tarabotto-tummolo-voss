package SharedWebInterfaces.Messages.MessagesFromServer.ReconnectionsMSG;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Display objective selection.
 * This message is sent to the client to display the secret objective selection after a reconnection.
 */
public class DisplayObjectiveSelection implements MessageFromServer {
    /**
     * The Player id.
     */
    private String playerID;

    /**
     * Instantiates a new Display objective selection.
     *
     * @param playerID the player id
     */
    public DisplayObjectiveSelection(String playerID) {
        this.playerID = playerID;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.displaySecretObjective();
    }
}
