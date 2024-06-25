package SharedWebInterfaces.SharedInterfaces;

import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * The interface Rmi manager interface.
 * This interface is used by the client to communicate with the RMI Manager.
 */
public interface RMI_ManagerInterface extends Remote {
    /**
     * Delivers a message to lobby.
     *
     * @param msg the message to deliver
     * @throws RemoteException when the message is not delivered correctly
     */
//    public void newHandler(String clientRegistry, String clientHost, int clientPort, ArrayList<String> games) throws RemoteException;
    public void deliverToLobby(MessageToLobby msg) throws RemoteException;

    /**
     * Creates a new handler for the newly connected client.
     *
     * @param clientRemote the client remote
     * @param games        the games
     * @throws RemoteException when the handler is not created correctly
     */
    public void newHandler(ServerHandlerInterface clientRemote, ArrayList<String> games) throws RemoteException;
}
