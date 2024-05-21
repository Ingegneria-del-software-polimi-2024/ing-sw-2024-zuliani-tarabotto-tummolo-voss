package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.util.ArrayList;

public class AvailableGames implements MessageFromServer {
    private ArrayList<String> availableGames;
    @Override
    public void execute(ViewAPI_Interface view) {
        view.displayAvailableGames(availableGames);
    }

    public AvailableGames(ArrayList<String> availableGames) {
        this.availableGames = availableGames;
    }

    public String toString(){
        String ret = "Giochi disponibili:";
        if(availableGames == null)
            return ret;
        for(String s : availableGames){
            ret = ret+"\n"+s;
        }
        return ret;
    }

}
