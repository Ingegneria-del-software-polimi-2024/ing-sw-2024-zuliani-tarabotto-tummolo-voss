package Client;

import SharedWebInterfaces.ClientInterface;
import SharedWebInterfaces.ServerInterface;

import java.net.Socket;
import java.rmi.RemoteException;

public class SOCKET_ServerHandler implements ServerInterface {

    private ClientAPI_COME api;
    private Socket socket;
    @Override
    public void enqueueMessage() throws RemoteException {}

    @Override
    public void addNewPlayer() throws RemoteException {}

    @Override
    public void notifyChanges() throws RemoteException {}
}
