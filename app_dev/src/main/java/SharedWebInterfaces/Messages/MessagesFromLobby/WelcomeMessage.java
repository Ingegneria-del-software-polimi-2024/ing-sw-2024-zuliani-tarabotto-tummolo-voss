package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.util.ArrayList;

/**
 * The type Welcome message.
 */
public class WelcomeMessage implements MessageFromServer {
    private final ArrayList<String> listOfGames;
    private final ClientHandlerInterface remoteServer;

    /**
     * Instantiates a new Welcome message.
     *
     * @param listOfGames the list of games
     */
    public WelcomeMessage(ArrayList<String> listOfGames) {
        if (listOfGames == null)
            this.listOfGames = new ArrayList<String>();
        else
            this.listOfGames = listOfGames;
        remoteServer = null;
    }

    /**
     * Gets list of games.
     *
     * @return the list of games
     */
    public ArrayList<String> getListOfGames() {return listOfGames;}


    /**
     * Instantiates a new Welcome message.
     *
     * @param listOfGames  the list of games
     * @param remoteServer the remote server
     */
    public WelcomeMessage(ArrayList<String> listOfGames, ClientHandlerInterface remoteServer){
        this.listOfGames = listOfGames;
        this.remoteServer = remoteServer;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.startHeartbeatThread();
        view.setAvailableGames(listOfGames);
        view.askNickname();
    }

    /**
     * Get server client handler interface.
     *
     * @return the client handler interface
     */
    public ClientHandlerInterface getServer(){
        return remoteServer;
    }
}
