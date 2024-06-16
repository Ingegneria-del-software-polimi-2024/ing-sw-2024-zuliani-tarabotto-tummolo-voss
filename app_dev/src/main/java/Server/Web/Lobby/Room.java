package Server.Web.Lobby;

import Server.ModelController;
import Server.Web.Game.ServerAPI_COME;
import Server.Web.Game.ServerAPI_GO;
import Server.Web.Lobby.LobbyExceptions.CantJoinRoomExcept;
import SharedWebInterfaces.Messages.MessagesFromClient.toModelController.I_WantToReconnectMessage;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_RoomChoice;
import SharedWebInterfaces.Messages.MessagesToLobby.CloseARoomMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.JoinGameMessage;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import model.GameState.GameState;
import model.GameState.TurnState;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.rmi.RemoteException;

public class Room {

    private ConcurrentHashMap<String, Long> lastSeen;
    private final Set<String> disconnectedUsers;
    private ModelController modelController;
    private String name;
    private int expectedPlayers;
    private ArrayList<String> players;
    private ServerAPI_COME receive;
    private ServerAPI_GO send;
    private boolean full;
    private HashMap<String, ClientHandlerInterface> playersInterfaces;

    private Thread heartbeatChecker = null;

    private final Lobby lobby;


    /**
     * allows a player in the room
     * @param name the player's nickname
     * @param handler the player's handler
     * @throws CantJoinRoomExcept if the room is full
     */
    public void joinRoom(String name, ClientHandlerInterface handler) throws CantJoinRoomExcept {
        if(full) {
            //handling rejoin
            if (disconnectedUsers.contains(name)) {
                disconnectedUsers.remove(name);
                updateHeartBeat(name);
            } else {
                throw new CantJoinRoomExcept(false); //TODO handle or change the except
            }
        }
        players.add(name);
        send.setHandler(name, handler);
        playersInterfaces.put(name, handler);
        if(expectedPlayers == players.size())
            full = true;
    }

    /**
     * verifies if a match in the room can start
     */
    public void verifyStart(){
        if(expectedPlayers == players.size()) {
//TODO if we want the game not to start when some player is disconnected we must add && disconnectedUsers.isEmpty(),
//furthermore we must also add a call to this function in the function reconnect
            Thread gameStarter = new Thread(this::startGame);
            gameStarter.start();//TODO CONTROL IF THIS MAKES SENSE
        }
    }

    /**
     * class constructor
     * @param name the name of the room
     * @param expectedPlayers the number of player to wait in order to start the game
     */
    public Room(String name, int expectedPlayers, Lobby lobby){
        this.playersInterfaces = new HashMap<>();
        this.expectedPlayers = expectedPlayers;
        this.name = name;
        players = new ArrayList<String>();
        send = new ServerAPI_GO();
        full = false;

        this.lobby = lobby;

        disconnectedUsers = Collections.synchronizedSet(new HashSet<>());
        lastSeen = new ConcurrentHashMap<>();

        startHeartbeatChecker();
    }

    public void updateHeartBeat(String playerId) {
        lastSeen.put(playerId, System.currentTimeMillis());
    }

    public void handleADetectedDisconnection() {
        //DEBUG
        System.out.println("handleADetectedDisconnection was called");
        boolean disconnection = false;
        while(!disconnection){
            long currentTime = System.currentTimeMillis();

            for (String player : players) {
                Long lastSeenTime = lastSeen.get(player);

                if (lastSeenTime != null && currentTime - lastSeenTime > HeartBeatSettings.timeout) {
                    if (!disconnectedUsers.contains(player)) {
                        disconnectPlayer(player);
                        disconnection = true;
                        System.out.println("Correctly disconnected player "+player);
                        //TODO NOTIFY ALL PLAYERS WITH BROADCAST AND MAYBE BLOCK UI
                    }
                }
            }
        }


    }

