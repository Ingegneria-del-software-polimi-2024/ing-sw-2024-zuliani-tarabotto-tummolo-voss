package SharedWebInterfaces.Messages.MessagesToLobby;


import Client.Web.RMI_ServerHandler;
import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

/**
 * The type New rmi connection.
 */
public class NewRMI_Connection implements MessageToLobby {
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
     * Instantiates a new New rmi connection.
     *
     * @param handler the handler
     */
    public NewRMI_Connection(ServerHandlerInterface handler) {
        this.handler = handler;
    }
}
