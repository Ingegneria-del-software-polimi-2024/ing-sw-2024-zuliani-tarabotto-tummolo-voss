package Server;

import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.ServerHandlerInterface;

import java.rmi.AlreadyBoundException;
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

    public void addNewPlayer(/*parameters*/) throws RemoteException{}

    //Come

    /**
     * notifies the client of the changes happened in the model
     * @param message the changes contained in the model
     * @throws RemoteException when an error occurs in the communication
     */
    public void notifyChanges(MessageFromServer message) throws RemoteException{client.notifyChanges(message);}

    /**
     * class constructor
     * @param port the port for the connection
     * @param name the name for the lookup in the registry
     * @throws RemoteException when an error occurs in the binding
     * @throws AlreadyBoundException when trying to bind two sides already bound
     */
    public RMI_ClientHandler(int port, String name) throws RemoteException, AlreadyBoundException {
        UnicastRemoteObject.exportObject(this, port);
        Registry registry = LocateRegistry.createRegistry(port);
        registry.bind(name, this);
    }
}
