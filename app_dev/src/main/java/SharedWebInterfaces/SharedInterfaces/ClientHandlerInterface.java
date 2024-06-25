package SharedWebInterfaces.SharedInterfaces;

import Server.Web.Game.ServerAPI_COME;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * this is the client handler ON THE SERVER,
 * it is an interface that keeps the connection open
 */
public interface ClientHandlerInterface extends Remote {

    /**
     * Send to server.
     *
     * @param message the message
     * @throws RemoteException the remote exception
     */
//Go
    public void sendToServer(MessageFromClient message) throws RemoteException;


    /**
     * Notify changes.
     *
     * @param message the message
     * @throws RemoteException the remote exception
     */
//Come
    public void notifyChanges(MessageFromServer message) throws RemoteException;

    /**
     * Sets receiver.
     *
     * @param receiver the receiver
     * @throws RemoteException the remote exception
     */
    public void setReceiver(ServerAPI_COME receiver) throws RemoteException;

    /**
     * Send to client.
     *
     * @param msg the msg
     * @throws RemoteException the remote exception
     */
    public void sendToClient(MessageFromServer msg) throws RemoteException;

    /**
     * Deliver to lobby.
     *
     * @param msg the msg
     * @throws RemoteException the remote exception
     */
    public void deliverToLobby(MessageToLobby msg) throws RemoteException;
}
