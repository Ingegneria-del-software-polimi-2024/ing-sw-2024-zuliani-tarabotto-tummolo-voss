package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.util.ArrayList;

/**
 * The type Welcome message.
 * This message is sent by the server to the client when the client connects for the first time to the server.
 */
public class WelcomeMessage implements MessageFromServer {
    /**
     * The List of available games for the client to join.
     */
    private final ArrayList<String> listOfGames;
    /**
     * The handler of the client server side.
     * In RMI connections the reference to the remote client handler must be passed to the client in order to be used.
     * This variable stores the reference to the remote client handler.
     * This variable is null in case of socket connections.
     */
    private final ClientHandlerInterface remoteServer;

    /**
     * Instantiates a new Welcome message in case of Socket connection, thus the remote server is null.
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
     *
     * @return the list of available games
     */
    public ArrayList<String> getListOfGames() {return listOfGames;}


    /**
     * Instantiates a new Welcome message in case of RMI connection, thus the remote server is passed to the client.
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
