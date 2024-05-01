package Server;

import MockModel.Lobby;
import SharedWebInterfaces.Messages.MessagesFromClient.AddNewPlayerMessage;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromLobby.MessageFromLobby;
import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.ServerHandlerInterface;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMI_ClientHandler implements ClientHandlerInterface {
    private ServerAPI_COME api;
    private ServerHandlerInterface client;
    private Lobby lobby;

    //Go

    /**
     * receives and enqueues the message coming from the client
     * @param message the message coming from the client
     * @throws RemoteException when the message is not correctly delivered
     */
    public void sendToServer(MessageFromClient message) throws RemoteException{api.sendToServer(message);}

    public void addNewPlayer(String nickname, String lookupTableName, int clientPort, String clientHost) throws RemoteException{
        //searching in the register for the required connection
        Registry registry = LocateRegistry.getRegistry(clientHost, clientPort);
        try {
            client = (ServerHandlerInterface) registry.lookup(lookupTableName);
        } catch (NotBoundException e) {
            throw new RemoteException();
        }
        //memorizing in the hashmap of ServerAPI_COME
        api.addNewPlayer(nickname, this);
        //signaling controller to memorize in the hashmap of ServerAPI_GO
        api.sendToServer(new AddNewPlayerMessage(nickname, this));
    }

    //Come

    /**
     * notifies the client of the changes happened in the model
     * @param message the changes contained in the model
     * @throws RemoteException when an error occurs in the communication
     */
    public void notifyChanges(MessageFromServer message) throws RemoteException{client.notifyChanges(message);}

    @Override
    public void setReceiver(ServerAPI_COME receiver) throws RemoteException {
        //todo
    }

    @Override
    public void sendToClient(MessageFromLobby msg) throws IOException {}

    /**
     * class constructor
     * @param clientPort the port for the connection
     * @throws RemoteException when an error occurs in the binding
     * @throws AlreadyBoundException when trying to bind two sides already bound
     */
    public RMI_ClientHandler(int clientPort, String ClientHost, String clientRegistry, String clientName, Lobby lobby, int serverPort) throws RemoteException, NotBoundException, AlreadyBoundException {
        this.lobby = lobby;
        UnicastRemoteObject.exportObject(this, 0);
        Registry serverRegistry = LocateRegistry.createRegistry(serverPort);
        String registryName = clientName+"Server";
        serverRegistry.bind("", this);
        Registry registry = LocateRegistry.getRegistry(clientRegistry, clientPort);
        client = (ServerHandlerInterface) registry.lookup(clientName);
        System.out.println("Client is bound with lobby");
    }

    public void setApi(ServerAPI_COME come){
        api = come;
    }
}
