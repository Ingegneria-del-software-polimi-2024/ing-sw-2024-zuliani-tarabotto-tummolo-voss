package Server;

import MockModel.Lobby;
import MockModel.RMI_ManagerInterface;
import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.Messages.MessagesToLobby.NewConnectionMessage;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class First_RMI_Manager implements RMI_ManagerInterface {
    private Lobby lobby;
    private int serverPort;
    private int connectionsNumber;
    public First_RMI_Manager(Lobby lobby, int serverPort) throws RemoteException, AlreadyBoundException {
        this.lobby = lobby;
        this.serverPort = serverPort;
        this.connectionsNumber = 0;
        UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.createRegistry(serverPort);
        registry.bind("Lobby", this);
        System.out.println("RMI interface ready to pair");
    }

    public void deliverToLobby(MessageToLobby msg){
        lobby.enqueueMessage(msg);
    }

    public void newHandler(String clientRegistry, String clientHost, int clientPort, ArrayList<String> games) throws AlreadyBoundException, NotBoundException, IOException {
        RMI_ClientHandler rmiClientHandler = new RMI_ClientHandler(clientPort, clientHost, clientRegistry, registryName(connectionsNumber), lobby, serverPort);
        rmiClientHandler.sendToClient(new WelcomeMessage(games, registryName(connectionsNumber)));
        connectionsNumber+=1;
    }

    private String registryName(int number){
        return String.valueOf(number)+"Server";
    }

}
