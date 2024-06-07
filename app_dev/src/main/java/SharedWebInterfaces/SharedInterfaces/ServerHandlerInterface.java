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

    //COME
    void sendToServer(MessageFromClient message)throws RemoteException;

    //GO
    void notifyChanges(MessageFromServer message)throws RemoteException;
    void receiveFromLobby(MessageFromServer msg)throws RemoteException;
    void sendToLobby(MessageToLobby msg) throws RemoteException;

}
