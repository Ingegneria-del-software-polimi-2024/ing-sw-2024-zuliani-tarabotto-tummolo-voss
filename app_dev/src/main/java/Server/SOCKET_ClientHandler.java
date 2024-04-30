package Server;

import MockModel.Lobby;
import MockModel.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromClient.NewConnectionMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.ACK_RoomChoice;
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
            //TODO out.flush(); va messo o no????
            out.reset();
        } catch (IOException e) {
            throw new RemoteException();
        }
    }

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
            //TODO handle better the exception
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


    public SOCKET_ClientHandler(Socket clientSocket, Lobby lobby) {
        this.lobby = lobby;
        this.clientSocket = clientSocket;
        try{
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            out.writeObject(new WelcomeMessage(lobby.getGameNames()));
            out.flush();
            out.reset();
            NewConnectionMessage msg = (NewConnectionMessage) in.readObject();
            if(msg instanceof NewConnectionMessage){//at the moment useless but useful when waiting for a common message
                msg.execute(lobby, this);
            }else
                throw new RuntimeException();

            String playerName = lobby.getPlayerName(this);

            if(playerName == null)
                throw new RuntimeException();

            String gameName = lobby.isInRoom(playerName);

            if (gameName == null)
                throw new RuntimeException();

            out.writeObject(new ACK_RoomChoice(playerName,gameName));
            out.flush();
            out.reset();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setReceiver(ServerAPI_COME receiver) throws RemoteException{
        this.api = receiver;
    }
}
