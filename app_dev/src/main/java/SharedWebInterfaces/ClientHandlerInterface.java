package SharedWebInterfaces;

import SharedWebInterfaces.Messages.MessageFromClient;
import SharedWebInterfaces.Messages.MessageFromServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * this is the client handler ON THE SERVER,
 * it is an interface that keeps the connection open
 */
public interface ClientHandlerInterface extends Remote {
    //private ServerAPI_COME api

    //Go
    public void sendToServer(MessageFromClient message) throws RemoteException;

    public void addNewPlayer(/*parameters*/) throws RemoteException;

    //Come
    public void notifyChanges(MessageFromServer message) throws RemoteException;
}
