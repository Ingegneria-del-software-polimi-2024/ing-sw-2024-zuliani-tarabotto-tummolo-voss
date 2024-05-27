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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Room {

    private ConcurrentHashMap<String, Long> lastSeen;

    private Set<String> disconnectedUsers;
    private ModelController modelController;
    private String name;
    private int expectedPlayers;
    private ArrayList<String> players;
    private GameState game;//Maybe controller??

    private ServerAPI_COME receive;
    private ServerAPI_GO send;
    private boolean full;
    private HashMap<String, ClientHandlerInterface> playersInterfaces;





    public void joinRoom(String name, ClientHandlerInterface handler){
        if(expectedPlayers == players.size()) {

            //handling rejoin
            if (disconnectedUsers.contains(name)) {
                players.add(name);
                disconnectedUsers.remove(name);
            } else {
                throw new RuntimeException("Too many players, can't join the room"); //TODO handle or change the except
            }

        }

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


    public Room(String name, int expectedPlayers){
        this.playersInterfaces = new HashMap<>();
        this.expectedPlayers = expectedPlayers;
        this.name = name;
        players = new ArrayList<String>();
        send = new ServerAPI_GO();
        full = false;


        disconnectedUsers = new HashSet<>();
        lastSeen = new ConcurrentHashMap<>();

        // Start the thread to continuously check the last seen list
        startHeartbeatChecker();
    }
    public void updateHeartBeat(String playerId) {
        lastSeen.put(playerId, System.currentTimeMillis());
    }
    public void handleADetectedDisconnection() {

        Boolean disconnection = false;
        while(disconnection!=true){
        long currentTime = System.currentTimeMillis();
        long timeout = 5000; // 5 seconds timeout

        for (String player : players) {
            Long lastSeenTime = lastSeen.get(player);
            if (lastSeenTime != null && currentTime - lastSeenTime > timeout) {
                if (!disconnectedUsers.contains(player)) {
                    disconnectedUsers.add(player);
                    disconnection = true;
                    System.out.println("Player " + player + " is disconnected.");
                    //TODO NOTIFY ALL PLAYERS WITH BROADCAST AND MAYE BLOCK UI
                }
            }
        }
        }
    }

    private void startHeartbeatChecker() {
        new Thread(() -> {
            while (true) {
                long currentTime = System.currentTimeMillis();
                long timeout = 5000; // 5 seconds timeout

                for (String player : players) {
                    Long lastSeenTime = lastSeen.get(player);
                    if (lastSeenTime != null && currentTime - lastSeenTime > timeout) {
                        if (!disconnectedUsers.contains(player)) {
                            disconnectedUsers.add(player);
                            System.out.println("Player " + player + " is disconnected.");
                            //TODO NOTIFY ALL PLAYERS WITH BROADCAST AND MAYE BLOCK UI
                        }
                    }
                }

                try {
                    Thread.sleep(1000); // Check every second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    public ArrayList<String> getPlayers() {
        return (ArrayList<String>) players.clone();
    }

    public String getName() {
        return name;
    }
    private void startGame(){
        //starts a thread starting the controller execution...
        //game = new GameState(players, "3");
        //game.setTurnState(TurnState.GAME_INITIALIZATION);
        modelController = new ModelController(players, name, send, this); //added room to get aggeess to methods for heartbeat
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
    public boolean contains(String player){
        return players.contains(player);
    }
    public boolean isFull(){return full;}
}
