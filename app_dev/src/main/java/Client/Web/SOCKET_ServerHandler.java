package Client.Web;

import Chat.MessagesFromServer.ChatHistoryMessage;
import Chat.MessagesFromServer.ChatUpdateMessage;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_RoomChoice;
import SharedWebInterfaces.Messages.MessagesFromServer.InterruptConnectionMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

public class SOCKET_ServerHandler implements ServerHandlerInterface, Runnable {

    private ClientAPI_COME api;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    /**
     * sends to the server the message from client
     * @param message message coming from client
     * @throws RemoteException when an error in the connection occurs
     */
    public void sendToServer(MessageFromClient message) throws RemoteException {
        try {
            out.writeObject(message);
            out.flush();
            out.reset();
        } catch (IOException e) {
            throw new RemoteException();
        }
    }

    /**
     * receives and enqueues the message coming from the server
     * @param message the message from the server containing the updates for the view
     * @throws RemoteException when an error in the network happens
     */
    public void notifyChanges(MessageFromServer message) throws RemoteException {
        if(message instanceof ChatHistoryMessage || message instanceof ChatUpdateMessage){
            api.enqueueChatMessage(message);
            return;
        }
        api.notifyChanges(message);
    }

    /**
     * forwards the incoming message to the client API incoming interface
     * @param msg incoming message
     */
    @Override
    public void receiveFromLobby(MessageFromServer msg) {
        api.notifyChanges(msg);
    }

    /**
     * sends the message to the lobby
     * @param msg message to be sent
     * @throws RemoteException if the message couldn't be delivered
     */
    @Override
    public void sendToLobby(MessageToLobby msg) throws RemoteException {
        try {
            out.writeObject(msg);
            out.flush();
            out.reset();
        } catch (IOException e) {
            throw new RemoteException();
        }
    }

    /**
     * a loop that keeps the socket in listening status
     * @throws IOException when an error in the communication occurs
     * @throws ClassNotFoundException when an error in the communication occurs
     */
    private void gameListeningLoop() throws IOException, ClassNotFoundException{
        MessageFromServer incomingMessage = null;
        do{
            incomingMessage = (MessageFromServer) in.readObject();
            notifyChanges(incomingMessage);
        }while(!(incomingMessage instanceof InterruptConnectionMessage));
    }

    /**
     * class constructor
     * @param add the server hostname
     * @param port the server port
     * @param come the interface for the reception of the messages
     * @throws StartConnectionFailedException if an error in the instantiation of the connection happens
     */
    public SOCKET_ServerHandler(String add, int port, ClientAPI_COME come) throws StartConnectionFailedException {
        api = come;
        try {
            //opening the connection
            socket = new Socket(add, port);
            out =  new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            throw new StartConnectionFailedException();
        }
    }

    /**
     * a method run by a single thread that keeps listening to the client handler
     */
    public void run() {
        try {
            MessageFromServer msg = null;
            do{
                msg = (MessageFromServer) in.readObject();
                receiveFromLobby(msg);
            }while (!(msg instanceof ACK_RoomChoice));
            gameListeningLoop();
            out.close();
            in.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
