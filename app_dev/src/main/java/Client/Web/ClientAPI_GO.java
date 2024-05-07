package Client.Web;

import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import java.rmi.RemoteException;

public class ClientAPI_GO {
    private ServerHandlerInterface handler;

    public void addNewPlayer(){}


    /**
     * notifies the server handler of the message the client wants to send
     * @param message the message going towards the server
     */
    public void sendToServer(MessageFromClient message){
        try {
            handler.sendToServer(message);
        } catch (RemoteException e) {
            //TODO handle correctly the exception, this is where indeed it is most important to handle correctly the exc.
            throw new RuntimeException(e);
        }
    }

    public void newRMI_Connection(String host, int port, ClientAPI_COME come) throws StartConnectionFailedException {
        handler = new RMI_ServerHandler(host, port, come);
    }
    public void newSocketConnection(String host, int port, ClientAPI_COME come) throws StartConnectionFailedException {
        handler = new SOCKET_ServerHandler(host, port, come);
        SOCKET_ServerHandler listener = (SOCKET_ServerHandler) handler;
        Thread listenForMessages = new Thread(listener);
        listenForMessages.start();
    }

}
