package Client.Web;

import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import java.rmi.RemoteException;

public class ClientAPI_GO {
    private ServerHandlerInterface handler;

    public ClientAPI_GO(ServerHandlerInterface handler){
        this.handler = handler;
    }

    public void addNewPlayer(){}


    /**
     * notifies the server handler of the message the client wants to send
     * @param message the message going towards the server
     */
    public synchronized void sendToServer(MessageFromClient message){
        try {
            handler.sendToServer(message);
        } catch (RemoteException e) {
            //TODO handle correctly the exception, this is where indeed it is most important to handle correctly the exc.
            throw new RuntimeException(e);
        }
    }
    public void sendToLobby(MessageToLobby message){
        try {
            handler.sendToLobby(message);
        } catch (RemoteException e) {
            //TODO handle correctly the exception, this is where indeed it is most important to handle correctly the exc.
            throw new RuntimeException(e);
        }
    }




}
