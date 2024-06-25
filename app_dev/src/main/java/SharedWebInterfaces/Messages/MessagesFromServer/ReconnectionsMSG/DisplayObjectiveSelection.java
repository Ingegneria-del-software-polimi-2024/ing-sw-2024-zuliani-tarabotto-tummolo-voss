package SharedWebInterfaces.Messages.MessagesFromServer.ReconnectionsMSG;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Display objective selection.
 */
public class DisplayObjectiveSelection implements MessageFromServer {
    private String plyerID;

    /**
     * Instantiates a new Display objective selection.
     *
     * @param plyerID the plyer id
     */
    public DisplayObjectiveSelection(String plyerID) {
        this.plyerID = plyerID;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.displaySecretObjective();
    }
}
