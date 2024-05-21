package Server.Web.Lobby;

import SharedWebInterfaces.SharedInterfaces.RMI_ManagerInterface;
import Server.Web.Game.RMI_ClientHandler;
import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class First_RMI_Manager implements RMI_ManagerInterface {
    private Lobby lobby;
    private int serverPort;
    private static First_RMI_Manager instance;

    /**
     * class constructor
     * @param lobby the lobby-controller
     * @param serverPort the port of the server
     * @throws RemoteException when an error in setting of the connection occurs
     */
    private First_RMI_Manager(Lobby lobby, int serverPort) throws RemoteException{
        try {
            System.setProperty("java.rmi.server.hostname","172.20.10.2");
            this.lobby = lobby;
            this.serverPort = serverPort;
            UnicastRemoteObject.exportObject(this, serverPort);
            Registry registry = LocateRegistry.createRegistry(serverPort);
            registry.bind("Lobby", this);
            System.out.println("RMI interface ready to pair");
        }catch (AlreadyBoundException e){
            throw new RemoteException();
        }
    }

    /**
     * the class is a singleton, returns the single instance of the class. See First_RMI_Manager constructor
     */
    public static First_RMI_Manager getInstance(Lobby lobby, int serverPort) throws RemoteException {
        if(instance == null)
            instance =  new First_RMI_Manager(lobby, serverPort);
        return instance;
    }

    /**
     * enqueues in the message queue of the lobby a new incoming message
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
