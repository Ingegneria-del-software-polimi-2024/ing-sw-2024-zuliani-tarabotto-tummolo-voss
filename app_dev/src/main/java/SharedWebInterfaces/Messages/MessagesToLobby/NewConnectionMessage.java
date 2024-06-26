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
 * This message is sent by the client to the lobby when a new connection is established.
 * It contains the desired username of the client.
 */
public class NewConnectionMessage implements Serializable, MessageToLobby {
    /**
     * The desired username.
     */
    private String username;
    /**
     * The handler of the client in the server side.
     * When the message is sent by the client it is caught by the client's handler before sending and a self reference is added.
     * This allows the client to be able to send messages to its handler on the server.
     */
    private ClientHandlerInterface handler;

    public void execute(Lobby lobby){
        lobby.addConnection(username, handler);
    }

    @Override
    public String getSender() {
        return username;
    }

    /**
     * Instantiates a new connection message.
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
