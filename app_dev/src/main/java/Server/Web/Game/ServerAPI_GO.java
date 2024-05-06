package Server.Web.Game;

import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;

import java.rmi.RemoteException;
import java.util.HashMap;

public class ServerAPI_GO {
    private HashMap<String, ClientHandlerInterface> players;

    public void notifyChanges(MessageFromServer message, String player){
        try {
            players.get(player).notifyChanges(message);
        } catch (RemoteException e) {
            //TODO handle correctly the exception
            throw new RuntimeException(e);
        }
    }

    /**
     * sends the same message to each client
     * @param message
     */
    public void broadcastNotifyChanges(MessageFromServer message){
        try {
            for(String p : players.keySet()){
                players.get(p).notifyChanges(message);
            }
        } catch (RemoteException e) {
            //TODO handle correctly the exception
            throw new RuntimeException(e);
        }
    }

    public void setHandler(String name, ClientHandlerInterface handler){
        players.put(name, handler);
    }

    public ServerAPI_GO() {
        players = new HashMap<String, ClientHandlerInterface>();
    }
}
