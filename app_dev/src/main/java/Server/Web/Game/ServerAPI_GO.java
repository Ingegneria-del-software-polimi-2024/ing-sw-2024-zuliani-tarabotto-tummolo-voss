package Server.Web.Game;

import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;

import java.rmi.RemoteException;
import java.util.HashMap;

public class ServerAPI_GO {
    private HashMap<String, ClientHandlerInterface> players;

    /**
     * the model changes are forwarded to the player
     * @param message the message containing the changes
     * @param player the player's nickname
     * @throws MsgNotDeliveredException when an error in delivering the message occurs
     */
    public void notifyChanges(MessageFromServer message, String player) throws MsgNotDeliveredException {
        try {
            players.get(player).notifyChanges(message);
        } catch (RemoteException e) {
            throw new MsgNotDeliveredException(message);
        }
    }

    /**
     * sends the same message to each client
     * @param message the message containing the changes
     */
    public void broadcastNotifyChanges(MessageFromServer message) throws MsgNotDeliveredException {
        try {
            for(String p : players.keySet()){
                System.out.println("broadcasting "+message.getClass()+ "to "+p);
                players.get(p).notifyChanges(message);
            }
        } catch (RemoteException e) {
            //throw new RuntimeException(e);
//            throw new MsgNotDeliveredException(message);
            System.out.println("RMI DISCONNECTION DETECTED");
        }
    }

    /**
     * sets a handler for the specified player
     * @param name the player's nickname
     * @param handler the player's personal handler
     */
    public void setHandler(String name, ClientHandlerInterface handler){
        players.put(name, handler);
    }

    /**
     * class constructor
     */
    public ServerAPI_GO() {
        players = new HashMap<>();
    }
}
