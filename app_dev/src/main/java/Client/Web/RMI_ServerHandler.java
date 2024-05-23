package Client.Web;

import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
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
    private String serverHost;

    public void sendToServer(MessageFromClient message) throws RemoteException{server.sendToServer(message);}
    public void sendToLobby(MessageToLobby message)throws RemoteException{server.deliverToLobby(message);}

    @Override
    public void notifyChanges(MessageFromServer message) throws RemoteException{api.notifyChanges(message);}

    @Override
    public void receiveFromLobby(MessageFromServer msg) throws RemoteException {
        if(msg instanceof WelcomeMessage){
            server = ((WelcomeMessage)msg).getServer();
        }
        api.enqueue(msg);
    }

    public RMI_ServerHandler(String host, int port, ClientAPI_COME come) throws StartConnectionFailedException {
        api = come;
        serverHost = host;
        try {
            UnicastRemoteObject.exportObject(this,  0);


            Registry registry1 = LocateRegistry.getRegistry(host, port);
            manager = (RMI_ManagerInterface) registry1.lookup(WebSettings.serverRegistryName);

            manager.deliverToLobby(new NewRMI_Connection(this));
        }catch (RemoteException | NotBoundException e){
            throw new StartConnectionFailedException();
        }
    }
}
