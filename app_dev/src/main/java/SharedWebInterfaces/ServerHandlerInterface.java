package SharedWebInterfaces;

import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * this is the server handler ON THE CLIENT,
 * it is an interface that keeps the connection open
 */
public interface ServerHandlerInterface extends Remote {

    //private ClientAPI_COME api

    //COME
    public void sendToServer(MessageFromClient message)throws RemoteException;

    public void addNewPlayer(/*parameters*/)throws RemoteException;

    //GO
    public void notifyChanges(MessageFromServer message)throws RemoteException;
}
