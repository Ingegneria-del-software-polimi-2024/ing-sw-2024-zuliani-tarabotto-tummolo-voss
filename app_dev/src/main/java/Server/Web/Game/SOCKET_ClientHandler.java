package Server.Web.Game;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.Messages.MessagesToLobby.NewConnectionMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

public class SOCKET_ClientHandler implements ClientHandlerInterface, Runnable{
    private ServerAPI_COME api;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String clientID;

    private Lobby lobby; //todo interface with ServerAPI_COME

    private final int MAX_RETRY = 3;

    @Override
    public void sendToServer(MessageFromClient message) throws RemoteException {api.sendToServer(message);}

    @Override
    public void notifyChanges(MessageFromServer message) throws RemoteException {snd(message);}

    /*public void listenForNewConnections(int port){
        try{
            //create new socket
            serverSocket = new ServerSocket(port);
            //if started
            //wait for a client
            clientSocket = serverSocket.accept();
            //Listens for a connection to be made to this socket and accepts it.
            // The method blocks until a connection is made.
            SOCKET_ClientHandler x = new SOCKET_ClientHandler();
            Thread t = new Thread(()->x.listenForNewConnections(port));
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
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }*/

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


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public SOCKET_ClientHandler(Socket clientSocket, Lobby lobby) throws IOException {
        this.lobby = lobby;
        this.clientSocket = clientSocket;
        in = new ObjectInputStream(clientSocket.getInputStream());
        out = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    public void run(){
        try{
            snd(new WelcomeMessage(lobby.getGameNames()));
            MessageToLobby msg = null;

            do {
                msg = (MessageToLobby) in.readObject();
            }while (! (msg instanceof NewConnectionMessage));

            ((NewConnectionMessage) msg).setHandler(this);
            deliverToLobby(msg);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException();
        }

        //TODO insert the listening loop for game messages
    }

    public void setReceiver(ServerAPI_COME receiver) throws RemoteException{
        this.api = receiver;
    }

    public void sendToClient(MessageFromServer msg) throws RemoteException {snd(msg);}

    @Override
    public void deliverToLobby(MessageToLobby msg) throws RemoteException {
        lobby.enqueueMessage(msg);
    }

    /**
     * This method tries to send the message MAX_RETRY times before signaling the exception to the caller
     * @param msg
     */
    private void snd(MessageFromServer msg) throws RemoteException {
        int retryCount = 0;
        while (retryCount < MAX_RETRY) {
            try {
                out.writeObject(msg);
                out.flush();
                out.reset();
                return;
            } catch (IOException e) {
                retryCount++;
            }
        }
        throw new RemoteException();
    }

//    @Override
//    public void addNewPlayer(String nickname, String lookupTableName, int clientPort, String clientHost) throws RemoteException {
//    }

}
