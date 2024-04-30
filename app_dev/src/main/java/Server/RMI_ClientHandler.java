package Server;

import SharedWebInterfaces.Messages.MessagesFromClient.AddNewPlayerMessage;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.ServerHandlerInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMI_ClientHandler implements ClientHandlerInterface {
    private ServerAPI_COME api;
    private ServerHandlerInterface client;

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

    /**
     * class constructor
     * @param port the port for the connection
     * @param name the name for the lookup in the registry
     * @param api_come the api for the incoming messages on the server
     * @throws RemoteException when an error occurs in the binding
     * @throws AlreadyBoundException when trying to bind two sides already bound
     */
    public RMI_ClientHandler(int port, String name, ServerAPI_COME api_come) throws RemoteException, AlreadyBoundException {
        //writing the current class on the register
        UnicastRemoteObject.exportObject(this, port);
        Registry registry = LocateRegistry.createRegistry(port);
        registry.bind(name, this);
        api = api_come;
    }
}
