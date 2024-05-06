package MockModel;

import Server.ServerAPI_COME;
import Server.ServerAPI_GO;
import SharedWebInterfaces.ClientHandlerInterface;
import model.GameState.GameState;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Room {
    private String name;
    private int expectedPlayers;
    private ArrayList<String> players;
    private GameState game;//Maybe controller??
    private ServerAPI_COME receive;
    private ServerAPI_GO send;
    private boolean full;


    public void joinRoom(String name, ClientHandlerInterface handler) throws RemoteException {
        if(expectedPlayers == players.size())
            throw new RuntimeException("Too many players, can't join the room");//TODO handle or change the except
        players.add(name);
        handler.setReceiver(receive);
        send.setHandler(name, handler);
        if(expectedPlayers == players.size())
            startGame();
    }

    public Room(String name, int expectedPlayers){
        this.expectedPlayers = expectedPlayers;
        this.name = name;
        players = new ArrayList<String>();
        receive = new ServerAPI_COME();
        send = new ServerAPI_GO();
        full = false;
    }

    public ArrayList<String> getPlayers() {
        return (ArrayList<String>) players.clone();
    }

    public String getName() {
        return name;
    }
    private void startGame(){
        //starts a thread starting the controller execution...
    }
    public boolean contains(String player){
        return players.contains(player);
    }
    public boolean isFull(){return full;}
}
