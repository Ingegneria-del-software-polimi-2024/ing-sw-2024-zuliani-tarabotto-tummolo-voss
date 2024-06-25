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
 */
public interface RMI_ManagerInterface extends Remote {
    /**
     * Deliver to lobby.
     *
     * @param msg the msg
     * @throws RemoteException the remote exception
     */
//    public void newHandler(String clientRegistry, String clientHost, int clientPort, ArrayList<String> games) throws RemoteException;
    public void deliverToLobby(MessageToLobby msg) throws RemoteException;

    /**
     * New handler.
     *
     * @param clientRemote the client remote
     * @param games        the games
     * @throws RemoteException the remote exception
     */
    public void newHandler(ServerHandlerInterface clientRemote, ArrayList<String> games) throws RemoteException;
}
