package Client.Web;

import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.Messages.MessagesFromServer.InterruptConnectionMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

//    public void addNewPlayer() throws RemoteException {}

    /**
     * receives and enqueues the message coming from the server
     * @param message the message from the server containing the updates for the view
     * @throws RemoteException
     */
    public void notifyChanges(MessageFromServer message) throws RemoteException {api.notifyChanges(message);}

    @Override
    public void receiveFromLobby(MessageFromServer msg) {
        api.notifyChanges(msg);
    }

    /**
     * a loop that keeps the socket in listening status
     * @throws IOException when an error in the communication occurs
     * @throws ClassNotFoundException when an error in the communication occurs
     */
    private void gameListeningLoop() throws IOException, ClassNotFoundException{
        MessageFromServer incomingMessage;
        do{
            incomingMessage = (MessageFromServer) in.readObject();
            notifyChanges(incomingMessage);
        }while(incomingMessage instanceof InterruptConnectionMessage);
    }
    public SOCKET_ServerHandler(String add, int port, ClientAPI_COME come) throws StartConnectionFailedException {
        api = come;
        try {
            //opnening the connection
            socket = new Socket(add, port);
            out =  new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            throw new StartConnectionFailedException();
        }
    }

    //TODO just ALWAYS remember to call this
    public void attachAPI(ClientAPI_COME api){
        this.api = api;
    }
    /**
     * a method run by a single thread that keeps listening to the client handler
     */
    public void run() {
        try {
            gameListeningLoop();
            out.close();
            in.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
