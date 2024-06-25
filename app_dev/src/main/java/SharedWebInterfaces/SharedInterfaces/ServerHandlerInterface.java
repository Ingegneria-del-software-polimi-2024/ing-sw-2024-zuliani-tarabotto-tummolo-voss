package SharedWebInterfaces.SharedInterfaces;

import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * this is the server handler ON THE CLIENT,
 * it is an interface that keeps the connection open
 */
public interface ServerHandlerInterface extends Remote{

    /**
     * Send to server.
     *
     * @param message the message
     * @throws RemoteException the remote exception
     */
//COME
    void sendToServer(MessageFromClient message)throws RemoteException;

    /**
     * Notify changes.
     *
     * @param message the message
     * @throws RemoteException the remote exception
     */
//GO
    void notifyChanges(MessageFromServer message)throws RemoteException;

    /**
     * Receive from lobby.
     *
     * @param msg the msg
     * @throws RemoteException the remote exception
     */
    void receiveFromLobby(MessageFromServer msg)throws RemoteException;

    /**
     * Send to lobby.
     *
     * @param msg the msg
     * @throws RemoteException the remote exception
     */
    void sendToLobby(MessageToLobby msg) throws RemoteException;

}
