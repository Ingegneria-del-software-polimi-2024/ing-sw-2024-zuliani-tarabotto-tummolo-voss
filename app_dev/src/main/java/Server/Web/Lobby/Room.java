package Server.Web.Lobby;

import Server.ModelController;
import Server.Web.Game.ServerAPI_COME;
import Server.Web.Game.ServerAPI_GO;
import Server.Web.Lobby.LobbyExceptions.CantJoinRoomExcept;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import model.GameState.GameState;
import model.GameState.TurnState;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    private String name;
    private int expectedPlayers;
    private ArrayList<String> players;
    private GameState game;//Maybe controller??
    private ModelController modelController;
    private ServerAPI_COME receive;
    private ServerAPI_GO send;
    private boolean full;
    private HashMap<String, ClientHandlerInterface> playersInterfaces;


    /**
     * allows a player in the room
     * @param name the player's nickname
     * @param handler the player's handler
     * @throws CantJoinRoomExcept if the room is full
     */
    public void joinRoom(String name, ClientHandlerInterface handler) throws CantJoinRoomExcept {
        if(expectedPlayers == players.size())
            throw new CantJoinRoomExcept(false);
        players.add(name);
        send.setHandler(name, handler);
        playersInterfaces.put(name, handler);
    }

    /**
     * verifies if a match in the room can start
     */
    public void verifyStart(){
        if(expectedPlayers == players.size())
            startGame();
    }

    /**
     * class constructor
     * @param name the name of the room
     * @param expectedPlayers the number of player to wait in order to start the game
     */
    public Room(String name, int expectedPlayers){
        this.playersInterfaces = new HashMap<>();
        this.expectedPlayers = expectedPlayers;
        this.name = name;
        players = new ArrayList<String>();
        send = new ServerAPI_GO();
        full = false;
    }

    /**
     *
     * @return the players in the room
     */
    public ArrayList<String> getPlayers() {
        return (ArrayList<String>) players.clone();
    }

    /**
     *
     * @return the room's name
     */
    public String getName() {
        return name;
    }

    /**
     * starts a thread starting the controller execution...
     */
    private void startGame(){
        //starts a thread starting the controller execution...
        //game = new GameState(players, "3");
        //game.setTurnState(TurnState.GAME_INITIALIZATION);
        modelController = new ModelController(players, name, send);
        receive = new ServerAPI_COME(modelController);
        try {
            for(String p : playersInterfaces.keySet()){
                playersInterfaces.get(p).setReceiver(receive);
            }
        }catch (RemoteException e){
            throw new RuntimeException("Can't join the room due to a comunication error");
        }
        Thread thread1 = new Thread(() -> receive.loop());
        thread1.start();
        modelController.initializeGameState();
        System.out.println("game can now start");
    }

    /**
     * verifies if a player is contained in a room
     * @param player the player's nickname
     * @return a boolean
     */
    public boolean contains(String player){
        return players.contains(player);
    }

    /**
     *
     * @return true if the room is full else false
     */
    public boolean isFull(){return full;}
}
