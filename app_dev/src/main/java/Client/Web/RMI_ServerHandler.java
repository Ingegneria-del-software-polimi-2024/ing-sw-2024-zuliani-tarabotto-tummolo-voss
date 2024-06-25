package Client.Web;

import Chat.MessagesFromServer.ChatHistoryMessage;
import Chat.MessagesFromServer.ChatUpdateMessage;
import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.Messages.MessagesToLobby.NewRMI_Connection;
import SharedWebInterfaces.SharedInterfaces.RMI_ManagerInterface;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;
import SharedWebInterfaces.WebSettings;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The type Rmi server handler.
 * An interface mantaining the RMI connection with the server.
 */
public class RMI_ServerHandler implements ServerHandlerInterface {

    /**
     * The interface managing incoming messages
     */
    private ClientAPI_COME api;
    /**
     * The server
     */
    private ClientHandlerInterface server;
    /**
     * The server RMI manager
     */
    private RMI_ManagerInterface manager;
    /**
     * The ip of the server
     */
    private String serverHost;

    /**
     * Sends the message to the server
     * @param message message to be sent
     * @throws RemoteException if the message couldn't be delivered
     */
    public void sendToServer(MessageFromClient message) throws RemoteException{server.sendToServer(message);}

    /**
     * Sends the message to the lobby
     * @param message message to be sent
     * @throws RemoteException if the message couldn't be delivered
     */
    public void sendToLobby(MessageToLobby message)throws RemoteException{server.deliverToLobby(message);}

    /**
     * Forwards the incoming message to the client API incoming interface
     * @param message incoming message
     * @throws RemoteException if an error in the network happens
     */
    @Override
    public void notifyChanges(MessageFromServer message) throws RemoteException{
        if(message instanceof ChatHistoryMessage || message instanceof ChatUpdateMessage){
            api.enqueueChatMessage(message);
            return;
        }
        api.notifyChanges(message);
    }

    /**
     * Forwards the incoming message to the client API incoming interface
     * @param msg incoming message
     * @throws RemoteException if an error in the network happens
     */
    @Override
    public void receiveFromLobby(MessageFromServer msg) throws RemoteException {
        if(msg instanceof WelcomeMessage){
            server = ((WelcomeMessage)msg).getServer();
        }
        api.enqueue(msg);
    }

    /**
     * Class constructor
     *
     * @param host the hostname of the server
     * @param come the interface for the reception of the messages
     * @throws StartConnectionFailedException if an error in the instantiation of the connection happens
     */
    public RMI_ServerHandler(String host, ClientAPI_COME come) throws StartConnectionFailedException {
        api = come;
        serverHost = host;
    }

    /**
     * Connects with the server.
     *
     * @param port the port
     * @throws StartConnectionFailedException if an error in the instantiation of the connection happens
     */
    public void connect(int port) throws StartConnectionFailedException {
        try {

            InetAddress localHost = InetAddress.getLocalHost();
            String ip = localHost.getHostAddress();
            System.setProperty("java.rmi.server.hostname", ip);
            UnicastRemoteObject.exportObject(this,  0);


            Registry registry1 = LocateRegistry.getRegistry(serverHost, port);
            manager = (RMI_ManagerInterface) registry1.lookup(WebSettings.serverRegistryName);

            manager.deliverToLobby(new NewRMI_Connection(this));
        }catch (RemoteException | NotBoundException | UnknownHostException e){
            throw new StartConnectionFailedException();
        }
    }
}
