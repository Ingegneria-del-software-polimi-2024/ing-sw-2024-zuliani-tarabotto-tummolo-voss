package Server;

import MockModel.Lobby;
import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.ServerHandlerInterface;

import java.lang.reflect.Array;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class First_RMI_Manager implements Remote {
    private Lobby lobby;
    private ArrayList<ServerHandlerInterface> clients;
    public First_RMI_Manager(Lobby lobby, int serverPort) throws RemoteException, AlreadyBoundException {
        this.lobby = lobby;
        this.clients = new ArrayList<ServerHandlerInterface>();
        UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.createRegistry(serverPort);
        registry.bind("Lobby", this);
    }

    public void newConnection(int port, String host, String clientName) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        ServerHandlerInterface client = (ServerHandlerInterface) registry.lookup(clientName);
        System.out.println("Client is bound with lobby");
        synchronized (clients){
            clients.add(client);
        }
        client.receiveFromLobby(new WelcomeMessage(lobby.getGameNames()));
    }

    public void deliverToLobby(MessageToLobby msg){
        lobby.enqueueMessage(msg);
    }

}
