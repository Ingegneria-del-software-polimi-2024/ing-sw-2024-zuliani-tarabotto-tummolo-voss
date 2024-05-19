package Server.Web.Game;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_RoomChoice;
import SharedWebInterfaces.Messages.MessagesFromLobby.AvailableGames;
import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.Messages.MessagesFromServer.EndGameMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.JoinGameMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.RequestAvailableGames;
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
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Lobby lobby;

    private final int MAX_RETRY = 3;

    @Override
    public void sendToServer(MessageFromClient message) throws RemoteException {api.sendToServer(message);}

    @Override
    public void notifyChanges(MessageFromServer message) throws RemoteException {snd(message);}


    /**
     * starts the listening loop for the gameplay
     * @throws IOException when an error in the communication occurs
     * @throws ClassNotFoundException when an error in the communication occurs
     */
    private void listenForCommands() throws IOException, ClassNotFoundException {
        //TODO change in a conditional while and NOT while(true)
        while (true){
            MessageFromClient msg = (MessageFromClient) in.readObject();
            api.sendToServer(msg);
        }
    }

    /**
     * safely closes the connection
     * @throws IOException when an error occurs, the connection must not be considered close in this case
     */
    private void closeConnection() throws IOException {
        out.close();
        in.close();
        clientSocket.close();
    }



    /**
     * class constructor
     * @param clientSocket the socket of the client
     * @param lobby the reference to the lobby-controller
     * @throws IOException when the streams couldn't be instantiated
     */
    public SOCKET_ClientHandler(Socket clientSocket, Lobby lobby) throws IOException {
        this.lobby = lobby;
        this.clientSocket = clientSocket;
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    /**
     * starts the listening loop
     */
    public void run(){
        try{
            snd(new WelcomeMessage(lobby.getGameNames()));
            System.out.println("sent welcomeMessage");
            NewConnectionMessage msg = null;

            boolean read = false;
            while(!read) {
                msg = (NewConnectionMessage) in.readObject();
                read = true;
            }
            msg.setHandler(this);
            deliverToLobby(msg);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
        try {

            MessageToLobby messageToLobby = null;
            do {
                messageToLobby = (MessageToLobby) in.readObject();
                deliverToLobby(messageToLobby);
            }while (! (messageToLobby instanceof JoinGameMessage));
        }catch (IOException | ClassNotFoundException e){
            throw new RuntimeException();
        }
        System.out.println("Started gameloop");
        //TODO insert the listening loop for game messages
        MessageFromClient msg = null;
        try {
            do{
                msg = (MessageFromClient) in.readObject();
                sendToServer(msg);
                System.out.println("NewMessage...\n");
            }while (!(msg instanceof EndGameMessage));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * sets the api deputed to the reception of incoming web messages
     * @param receiver the web API "come"
     * @throws RemoteException because, implementing a remote interface this must be callable also remotely
     */
    public void setReceiver(ServerAPI_COME receiver) throws RemoteException{
        this.api = receiver;
    }

    /**
     * Sends the message to the client
     * @param msg the message for the client
     * @throws RemoteException when the message is not correctly delivered
     */
    public void sendToClient(MessageFromServer msg) throws RemoteException {snd(msg);}

    /**
     * handles the messages for the lobby
     * @param msg the message from the client
     * @throws RemoteException when the message couldn't be delivered to the lobby
     */
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

}
