package Server;

import SharedWebInterfaces.Messages.MessageFromClient;
import SharedWebInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessageFromServer;
import SharedWebInterfaces.ServerHandlerInterface;

import java.rmi.RemoteException;

public class RMI_ClientHandler implements ClientHandlerInterface {
    private ServerAPI_COME api;
    private ServerHandlerInterface client;

    //Go
    public void sendToServer(MessageFromClient message) throws RemoteException{api.sendToServer(message);}

    public void addNewPlayer(/*parameters*/) throws RemoteException{}

    //Come
    public void notifyChanges(MessageFromServer message) throws RemoteException{client.notifyChanges(message);}
}
