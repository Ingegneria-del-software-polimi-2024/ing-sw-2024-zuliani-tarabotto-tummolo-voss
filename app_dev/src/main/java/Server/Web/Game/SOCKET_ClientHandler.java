package Server.Web.Game;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.Message;
import SharedWebInterfaces.Messages.MessagesFromLobby.WelcomeMessage;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.Messages.MessagesToLobby.JoinGameMessage;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.Messages.MessagesToLobby.NewConnectionMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.Messages.MessagesFromClient.toModelController.DisconnectionMessage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * The type Socket client handler.
 * An interface mantaining the Socket connection with the client.
 */
public class  SOCKET_ClientHandler implements ClientHandlerInterface, Runnable{
    /**
     * The interface managing incoming messages
     */
    private ServerAPI_COME api;
    /**
     * The client socket
     */
    private Socket clientSocket;
    /**
     * The socket input stream
     */
    private ObjectInputStream in;
    /**
     * The socket output stream
     */
    private ObjectOutputStream out;
    /**
     * The lobby interface
     */
    private Lobby lobby;
    /**
     * The maximum number of retries for sending a message
     */
    private final int MAX_RETRY = 3;

    /**
     * Forwards the message to the interface managing incoming messages
     * @param message the incoming message
     * @throws RemoteException when a communication error occurs
     */
    @Override
    public void sendToServer(MessageFromClient message) throws RemoteException {api.sendToServer(message);}

    /**
     * Sends a message to the client
     * @param message the message to be sent
     * @throws RemoteException when a communication error occurs
     */
    @Override
    public void notifyChanges(MessageFromServer message) throws RemoteException {
        snd(message);
    }


    /**
     * Starts the listening loop for the gameplay
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
     * Safely closes the connection
     * @throws IOException when an error occurs, the connection must not be considered close in this case
     */
    private void closeConnection() throws IOException {
        out.close();
        in.close();
        clientSocket.close();
    }


    /**
     * Class constructor
     *
     * @param clientSocket the socket of the client
     * @param lobby        the reference to the lobby-controller
     * @throws IOException when the streams couldn't be instantiated
     */
    public SOCKET_ClientHandler(Socket clientSocket, Lobby lobby) throws IOException {
        this.lobby = lobby;
        this.clientSocket = clientSocket;
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    /**
     * Starts the listening loop
     */
    public void run(){
        try{
            snd(new WelcomeMessage(lobby.getGameNames()));
            System.out.println("sent welcomeMessage");
        } catch (IOException e) {
            throw new RuntimeException();
        }
        try {

            Message message = null;
            do{
                message = (Message) in.readObject();
                //TODO replace if else with overloading
                if(message instanceof MessageToLobby) {
                    if(message instanceof NewConnectionMessage)
                        ((NewConnectionMessage) message).setHandler(this);
                    deliverToLobby((MessageToLobby) message);
                }else if(message instanceof MessageFromClient)
                    sendToServer((MessageFromClient)message);
            }while(true);
        }catch (IOException | ClassNotFoundException e){
            DisconnectionMessage disconnectionMessage = new DisconnectionMessage();
            if(api == null)
                return;
            api.sendToServer(disconnectionMessage);
        }
    }

    /**
     * Sets the api deputed to the reception of incoming web messages
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
     * Handles the messages for the lobby
     * @param msg the message from the client
     * @throws RemoteException when the message couldn't be delivered to the lobby
     */
    @Override
    public void deliverToLobby(MessageToLobby msg) throws RemoteException {
        lobby.enqueueMessage(msg);
    }

    /**
     * This method tries to send the message MAX_RETRY times before signaling the exception to the caller
     * @param msg the message to send
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
