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
    private int connectionsNumber;
    public First_RMI_Manager(Lobby lobby, int serverPort) throws RemoteException{
        try {
            this.lobby = lobby;
            this.serverPort = serverPort;
            this.connectionsNumber = 0;
            UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.createRegistry(serverPort);
            registry.bind("Lobby", this);
            System.out.println("RMI interface ready to pair");
        }catch (AlreadyBoundException e){
            throw new RemoteException();
        }
    }

    public void deliverToLobby(MessageToLobby msg){
        lobby.enqueueMessage(msg);
    }

    public void newHandler(ServerHandlerInterface clientRemote, ArrayList<String> games) throws RemoteException {
        RMI_ClientHandler rmiClientHandler = new RMI_ClientHandler(clientRemote, lobby, serverPort);
        rmiClientHandler.sendToClient(new WelcomeMessage(games, rmiClientHandler));
        connectionsNumber += 1;
    }

    private String registryName(int number){
        return String.valueOf(number)+"Server";
    }

}
