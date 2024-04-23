package SharedWebInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    //private ServerAPI_COME api

    //Go
    public void enqueueMethod(/*parameters*/) throws RemoteException;

    public void addNewPlayer(/*parameters*/) throws RemoteException;

    //Come
    public void notifyChanges(/*parameters*/) throws RemoteException;
}
