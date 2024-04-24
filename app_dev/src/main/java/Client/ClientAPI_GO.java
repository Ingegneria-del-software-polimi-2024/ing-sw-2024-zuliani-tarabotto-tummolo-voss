package Client;

import SharedWebInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.Messages.MessageFromClient;

import java.rmi.RemoteException;

public class ClientAPI_GO {
    private ServerHandlerInterface client;

    public void addNewPlayer(){}

    /**
     * notifies the server handler of the message the client wants to send
     * @param message the message going towards the server
     */
    public void sendToServer(MessageFromClient message){
        try {
            client.sendToServer(message);
        } catch (RemoteException e) {
            //TODO handle correctly the exception, this is where indeed it is most important to handle correctly the exc.
            throw new RuntimeException(e);
        }
    }
}
