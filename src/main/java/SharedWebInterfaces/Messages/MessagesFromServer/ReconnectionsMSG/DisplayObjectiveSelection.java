package SharedWebInterfaces.Messages.MessagesFromServer.ReconnectionsMSG;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

public class DisplayObjectiveSelection implements MessageFromServer {
    private String plyerID;

    public DisplayObjectiveSelection(String plyerID) {
        this.plyerID = plyerID;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.displaySecretObjective();
    }
}
