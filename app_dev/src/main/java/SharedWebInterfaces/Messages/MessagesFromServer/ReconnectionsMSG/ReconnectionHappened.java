package SharedWebInterfaces.Messages.MessagesFromServer.ReconnectionsMSG;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Reconnection happened.
 */
public class ReconnectionHappened implements MessageFromServer {
    /**
     * The Player id.
     */
    String playerID;
    @Override
    public void execute(ViewAPI_Interface view) {
        view.ackNickName(playerID);
        view.displayReconnection();
    }

    /**
     * Instantiates a new Reconnection happened.
     *
     * @param playerID the player id
     */
    public ReconnectionHappened(String playerID) {
        this.playerID = playerID;
    }
}
