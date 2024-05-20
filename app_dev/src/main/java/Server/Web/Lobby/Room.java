package Server.Web.Lobby;

import Server.ModelController;
import Server.Web.Game.ServerAPI_COME;
import Server.Web.Game.ServerAPI_GO;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import model.GameState.GameState;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Room {
    private String name;
    private int expectedPlayers;
    private ArrayList<String> players;
    private GameState game;//Maybe controller??
    private ServerAPI_COME receive;
    private ServerAPI_GO send;
    private boolean full;
    private ConcurrentHashMap<String, Long> lastSeen;





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
        try {
            handler.setReceiver(receive);
        }catch (RemoteException e){
            throw new RuntimeException("Can't join the room due to a comunication error");
        }
        send.setHandler(name, handler);
        if(expectedPlayers == players.size())
            startGame();
    }



    private Set<String> disconnectedUsers;
    private ModelController modelController;

    public Room(String name, int expectedPlayers) {
        this.expectedPlayers = expectedPlayers;
        this.name = name;
        players = new ArrayList<>();
        receive = new ServerAPI_COME();
        send = new ServerAPI_GO();
        full = false;
        disconnectedUsers = new HashSet<>();
        lastSeen = new ConcurrentHashMap<>();

        // Start the thread to continuously check the last seen list
        startHeartbeatChecker();
    }

    // Method to set the ModelController


    // Thread to continuously check last seen list from the ModelController
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
    }
    public boolean contains(String player){
        return players.contains(player);
    }
    public boolean isFull(){return full;}

    public void updateHeartBeat(String playerId) {
        lastSeen.put(playerId, System.currentTimeMillis());
    }
}
