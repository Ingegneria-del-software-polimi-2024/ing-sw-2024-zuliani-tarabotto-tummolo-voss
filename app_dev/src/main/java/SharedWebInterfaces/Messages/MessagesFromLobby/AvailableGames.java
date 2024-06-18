package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.util.ArrayList;

public class AvailableGames implements MessageFromServer {
    private ArrayList<String> availableGames;
    private boolean backFromWaitingRoom = false;
    @Override
    public void execute(ViewAPI_Interface view) {
        //TODO WTF we can delete the next line, it bugs everythin(!?)
        if(backFromWaitingRoom) view.returnToChooseGame(); //if we are coming back from the waitingRoom we call this method
        view.displayAvailableGames(availableGames);
    }

    public AvailableGames(ArrayList<String> availableGames) {
        this.availableGames = availableGames;
    }

    //we use this constructor only when we quit from the waitingRoom
    public AvailableGames(ArrayList<String> availableGames, boolean b) {
        this.backFromWaitingRoom = b;
        this.availableGames = availableGames;
    }
    public String toString(){
        String ret = "Available Games:";
        if(availableGames == null)
            return ret;
        for(String s : availableGames){
            ret = ret+"\n"+s;
        }
        return ret;
    }


}
