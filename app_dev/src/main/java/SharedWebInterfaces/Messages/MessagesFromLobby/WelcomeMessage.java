package SharedWebInterfaces.Messages.MessagesFromLobby;

import java.io.Serializable;
import java.util.ArrayList;

public class WelcomeMessage implements Serializable, MessageFromLobby {
    private final ArrayList<String> listOfGames;
    private final String registryName;

    public WelcomeMessage(ArrayList<String> listOfGames) {
        if (listOfGames == null)
            this.listOfGames = new ArrayList<String>();
        else
            this.listOfGames = listOfGames;

        registryName = null;
    }

    public ArrayList<String> getListOfGames() {
        return listOfGames;
    }

    @Override
    public void execute() {}

    public WelcomeMessage(ArrayList<String> listOfGames, String name){
        this.listOfGames = new ArrayList<String>();
        registryName = name;

    }
}
