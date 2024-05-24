package SharedWebInterfaces.Messages.MessagesFromServer.Errors;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

public class KickOutOfGameMessage implements MessageFromServer {

    private String playerName;
    @Override
    public void execute(ViewAPI_Interface view) {

    }

    public KickOutOfGameMessage(String playerName) {
        this.playerName = playerName;
    }
}
