package Client.Web;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMI_ServerHandler implements ServerHandlerInterface {

    private ClientAPI_COME api;
    private ClientHandlerInterface server;


    public void sendToServer(MessageFromClient message) throws RemoteException{server.sendToServer(message);}

    @Override
    public void addNewPlayer() throws RemoteException{}

    @Override
    public void notifyChanges(MessageFromServer message) throws RemoteException{api.notifyChanges(message);}

    @Override
    public void receiveFromLobby(MessageFromServer msg) {
        //todo implementtt
    }

}
