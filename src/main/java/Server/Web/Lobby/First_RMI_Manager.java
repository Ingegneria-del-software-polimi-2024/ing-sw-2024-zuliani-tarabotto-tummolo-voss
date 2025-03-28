package Server.Web.Lobby;

import SharedWebInterfaces.SharedInterfaces.RMI_ManagerInterface;
import Server.Web.Game.RMI_ClientHandler;
import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * The type First rmi manager.
 * This class is the first RMI manager to be created, it is responsible for the creation of the RMI connection and for
 * the identification of potential RMI clients. It is also responsible for the creation of the RMI_ClientHandler when a
 * new client connects to the server.
 * It is a singleton class.
 */
public class First_RMI_Manager implements RMI_ManagerInterface {
    /**
     * The Lobby interface.
     */
    private Lobby lobby;
    /**
     * The port of the server.
     */
    private int serverPort;
    /**
     * The instance of the class, it is a singleton.
     */
    private static First_RMI_Manager instance;

    /**
     * Class constructor
     * @param lobby the lobby-controller
     * @param serverPort the port of the server
     * @throws RemoteException when an error in setting of the connection occurs
     */
    private First_RMI_Manager(Lobby lobby, int serverPort) throws RemoteException{
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String ip = localHost.getHostAddress();
            System.out.println("Server ip is: "+ip);
            System.setProperty("java.rmi.server.hostname",ip);
            this.lobby = lobby;
            this.serverPort = serverPort;
            UnicastRemoteObject.exportObject(this, serverPort);
            Registry registry = LocateRegistry.createRegistry(serverPort);
            registry.bind("Lobby", this);
            System.out.println("RMI interface ready to pair");
        }catch (AlreadyBoundException | UnknownHostException e){
            throw new RemoteException();
        }
    }

    /**
     * The class is a singleton, returns the single instance of the class. See First_RMI_Manager constructor
     *
     * @param lobby      the lobby interface
     * @param serverPort the server port
     * @return the first rmi manager
     * @throws RemoteException when an error in setting of the connection occurs
     */
    public static First_RMI_Manager getInstance(Lobby lobby, int serverPort) throws RemoteException {
        if(instance == null)
            instance =  new First_RMI_Manager(lobby, serverPort);
        return instance;
    }

    /**
     * Enqueues in the message queue of the lobby a new incoming message
     * @param msg incoming message from client
     */
    public void deliverToLobby(MessageToLobby msg){
        lobby.enqueueMessage(msg);
    }

    /**
     * When receiving a request for a new connection creates a new RMI_handler for the client and sends a WelcomeMessage
     * to the client after the connection
     * @param clientRemote the remote interface of the client
     * @param games the available games, to be sent to the client
     * @throws RemoteException when an error in sending the WelcomeMessage occurs
     */
    public void newHandler(ServerHandlerInterface clientRemote, ArrayList<String> games) throws RemoteException {
        RMI_ClientHandler rmiClientHandler = new RMI_ClientHandler(clientRemote, lobby, serverPort);
        rmiClientHandler.sendToClient(new WelcomeMessage(games, rmiClientHandler));
    }

}
