package Server.Web.Lobby;

import Server.ModelController;
import Server.Web.Game.ServerAPI_COME;
import Server.Web.Game.ServerAPI_GO;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import model.GameState.GameState;
import model.GameState.TurnState;
import model.Model;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Room {
    private String name;
    private int expectedPlayers;
    private ArrayList<String> players;
    private GameState game;//Maybe controller??
    private ModelController modelController;
    private ServerAPI_COME receive;
    private ServerAPI_GO send;
    private boolean full;


    public void joinRoom(String name, ClientHandlerInterface handler){
        if(expectedPlayers == players.size())
            throw new RuntimeException("Too many players, can't join the room");//TODO handle or change the except
        players.add(name);
        /*try {
            handler.setReceiver(receive);
        }catch (RemoteException e){
            throw new RuntimeException("Can't join the room due to a comunication error");
        }*/
        send.setHandler(name, handler);
        if(expectedPlayers == players.size())
            startGame(handler);
    }

    public Room(String name, int expectedPlayers){
        this.expectedPlayers = expectedPlayers;
        this.name = name;
        players = new ArrayList<String>();
        send = new ServerAPI_GO();
        full = false;
    }

    public ArrayList<String> getPlayers() {
        return (ArrayList<String>) players.clone();
    }

    public String getName() {
        return name;
    }
    private void startGame(ClientHandlerInterface handler){
        //starts a thread starting the controller execution...
        //game = new GameState(players, "3");
        //game.setTurnState(TurnState.GAME_INITIALIZATION);
        modelController = new ModelController(players, name, send);
        receive = new ServerAPI_COME(modelController);
        try {
            handler.setReceiver(receive);
        }catch (RemoteException e){
            throw new RuntimeException("Can't join the room due to a comunication error");
        }
        Thread thread1 = new Thread(() -> receive.loop());
        thread1.start();
        //Thread thread2 = new Thread(() -> modelController.initializeGameState());
        modelController.initializeGameState();
        //thread2.start();
        System.out.println("game can now start");
    }
    public boolean contains(String player){
        return players.contains(player);
    }
    public boolean isFull(){return full;}
}
