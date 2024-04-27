package Client;

import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class SOCKET_ServerHandler implements ClientHandlerInterface {

    private ClientAPI_COME api;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    @Override
    public void sendToServer(MessageFromClient message) throws RemoteException {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            //TODO handle correctly the exception
            throw new RemoteException();
        }
    }

    @Override
    public void addNewPlayer() throws RemoteException {}

    @Override
    public void notifyChanges(MessageFromServer message) throws RemoteException {api.notifyChanges(message);}

    public SOCKET_ServerHandler(String add, int port) {
        try {
            //opnening the connection
            socket = new Socket(add, port);
            //if the connection is established

            //...
            //let the client cook
            //...

            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            //TODO handle correctly the exception
            throw new RuntimeException(e);
        }
    }
}
