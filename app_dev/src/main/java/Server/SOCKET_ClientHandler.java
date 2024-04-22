package Server;

import SharedWebInterfaces.ServerInterface;

import java.net.Socket;
import java.rmi.RemoteException;

public class SOCKET_ClientHandler implements ServerInterface {
    private ServerAPI_COME api;
    private Socket socket;
    private String clientID;

    @Override
    public void enqueueMethod() throws RemoteException {}

    @Override
    public void addNewPlayer() throws RemoteException {}

    @Override
    public void notifyChanges() throws RemoteException {}
}
