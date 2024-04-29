package Server;

import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;

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
    public void addNewPlayer(String nickname, String lookupTableName, int clientPort, String clientHost) throws RemoteException {
        //TODO implement!!!!
    }

    @Override
    public void notifyChanges(MessageFromServer message) throws RemoteException {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException();
        }
    }

    public void listenForNewConnections(int port){
        try{
            //create new socket
            serverSocket = new ServerSocket(port);
            //if started
            //wait for a client
            clientSocket = serverSocket.accept();
            //Listens for a connection to be made to this socket and accepts it.
            // The method blocks until a connection is made.

            Thread t = new Thread(()->(new SOCKET_ClientHandler()).listenForNewConnections(port));
            t.start();

            //when a client is accepted
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            //to read an object: obj = in.readObject();

            //...
            //let the socket cook
            //...
            listenForCommands();
            closeConnection();

        } catch (IOException e) {
            //TODO handle better the exception
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void listenForCommands() throws IOException, ClassNotFoundException {
        //TODO change in a conditional while and NOT while(true)
        while (true){
            MessageFromClient msg = (MessageFromClient) in.readObject();
            api.sendToServer(msg);
        }
    }
    private void closeConnection() throws IOException {
        out.close();
        in.close();
        clientSocket.close();
    }
}
