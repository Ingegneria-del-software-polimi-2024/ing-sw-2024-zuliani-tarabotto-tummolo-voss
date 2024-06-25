package SharedWebInterfaces.Messages.MessagesFromServer.ReconnectionsMSG;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

public class DisplayStarterCardSelection implements MessageFromServer {
    private String playerID;

    public DisplayStarterCardSelection(String playerID) {
        this.playerID = playerID;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.displayStarterCard();
    }
}