    private void startHeartbeatChecker() {
        heartbeatChecker = new Thread(() -> {
            while (true) {
                long currentTime = System.currentTimeMillis();

                for (String player : players) {
                    Long lastSeenTime = lastSeen.get(player);

                    if (!disconnectedUsers.contains(player) && lastSeenTime != null && currentTime - lastSeenTime > HeartBeatSettings.timeout) {
                        //DEBUG
                        System.out.println("a disconnection was seen by the thread");
                        System.out.println("the delta percieved is: "+ (currentTime - lastSeenTime)+ " milliseconds");
                        System.out.println("the player involved is: "+player);
                        disconnectPlayer(player);
                        //TODO NOTIFY ALL PLAYERS WITH BROADCAST AND MAYBE BLOCK UI
                    }
                }
                try {
                    Thread.sleep(HeartBeatSettings.checkFreq); // Check every second
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        heartbeatChecker.start();
    }

    public void interruptHBChecker(){
        heartbeatChecker.interrupt();
        try {
            heartbeatChecker.join();
        } catch (InterruptedException e) {
            System.out.println("An error occurred when stopping the HBChecker of game: "+name);
        }
    }

    private void disconnectPlayer(String player){
        disconnectedUsers.add(player);
        playersInterfaces.put(player, null);
        send.disconnectPlayer(player);
        if(modelController!=null)
            modelController.setPlayerInactive(player);

        System.out.println("Player " + player + " is disconnected.");
    }

    /**
     * @return the players in the room
     */
    public ArrayList<String> getPlayers() {
        return (ArrayList<String>) players.clone();
    }


    /**
     * if a player quits the game while still in the "waiting for other players phase", we simply remove
     * him from all the room's lists, so that another player can instantly join the game
     * @param player
     */
    public void removePlayerBeforeStart(String player){
        lastSeen.keySet().remove(player);
        players.remove(player);
        playersInterfaces.keySet().remove(player);
    }



    /**
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
        modelController = new ModelController(players, name, send, this);
        receive = new ServerAPI_COME(modelController);
        try {
            for(String p : playersInterfaces.keySet()){
                playersInterfaces.get(p).setReceiver(receive);
            }
        }catch (RemoteException e){
            throw new RuntimeException("Can't join the room due to a communication error");
            //TODO handle exception
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

    /**
     * reconnects the disconnected player in the place he was disconnected from
     * @param playerID the reconnecting player
     * @param handlerInterface the handler of the player
     */
    public void reconnect(String playerID, ClientHandlerInterface handlerInterface){
        playersInterfaces.put(playerID, handlerInterface);
        send.setHandler(playerID, handlerInterface);
        updateHeartBeat(playerID);
        disconnectedUsers.remove(playerID);
        try {
            playersInterfaces.get(playerID).sendToClient(new ACK_RoomChoice(playerID, name));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
            //TODO handle exception
        }

        if(modelController != null){
            try {
                playersInterfaces.get(playerID).setReceiver(receive);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
                //TODO is the exception necessary?
            }
            receive.sendToServer(new I_WantToReconnectMessage(playerID, name));
            return;
        }

    }

    public boolean isDisconnected(String name){
        return disconnectedUsers.contains(name);
    }

    public void ended() {
        //since the disconnection of the players is handled by the only thread unraveling messages to the room,
        //as the second-from-last player leaves the room there will be still a player connected or, at least a handler
        //interface linked to him (in order to send a message to the lobby from the server internet connection
        //is NOT required)
//        for (String player : players) {
//            if (!disconnectedUsers.contains(player)) {
//                boolean sent = true;
//                ClientHandlerInterface handler = playersInterfaces.get(player);
//                try {
//                    handler.deliverToLobby(new CloseARoomMessage(name));
//                } catch (RemoteException e) {
//                    sent = false;
//                }
//                if (sent)
//                    return;
//            }
//        }
        lobby.enqueueMessage(new CloseARoomMessage(name));
    }

    public void quitGame(String playerID){

        disconnectedUsers.add(playerID);
        playersInterfaces.put(playerID, null);
        send.disconnectPlayer(playerID);
        if(modelController!=null)
            modelController.setPlayerInactive(playerID);

    }
}
