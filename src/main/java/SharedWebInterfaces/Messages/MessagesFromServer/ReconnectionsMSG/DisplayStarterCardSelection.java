package SharedWebInterfaces.Messages.MessagesFromServer.ReconnectionsMSG;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Display starter card selection.
 * This message is sent to the client to display the starter card selection after a reconnection
 */
public class DisplayStarterCardSelection implements MessageFromServer {
    /**
     * The Player id.
     */
    private String playerID;

    /**
     * Instantiates a new Display starter card selection.
     *
     * @param playerID the player id
     */
    public DisplayStarterCardSelection(String playerID) {
        this.playerID = playerID;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.displayStarterCard();
    }
}
