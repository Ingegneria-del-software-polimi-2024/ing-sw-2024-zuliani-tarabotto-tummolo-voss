package Server.Web.Game;

import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Server api go, an interface to handle the sending of messages to the clients.
 */
public class ServerAPI_GO {
    /**
     * The players and their handlers.
     */
    private ConcurrentHashMap<String, ClientHandlerInterface> players;

    /**
     * The model changes are forwarded to the player
     *
     * @param message the message containing the changes
     * @param player  the player's nickname
     * @throws MsgNotDeliveredException when an error in delivering the message occurs
     */
    public void notifyChanges(MessageFromServer message, String player) throws MsgNotDeliveredException {
        try {
            ClientHandlerInterface c = players.get(player);
            if(c != null)
                c.notifyChanges(message);
        } catch (RemoteException e) {
            throw new MsgNotDeliveredException(message);
        }
    }

    /**
     * Sends the same message to each client
     *
     * @param message the message containing the changes
     * @throws MsgNotDeliveredException the msg not delivered exception
     */
    public void broadcastNotifyChanges(MessageFromServer message) throws MsgNotDeliveredException {
        try {
            for(String p : players.keySet()) {
                System.out.println("broadcasting " + message.getClass() + "to " + p);
                ClientHandlerInterface c = players.get(p);
                if(c != null)
                    c.notifyChanges(message);
            }
        } catch (RemoteException e) {
            //throw new RuntimeException(e);
//            throw new MsgNotDeliveredException(message);
            System.out.println("RMI DISCONNECTION DETECTED");
        }
    }

    /**
     * Sets a handler for the specified player
     *
     * @param name    the player's nickname
     * @param handler the player's personal handler
     */
    public void setHandler(String name, ClientHandlerInterface handler){
        players.put(name, handler);
    }

    /**
     * Class constructor
     */
    public ServerAPI_GO() {
        players = new ConcurrentHashMap<>();
    }

    /**
     * Disconnect player.
     *
     * @param nickName the nickname
     */
    public void disconnectPlayer(String nickName){
        players.remove(nickName);
    }
}
