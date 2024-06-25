package SharedWebInterfaces.Messages.MessagesFromServer.ReconnectionsMSG;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Display starter card selection.
 */
public class DisplayStarterCardSelection implements MessageFromServer {
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
