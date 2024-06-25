package Client.Web;

import SharedWebInterfaces.Messages.MessagesFromServer.Errors.KickOutOfGameMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.Errors.ReturnToStartMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import java.rmi.RemoteException;

/**
 * The type Client api go, an interface to handle the sending of messages to the server.
 */
public class ClientAPI_GO {
    /**
     * The server handler
     */
    private ServerHandlerInterface handler;

    /**
     * Instantiates a new Client api go.
     *
     * @param handler the server handler
     */
    public ClientAPI_GO(ServerHandlerInterface handler){
        this.handler = handler;
    }


    /**
     * Sends a message to server.
     *
     * @param message the message
     */
    public synchronized void sendToServer(MessageFromClient message){
        try {
            handler.sendToServer(message);
        } catch (RemoteException e) {
            //TODO handle correctly the exception, this is where indeed it is most important to handle correctly the exc.
            try {
                handler.notifyChanges(new ReturnToStartMessage());
            } catch (RemoteException ex) {
                return;
            }
        }
    }

    /**
     * Forwards to the lobby the message
     *
     * @param message the message to pass to the lobby
     */
    public void sendToLobby(MessageToLobby message){
        try {
            handler.sendToLobby(message);
        } catch (RemoteException e) {
            //TODO handle correctly the exception, this is where indeed it is most important to handle correctly the exc.
            try {
                handler.notifyChanges(new ReturnToStartMessage());
            } catch (RemoteException ex) {
                return;
            }
        }
    }

}
