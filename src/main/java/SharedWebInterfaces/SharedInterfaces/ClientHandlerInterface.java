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

    //Go
    public void sendToServer(MessageFromClient message) throws RemoteException;


    //Come
    public void notifyChanges(MessageFromServer message) throws RemoteException;

    public void setReceiver(ServerAPI_COME receiver) throws RemoteException;

    public void sendToClient(MessageFromServer msg) throws RemoteException;
    public void deliverToLobby(MessageToLobby msg) throws RemoteException;
}
