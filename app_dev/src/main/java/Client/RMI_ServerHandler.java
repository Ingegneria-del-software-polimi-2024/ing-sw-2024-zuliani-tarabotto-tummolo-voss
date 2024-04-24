package Client;

import SharedWebInterfaces.Messages.MessageFromServer;
import SharedWebInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.Messages.MessageFromClient;
import SharedWebInterfaces.ClientHandlerInterface;

import java.rmi.RemoteException;

public class RMI_ServerHandler implements ServerHandlerInterface {

    private ClientAPI_COME api;
    private ClientHandlerInterface server;
    @Override
    public void sendToServer(MessageFromClient message) throws RemoteException{server.sendToServer(message);}


    @Override
    public void addNewPlayer() throws RemoteException{}

    @Override
    public void notifyChanges(MessageFromServer message) throws RemoteException{api.notifyChanges(message);}
}
