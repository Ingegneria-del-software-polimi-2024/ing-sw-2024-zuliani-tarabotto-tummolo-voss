package Client;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.ClientHandlerInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMI_ServerHandler implements ServerHandlerInterface {

    private ClientAPI_COME api;
    private ClientHandlerInterface server;


    public void sendToServer(MessageFromClient message) throws RemoteException{server.sendToServer(message);}

    @Override
    public void addNewPlayer() throws RemoteException{}

    @Override
    public void notifyChanges(MessageFromServer message) throws RemoteException{api.notifyChanges(message);}

    public RMI_ServerHandler(String nickname, String serverHost, int serverPort, String lookupTableServer, String lookupTableClient, String clientHost, int clientPort, ClientAPI_COME come) throws RemoteException, AlreadyBoundException, NotBoundException {
        api = come;
        //writing in the table the present object in order for the object to find it
        UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.createRegistry(clientPort);
        registry.bind(lookupTableClient, this);

        //get the reference to client handler object on the server
        Registry registry1 = LocateRegistry.getRegistry(serverHost, serverPort);
        server = (ClientHandlerInterface) registry1.lookup(lookupTableServer);

        server.addNewPlayer(nickname, lookupTableClient, clientPort, clientHost);
    }
}
