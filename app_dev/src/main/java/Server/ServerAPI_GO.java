package Server;

import SharedWebInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessageFromServer;

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
}
