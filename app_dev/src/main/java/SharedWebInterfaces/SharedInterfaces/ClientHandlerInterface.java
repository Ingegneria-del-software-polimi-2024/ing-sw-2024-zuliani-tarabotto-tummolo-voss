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
     * Sends a message to server.
     *
     * @param message the message
     * @throws RemoteException when the message is not correctly delivered
     */
//Go
    public void sendToServer(MessageFromClient message) throws RemoteException;


    /**
     * Sends a message to client.
     *
     * @param message the message
     * @throws RemoteException when the message is not correctly delivered
     */
//Come
    public void notifyChanges(MessageFromServer message) throws RemoteException;

    /**
     * Sets receiver, the receiver is the serverAPI_COME.
     *
     * @param receiver the receiver
     * @throws RemoteException when a network error occurs
     */
    public void setReceiver(ServerAPI_COME receiver) throws RemoteException;

    /**
     * Sends a message from lobby to client.
     *
     * @param msg the message from lobby
     * @throws RemoteException when a network error occurs
     */
    public void sendToClient(MessageFromServer msg) throws RemoteException;

    /**
     * Delivers a message to lobby.
     *
     * @param msg the message to deliver
     * @throws RemoteException when a network error occurs
     */
    public void deliverToLobby(MessageToLobby msg) throws RemoteException;
}
