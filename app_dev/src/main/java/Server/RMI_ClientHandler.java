package Server;

import SharedWebInterfaces.ServerInterface;

import java.rmi.RemoteException;

public class RMI_ClientHandler implements ServerInterface {
    private ServerAPI_COME api;

    //Go
    public void enqueueMessage(/*parameters*/) throws RemoteException{}

    public void addNewPlayer(/*parameters*/) throws RemoteException{}

    //Come
    public void notifyChanges(/*parameters*/) throws RemoteException{}
}
