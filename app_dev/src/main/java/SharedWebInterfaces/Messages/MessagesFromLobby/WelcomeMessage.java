package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.io.Serializable;
import java.util.ArrayList;

public class WelcomeMessage implements MessageFromServer {
    private final ArrayList<String> listOfGames;
    private final String registryName;

    public WelcomeMessage(ArrayList<String> listOfGames) {
        if (listOfGames == null)
            this.listOfGames = new ArrayList<String>();
        else
            this.listOfGames = listOfGames;

        registryName = null;
    }

    public ArrayList<String> getListOfGames() {return listOfGames;}

    public String getRegistryName() {return registryName;}


    public WelcomeMessage(ArrayList<String> listOfGames, String registryName){
        this.listOfGames = listOfGames;
        this.registryName = registryName;

    }

    @Override
    public void execute(ViewAPI_Interface view) {

    }
}
