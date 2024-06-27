package SharedWebInterfaces.Messages.MessagesToLobby;


import Client.Web.RMI_ServerHandler;
import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

/**
 * The type New rmi connection.
 * This message is used to notify the lobby that a new RMI connection has been established.
 */
public class NewRMI_Connection implements MessageToLobby {
    /**
     * The server handler.
     * When a client wants to connnect to the server via RMI, the server must receive
     * a ServerHandlerInterface object to be able to communicate with the client.
     */
    private ServerHandlerInterface handler;


    @Override
    public void execute(Lobby lobby){
        lobby.newRMI_Connection(handler);
    }

    @Override
    public String getSender() {
        return null;
    }


    /**
     * Instantiates a new rmi connection.
     *
     * @param handler the handler
     */
    public NewRMI_Connection(ServerHandlerInterface handler) {
        this.handler = handler;
    }
}
