package Server;

import SharedWebInterfaces.Messages.MessageFromClient;
import SharedWebInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessageFromServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

public class SOCKET_ClientHandler implements ClientHandlerInterface {
    private ServerAPI_COME api;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String clientID;

    @Override
    public void sendToServer(MessageFromClient message) throws RemoteException {api.sendToServer(message);}

    @Override
    public void addNewPlayer() throws RemoteException {}

    @Override
    public void notifyChanges(MessageFromServer message) throws RemoteException {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException();
        }
    }

    public SOCKET_ClientHandler(ServerSocket server){
        try{
            //create new socket
            serverSocket = server;
            //if started
            //wait for a client
            clientSocket = serverSocket.accept();
            //Listens for a connection to be made to this socket and accepts it.
            // The method blocks until a connection is made.

            //TODO here a new clientHandler to wait for new possible connections should be instantiated

            //when a client is accepted
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            //to read an object: obj = in.readObject();

            //...
            //let the socket cook
            //...

            out.close();
            in.close();
            clientSocket.close();

        } catch (IOException e) {
            //TODO handle better the exception
            throw new RuntimeException(e);
        }
    }

}
