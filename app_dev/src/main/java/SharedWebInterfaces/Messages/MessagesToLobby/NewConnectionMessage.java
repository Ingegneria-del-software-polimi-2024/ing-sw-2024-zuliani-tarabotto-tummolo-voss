package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_NewConnection;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_RoomChoice;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;

import java.io.IOException;
import java.io.Serializable;

/**
 * The type New connection message.
 */
public class NewConnectionMessage implements Serializable, MessageToLobby {

    private String username;
    //private String roomName;
    //private int expectedPlayers;

    private ClientHandlerInterface handler;

    public void execute(Lobby lobby){
        lobby.addConnection(username, handler);
    }

    @Override
    public String getSender() {
        return username;
    }

    /**
     * Instantiates a new New connection message.
     *
     * @param username the username
     */
    public NewConnectionMessage(String username) {
        this.username = username;
    }

    /**
     * Sets handler.
     *
     * @param handler the handler
     */
    public void setHandler(ClientHandlerInterface handler) {
        this.handler = handler;
    }

}
