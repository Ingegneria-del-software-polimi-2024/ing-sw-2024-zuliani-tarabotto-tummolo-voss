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
     * Send to server, sends a message from the client to the server.
     *
     * @param message the message
     * @throws RemoteException the remote exception
     */
//COME
    void sendToServer(MessageFromClient message)throws RemoteException;

    /**
     * Send to client, sends a message from the server to the client.
     *
     * @param message the message
     * @throws RemoteException the remote exception
     */
//GO
    void notifyChanges(MessageFromServer message)throws RemoteException;

    /**
     * Receives a message from lobby.
     *
     * @param msg the message
     * @throws RemoteException when the message is not correctly received
     */
    void receiveFromLobby(MessageFromServer msg)throws RemoteException;

    /**
     * Sends a message to lobby.
     *
     * @param msg the message
     * @throws RemoteException when the message is not correctly sent
     */
    void sendToLobby(MessageToLobby msg) throws RemoteException;

}
