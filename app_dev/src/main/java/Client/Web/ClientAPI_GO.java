package Client.Web;

import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;

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

    public ClientAPI_GO(ServerHandlerInterface client) {
        this.handler = client;
    }
}
