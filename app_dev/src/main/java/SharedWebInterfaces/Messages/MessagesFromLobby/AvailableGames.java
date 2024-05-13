package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.util.ArrayList;

public class AvailableGames implements MessageFromServer {
    private ArrayList<String> availableGames;
    @Override
    public void execute(ViewAPI_Interface view) {
        //TODO implement!
    }

    public AvailableGames(ArrayList<String> availableGames) {
        this.availableGames = availableGames;
    }
}
