package Client.Web;

import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.Messages.MessagesToLobby.NewRMI_Connection;
import SharedWebInterfaces.SharedInterfaces.RMI_ManagerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMI_ServerHandler implements ServerHandlerInterface {

    private ClientAPI_COME api;
    private ClientHandlerInterface server;
    private RMI_ManagerInterface manager;

    private final String registryName = "Client";
    private final int localPort = 0; //TODO insert the port
    private final String myHost = ""; //TODO insert the host


    private final String serverHost;
    private final int serverPort;

    public void sendToServer(MessageFromClient message) throws RemoteException{server.sendToServer(message);}

//    @Override
//    public void addNewPlayer() throws RemoteException{}

    @Override
    public void notifyChanges(MessageFromServer message) throws RemoteException{api.notifyChanges(message);}

    @Override
    public void receiveFromLobby(MessageFromServer msg) throws RemoteException {
        if(msg instanceof WelcomeMessage){
//            try {
//                Registry registry1 = LocateRegistry.getRegistry(serverHost, serverPort);
//                server = (ClientHandlerInterface) registry1.lookup(((WelcomeMessage) msg).getRegistryName());
//            }catch (RemoteException | NotBoundException e){
//                throw new RemoteException();
//            }
            server = ((WelcomeMessage)msg).getServer();
        }
        api.enqueue(msg);
    }

    public RMI_ServerHandler(String host, int port, ClientAPI_COME come) throws StartConnectionFailedException {
        api = come;
        serverHost = host;
        serverPort = port;

        try {
            UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.createRegistry(localPort);
            registry.bind(registryName, this);

            Registry registry1 = LocateRegistry.getRegistry(host, port);
            manager = (RMI_ManagerInterface) registry1.lookup("Lobby");

            manager.deliverToLobby(new NewRMI_Connection(this));
        }catch (RemoteException | AlreadyBoundException | NotBoundException e){
            throw new StartConnectionFailedException();
        }
    }
}
