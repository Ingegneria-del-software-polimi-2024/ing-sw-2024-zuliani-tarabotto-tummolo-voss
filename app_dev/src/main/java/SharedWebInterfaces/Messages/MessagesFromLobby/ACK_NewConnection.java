package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Ack new connection.
 */
public class ACK_NewConnection implements MessageFromServer {
    private String user;
    @Override
    public void execute(ViewAPI_Interface view) {
        view.ackNickName(user);
        view.displayAvailableGames();
    }

    /**
     * Instantiates a new Ack new connection.
     *
     * @param user the user
     */
    public ACK_NewConnection(String user) {
        this.user = user;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }
}
