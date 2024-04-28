package Client;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.ClientHandlerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMI_ServerHandler implements ServerHandlerInterface {

    private ClientAPI_COME api;
    private ClientHandlerInterface server;


    public void sendToServer(MessageFromClient message) throws RemoteException{server.sendToServer(message);}

    @Override
    public void addNewPlayer() throws RemoteException{}

    @Override
    public void notifyChanges(MessageFromServer message) throws RemoteException{api.notifyChanges(message);}

    public RMI_ServerHandler(String host, int port, String name) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        server = (ClientHandlerInterface) registry.lookup(name);

    }
}
