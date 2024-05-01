package SharedWebInterfaces.Messages.MessagesFromLobby;

import java.io.Serializable;
import java.util.ArrayList;

public class WelcomeMessage implements Serializable, MessageFromLobby {
    private final ArrayList<String> listOfGames;

    public WelcomeMessage(ArrayList<String> listOfGames) {
        if (listOfGames == null)
            this.listOfGames = new ArrayList<String>();
        else
            this.listOfGames = listOfGames;
    }

    public ArrayList<String> getListOfGames() {
        return listOfGames;
    }

    @Override
    public void execute() {

    }
}
