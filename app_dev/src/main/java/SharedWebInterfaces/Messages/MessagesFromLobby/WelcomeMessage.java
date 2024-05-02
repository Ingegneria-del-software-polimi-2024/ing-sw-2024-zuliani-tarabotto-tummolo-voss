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

    public ArrayList<String> getListOfGames() {return listOfGames;}

    public String getRegistryName() {return registryName;}

    @Override
    public void execute() {}

    public WelcomeMessage(ArrayList<String> listOfGames, String registryName){
        this.listOfGames = listOfGames;
        this.registryName = registryName;

    }

    public String toString(){
        String ret = "Giochi disponibili:";
        if(listOfGames == null)
            return ret;
        for(String s : listOfGames){
            ret = ret+"\n"+s;
        }
        return ret;
    }
}
