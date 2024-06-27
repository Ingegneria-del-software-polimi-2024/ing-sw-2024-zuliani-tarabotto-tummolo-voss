package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.util.ArrayList;

/**
 * The type Available games.
 * This class is used to display the available games to the user
 */
public class AvailableGames implements MessageFromServer {
    /**
     * The list containing the names of the available games.
     */
    private ArrayList<String> availableGames;
    /**
     * The boolean that tells us if we are coming back from the waitingRoom.
     * True if we are coming back from the waitingRoom, false otherwise.
     */
    private boolean backFromWaitingRoom = false;
    @Override
    public void execute(ViewAPI_Interface view) {
        if(backFromWaitingRoom) view.returnToChooseGame(); //if we are coming back from the waitingRoom we call this method
        view.displayAvailableGames(availableGames);
    }

    /**
     * Instantiates a new Available games.
     *
     * @param availableGames the available games
     */
    public AvailableGames(ArrayList<String> availableGames) {
        this.availableGames = availableGames;
    }

    /**
     * Instantiates a new Available games, setting the flag backFromWaitingRoom.
     * BackFromWaitingRoom is set to true if we are coming back from the waitingRoom.
     *
     * @param availableGames the available games
     * @param b              the boolean to set the flag backFromWaitingRoom
     */
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
