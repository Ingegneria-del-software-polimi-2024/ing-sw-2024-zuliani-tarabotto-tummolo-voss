package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.io.Serializable;
import java.util.ArrayList;

public class WelcomeMessage implements MessageFromServer {
    private final ArrayList<String> listOfGames;
//    private final String registryName;
    private final ClientHandlerInterface remoteServer;

    public WelcomeMessage(ArrayList<String> listOfGames) {
        if (listOfGames == null)
            this.listOfGames = new ArrayList<String>();
        else
            this.listOfGames = listOfGames;

//        registryName = null;
        remoteServer = null;
    }

    public ArrayList<String> getListOfGames() {return listOfGames;}

//    public String getRegistryName() {return registryName;}


    public WelcomeMessage(ArrayList<String> listOfGames, ClientHandlerInterface remoteServer){
        this.listOfGames = listOfGames;
//        this.registryName = registryName;
        this.remoteServer = remoteServer;

    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.setAvailableGames(listOfGames);
        view.askNickname();
    }

    public ClientHandlerInterface getServer(){
        return remoteServer;
    }
}
