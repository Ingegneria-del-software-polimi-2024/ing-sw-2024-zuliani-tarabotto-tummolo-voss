package Server.Web.Lobby;

import Server.ModelTranslator;
import Server.Web.Game.ServerAPI_COME;
import Server.Web.Game.ServerAPI_GO;
import Server.Web.Lobby.LobbyExceptions.CantJoinRoomExcept;
import SharedWebInterfaces.Messages.MessagesFromClient.toModelController.I_WantToReconnectMessage;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_RoomChoice;
import SharedWebInterfaces.Messages.MessagesFromServer.ReconnectionsMSG.ReconnectionHappened;
import SharedWebInterfaces.Messages.MessagesToLobby.CloseARoomMessage;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.rmi.RemoteException;

/**
 * The type Room.
 * This class represents a room in the lobby, where players can join and play a game.
 */
public class Room {
    /**
     * The Last seen hashmap, containing the couples (playerName, lastSeenTime) indicating the time when the  player
     * was seen for the last time in the room.
     */
    private ConcurrentHashMap<String, Long> lastSeen;
    /**
     * The Disconnected users set, containing the nicknames of the players that are disconnected.
     */
    private final Set<String> disconnectedUsers;
    /**
     * The Model translator, the interface mediating between controller and network.
     */
    private ModelTranslator modelTranslator;
    /**
     * The Name of the room.
     */
    private String name;
    /**
     * The Expected players, the number of players to wait in order to start the game.
     */
    private int expectedPlayers;
    /**
     * The Players, the list of players in the room.
     */
    private ArrayList<String> players;
    /**
     * The Receiver, the server API for receiving messages.
     */
    private ServerAPI_COME receive;
    /**
     * The Sender, the server API for sending messages.
     */
    private ServerAPI_GO send;
    /**
     * The Full, a boolean indicating if the room is full.
     */
    private boolean full;
    /**
     * The Players interfaces, a hashmap containing the couples (playerName, handler) indicating the handler of the player.
     */
    private HashMap<String, ClientHandlerInterface> playersInterfaces;
    /**
     * The Heartbeat checker, a thread checking the heartbeat of the players.
     */
    private Thread heartbeatChecker = null;
    /**
     * The Lobby, the lobby to which the room belongs.
     */

    private final Lobby lobby;


    /**
     * Allows a player in the room
     *
     * @param name    the player's nickname
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
                throw new CantJoinRoomExcept(false);
            }
        }
        players.add(name);
        send.setHandler(name, handler);
        playersInterfaces.put(name, handler);
        if(expectedPlayers == players.size())
            full = true;
    }

    /**
     * Verifies if a match in the room can start
     */
    public void verifyStart(){
        if(expectedPlayers == players.size()) {
            //furthermore we must also add a call to this function in the function reconnect
            Thread gameStarter = new Thread(this::startGame);
            gameStarter.start();
        }
    }

    /**
     * Class constructor
     *
     * @param name            the name of the room
     * @param expectedPlayers the number of player to wait in order to start the game
     * @param lobby           the lobby to which the room belongs
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

    /**
     * Updates heart beat by adding the current time to the last seen hashmap.
     *
     * @param playerId the player id
     */
    public void updateHeartBeat(String playerId) {
        lastSeen.put(playerId, System.currentTimeMillis());
    }

    /**
     * Handle a detected disconnection.
     */
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
                    }else {
                        return;
                    }
                }
            }
        }


    }

    /**
     * Start heartbeat checker, the thread checking if the difference between current time and last seen timestamp is more than the timeout.
     * If so, the player is disconnected.
     */
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

    /**
     * Interrupt heartbeat checker.
     */
    public void interruptHBChecker(){
        heartbeatChecker.interrupt();
        try {
            heartbeatChecker.join();
        } catch (InterruptedException e) {
            System.out.println("An error occurred when stopping the HBChecker of game: "+name);
        }
    }

    /**
     * Disconnects a player.
     * @param player the name of the player to disconnect
     */
    private void disconnectPlayer(String player){
        disconnectedUsers.add(player);
        playersInterfaces.put(player, null);
        send.disconnectPlayer(player);
        if(modelTranslator !=null)
            modelTranslator.setPlayerInactive(player);

        System.out.println("Player " + player + " is disconnected.");
    }

    /**
     * Gets players.
     *
     * @return the players in the room
     */
    public ArrayList<String> getPlayers() {
        return (ArrayList<String>) players.clone();
    }


    /**
     * If a player quits the game while still in the "waiting for other players phase", we simply remove
     * him from all the room's lists, so that another player can instantly join the game
     *
     * @param player the player's name
     */
    public void removePlayerBeforeStart(String player){
        lastSeen.remove(player);
        players.remove(player);
        playersInterfaces.remove(player);
        send.disconnectPlayer(player);
    }


    /**
     * Gets name.
     *
     * @return the room's name
     */
    public String getName() {
        return name;
    }

    /**
     * starts a thread starting the game
     */
    private void startGame(){
        modelTranslator = new ModelTranslator(players, name, send, this, disconnectedUsers);
        receive = new ServerAPI_COME(modelTranslator);
        try {
            for(String p : playersInterfaces.keySet()){
                if(playersInterfaces.get(p)!= null)
                    playersInterfaces.get(p).setReceiver(receive);
            }
        }catch (RemoteException e){
            System.out.println("Can't join the room due to a communication error");
            return;
        }
        Thread thread1 = new Thread(() -> receive.loop());
        thread1.start();
        modelTranslator.initializeGameState();
        System.out.println("game can now start");
    }

    /**
     * verifies if a player is contained in a room
     *
     * @param player the player's nickname
     * @return true if the player is in the room, false otherwise
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
     * Reconnects the disconnected player in the place he was disconnected from
     *
     * @param playerID         the reconnecting player
     * @param handlerInterface the handler of the player
     */
    public void reconnect(String playerID, ClientHandlerInterface handlerInterface){
        lobby.reconnectPlayer(playerID, handlerInterface);
        lastSeen.put(playerID, System.currentTimeMillis());
        playersInterfaces.put(playerID, handlerInterface);
        send.setHandler(playerID, handlerInterface);
        updateHeartBeat(playerID);
        disconnectedUsers.remove(playerID);
        try {
            playersInterfaces.get(playerID).sendToClient(new ACK_RoomChoice(playerID, name));
        } catch (RemoteException e) {
            return;
        }

        if(modelTranslator != null){
            try {
                playersInterfaces.get(playerID).setReceiver(receive);
            } catch (RemoteException e) {
                return;
            }
            receive.sendToServer(new I_WantToReconnectMessage(playerID, name));
            return;
        }else{
            try {
                playersInterfaces.get(playerID).sendToClient(new ReconnectionHappened(playerID));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Correctly reconnected player: "+playerID);

    }

    /**
     * Is disconnected boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public boolean isDisconnected(String name){
        return disconnectedUsers.contains(name);
    }

    /**
     * Ends the game by sending CloseARoomMessage.
     */
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

    /**
     * Allows a player to quit the game.
     *
     * @param playerID the player id
     */
    public void quitGame(String playerID){

        disconnectedUsers.add(playerID);
        playersInterfaces.put(playerID, null);
        send.disconnectPlayer(playerID);
        if(modelTranslator !=null)
            modelTranslator.setPlayerInactive(playerID);

    }
}
