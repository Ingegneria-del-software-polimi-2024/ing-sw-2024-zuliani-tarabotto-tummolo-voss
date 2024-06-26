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

public class  SOCKET_ClientHandler implements ClientHandlerInterface, Runnable{
    private ServerAPI_COME api;
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Lobby lobby;

    private final int MAX_RETRY = 3;

    /**
     * forwards the message to the interface managing incoming messages
     * @param message the incoming message
     * @throws RemoteException when a communication error occurs
     */
    @Override
    public void sendToServer(MessageFromClient message) throws RemoteException {api.sendToServer(message);}

    /**
     * sends a message to the client
     * @param message the message to be sent
     * @throws RemoteException when a communication error occurs
     */
    @Override
    public void notifyChanges(MessageFromServer message) throws RemoteException {
        snd(message);
    }


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

//            boolean read = false;
//            MessageToLobby incoming;
//            do{
//                incoming = (MessageToLobby) in.readObject();
//                if(incoming instanceof NewConnectionMessage) {
//                    ((NewConnectionMessage) incoming).setHandler(this);
////                    read = true;
//                }
//                deliverToLobby(incoming);
//            }while(!(incoming instanceof JoinGameMessage));

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
//            System.err.println("Connection lost: " + e.getMessage());

            DisconnectionMessage disconnectionMessage = new DisconnectionMessage();
            if(api == null)
                return;
            api.sendToServer(disconnectionMessage);
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
