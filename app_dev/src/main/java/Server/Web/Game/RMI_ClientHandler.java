package Server.Web.Game;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.Messages.MessagesToLobby.NewConnectionMessage;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMI_ClientHandler implements ClientHandlerInterface {
    private ServerAPI_COME api;
    private ServerHandlerInterface client;
    private Lobby lobby;
    private final int MAX_RETRY = 3;

    //Go

    /**
     * receives and enqueues the message coming from the client
     * @param message the message coming from the client
     * @throws RemoteException when the message is not correctly delivered
     */
    public void sendToServer(MessageFromClient message) throws RemoteException{
        api.sendToServer(message);}

    /**
     * notifies the client of the changes happened in the model
     * @param message the changes contained in the model
     * @throws RemoteException when an error occurs in the communication
     */
    public void notifyChanges(MessageFromServer message) throws RemoteException{client.notifyChanges(message);}

    /**
     * sets the api deputed to the reception of incoming web messages
     * @param receiver the web API "come"
     * @throws RemoteException because, implementing a remote interface this must be callable also remotely
     */
    @Override
    public void setReceiver(ServerAPI_COME receiver) throws RemoteException {
        api = receiver;
    }

    /**
     * Sends the message to the client
     * @param msg the message for the client
     * @throws RemoteException when the message is not correctly delivered
     */
    @Override
    public void sendToClient(MessageFromServer msg) throws RemoteException {snd(msg);}

    /**
     * class creator
     * @param clientRemoteInterface the remote interface of the client to which the server must comunicate
     * @param lobby the lobby
     * @param serverPort the port of the server, used to export the new personal handler
     * @throws RemoteException when the handler could not be correctly exported
     */
    public RMI_ClientHandler(ServerHandlerInterface clientRemoteInterface, Lobby lobby, int serverPort) throws RemoteException{
            this.lobby = lobby;
            UnicastRemoteObject.exportObject(this, 0);

            client = clientRemoteInterface;

            System.out.println("Client is bound with its handler");
    }

    /**
     * handles the messages for the lobby
     * @param msg the message from the client
     * @throws RemoteException when the message couldn't be delivered to the lobby
     */
    public void deliverToLobby(MessageToLobby msg) throws RemoteException{
        if(msg instanceof NewConnectionMessage)
            ((NewConnectionMessage) msg).setHandler(this);
        lobby.enqueueMessage(msg);
    }

    /**
     * This method tries to send the message MAX_RETRY times before signaling the exception to the caller
     * @param msg
     */
    private void snd(MessageFromServer msg) throws RemoteException{
        int count = 0;
        while (count < MAX_RETRY+1){
            try{
                client.receiveFromLobby(msg);
                return;
            } catch (RemoteException e) {
                count++;
                if (count == MAX_RETRY+1)
                    throw new RemoteException();
            }
        }
    }
}
