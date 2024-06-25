package SharedWebInterfaces.Messages.MessagesFromServer.ReconnectionsMSG;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

public class ReconnectionHappened implements MessageFromServer {
    String playerID;
    @Override
    public void execute(ViewAPI_Interface view) {
        view.ackNickName(playerID);
        view.displayReconnection();
    }

    public ReconnectionHappened(String playerID) {
        this.playerID = playerID;
    }
}
