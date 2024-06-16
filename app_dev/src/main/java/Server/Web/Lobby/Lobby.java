package Server.Web.Lobby;

import Server.Web.Lobby.LobbyExceptions.CantJoinRoomExcept;
import SharedWebInterfaces.Messages.MessagesFromLobby.*;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.Messages.MessagesToLobby.HeartbeatMessage;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class Lobby implements ControllerInterface {//TODO all the methods here must be sinchronized!! :)
    private ArrayList<Room> rooms;
    private HashMap<String, ClientHandlerInterface> players;
    private FirstSocketManager socketManager;
    private First_RMI_Manager rmiManager;
    private LobbyMessageQueue queue;
    private HashMap<String, Long> lastSeenInLobby;

    public Lobby(int portSocket, int portRMI){
        try {
            rooms = new ArrayList<Room>();
            players = new HashMap<String, ClientHandlerInterface>();
            lastSeenInLobby = new HashMap<String, Long>();
            socketManager = FirstSocketManager.getInstance(this, portSocket);
            Thread listenForNewConnection = new Thread(socketManager);
            listenForNewConnection.start();
            rmiManager = First_RMI_Manager.getInstance(this, portRMI);
            queue = new LobbyMessageQueue();
        }catch (RemoteException | RuntimeException e){
            throw new RuntimeException("Can't create lobby, control the connection parameters", e);
        }
    }

    /**
     * dequeues messages from the toDoQueue
     * @throws MsgNotDeliveredException when the message couldn't be delivered
     * @throws StartConnectionFailedException when the connection with client couldn't be started
     */
    public void start() throws MsgNotDeliveredException, StartConnectionFailedException {
        //Starts dequeueing messages
        while(true){
            MessageToLobby msg = queue.getNextMessage();
            if(msg != null){
                //DEBUG
                if(!(msg instanceof HeartbeatMessage)) {
                    System.out.println("———————————————————————————————————————————————————————————————");
                    System.out.println("Arrived a new message:      " + msg.getClass());
                    System.out.println("———————————————————————————————————————————————————————————————");
                }
                try{
                    msg.execute(this);
                }catch (RuntimeException e){
                    if(e.getCause() instanceof MsgNotDeliveredException)
                        throw (MsgNotDeliveredException)e.getCause();
                    if(e.getCause() instanceof RemoteException)
                        throw new StartConnectionFailedException();
                    throw new RuntimeException("Execution couldn't happen due to an unknown error "+e.getCause().getClass(), e);
                }
            }
        }
    }

    /**
     * Memorizes a new couple (nickname, personal handler) to effectively use the new connection
     * @param name the player
     * @param handlerInterface the handler of the player
     */
    public void addConnection(String name, ClientHandlerInterface handlerInterface){
        //if the name is already present we must investigate
        if(name.isEmpty()){
            try {
                handlerInterface.sendToClient(new AlreadyExistingNameMessage(name));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        if (players.containsKey(name)){
            Room room = isInRoom(name);
            if(room != null && room.isDisconnected(name)){
                room.reconnect(name, handlerInterface);
                return;
            }else if(room == null && System.currentTimeMillis()-lastSeenInLobby.get(name) > HeartBeatSettings.timeout){
                //here we should handle disconnections happened before joining a room
                manageNewConnection(name, handlerInterface);
                return;
            }else{
                try {
                    handlerInterface.sendToClient(new AlreadyExistingNameMessage(name));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("The name chosen was already taken");
                return;
            }
        }
        //if the name isn't taken we add the player to the lobby's players list
        manageNewConnection(name, handlerInterface);
        System.out.println("Added a player: "+name);
    }

    private void manageNewConnection(String name, ClientHandlerInterface handlerInterface){
        players.put(name, handlerInterface);
        try {
            sendToPlayer(name, new ACK_NewConnection(name));
        } catch (MsgNotDeliveredException e) {
            throw new RuntimeException(e);
        }
        //TODO sistema exception handling

    }

    /**
     * inserts the player in the requested room, if the room doesn't exist creates a new room and inserts the player there
     * @param playerName the player nickname
     * @param roomName the name of the room
     * @param expectedPlayers the number of expected players, it is null if the room already exists
     */
    public void enterRoom(String playerName, String roomName, int expectedPlayers){
        if(roomName == null||!players.containsKey(playerName))
            return;
        if(roomName.isEmpty() || roomName.trim().isEmpty()) {
            try {
                sendToPlayer(playerName, new CantJoinRoomMsg(true));
            } catch (MsgNotDeliveredException e) {
                throw new RuntimeException(e);
                //todo remove
            }
            return;
        }
        Room room = lookFor(roomName);
        try {
            if (room == null) {
                createRoom(roomName, playerName, expectedPlayers);

            }else if (expectedPlayers == 0) {
                room.joinRoom(playerName, players.get(playerName));

            }else{
                try {
                    sendToPlayer(playerName, new CantJoinRoomMsg(true));
                    return;
                } catch (MsgNotDeliveredException ex) {
                    throw new RuntimeException(ex);
                    //TODO remove this trycatch
                }
            }
        }catch (CantJoinRoomExcept e){
            try {
                sendToPlayer(playerName, new CantJoinRoomMsg(e.isCreating()));
            } catch (MsgNotDeliveredException ex) {
                throw new RuntimeException(e);
                //TODO remove this trycatch
            }
            return;
        }
        //sending ACK
        try {
            sendToPlayer(playerName, new ACK_RoomChoice(playerName, roomName));
        } catch (MsgNotDeliveredException e) {
            throw new RuntimeException(e);
            //TODO verify this is handled correctly
        }
        verifyStart(roomName);
    }


    /**
     *
     * @return returns the names of the available rooms doesn't return the rooms which are already full
     */
    public ArrayList<String> getGameNames(){
        if(rooms.isEmpty())
            return null;
        ArrayList<String> retVal = new ArrayList<String>();

        for(Room r : rooms){
            if(!r.isFull())
                retVal.add(r.getName());
        }
        return retVal;
    }

    /**
     * given the handler returns the nickname of the associated player
     * @param handlerInterface the personal handler
     * @return the nickname of the player
     */
    public String getPlayerName(ClientHandlerInterface handlerInterface){
        for(String name : players.keySet())
            if(players.get(name) == handlerInterface)
                return name;
        return null;
    }



    /**
     * adds a message to the queue of incoming messages
     * @param msg the incoming message to be added
     */
    public void enqueueMessage(MessageToLobby msg){
        queue.enqueueMessage(msg);
    }

    /**
     * sends a message to a player
     * @param playerName the recipient nickname
     * @param msg the message to be delivered
     * @throws MsgNotDeliveredException if the message couldn't be delivered
     */
    public void sendToPlayer(String playerName, MessageFromServer msg) throws MsgNotDeliveredException {
        try {
            players.get(playerName).sendToClient(msg);
        } catch (RemoteException e) {
            throw new MsgNotDeliveredException(msg);
        }
    }

    /**
     * method to instantiate a new RMI connection with a client
     * @param handlerInterface the client remote interface to communicate with
     */
    public void newRMI_Connection(ServerHandlerInterface handlerInterface){
        try {
            rmiManager.newHandler(handlerInterface, getGameNames());
        } catch (RemoteException e) {
            throw new RuntimeException("Can't create connection with the new handler",e);
        }
    }

    public void closeRoom(String roomName){
        Room room = getRoomByName(roomName);
        if(room != null)
            rooms.remove(room);

        System.out.println("Room "+roomName+" is correctly closed");
    }

    ///////////////////////////////////////////////PRIVATE METHODS//////////////////////////////////////////////////////

    /**
     * creates a new room inserting the player who requested the creation of the room
     * @param roomName the name of the room
     * @param playerName who requested the creation of the room
     * @param expectedPlayers number of players to play with
     */
    private void createRoom(String roomName, String playerName, int expectedPlayers) throws CantJoinRoomExcept {
        if(expectedPlayers<2||expectedPlayers>4)
            throw new CantJoinRoomExcept(true);
        Room room = new Room(roomName, expectedPlayers, this);
        rooms.add(room);
        room.joinRoom(playerName, players.get(playerName));
    }

    /**
     * allows to find the room with its name
     * @param roomName the room name
     * @return the reference to the room
     */
    private Room lookFor(String roomName){
        for(Room r : rooms)
            if(r.getName().equals(roomName)){
                return r;
            }
        return null;
    }

    /**
     *
     * @param playerName the name of the player
     * @return the name of the room in which the player is
     */
    private Room isInRoom(String playerName){
        for(Room r : rooms){
            if(r.contains(playerName))
                return r;
        }
        return null;
    }

    public void updateHeartBeat(String playerId) {
        if(playerId == null)
            return;
        // Update the last seen timestamp for the player in the room
        Room r = isInRoom(playerId);
        if(r == null){
            lastSeenInLobby.put(playerId, System.currentTimeMillis());
        }else{
            r.updateHeartBeat(playerId);
        }
    }

    private Room getRoomByName(String roomName){
        for(Room room : rooms){
            if(room.getName().equals(roomName))
                return room;
        }
        return null;
    }
//    /**
//     * reconnects the disconnected player in the place he was disconnected from
//     * @param playerID the reconnecting player
//     * @param handlerInterface the handler of the player
//     */
//    private void reconnect(String playerID, ClientHandlerInterface handlerInterface){
//        players.put(playerID, handlerInterface);
//        Room r = isInRoom(playerID);
//        //if the player was present in a room we must bring him back there and notify the game state and the player
//        if(r != null) {
//            r.reconnect(playerID, handlerInterface);
//        }else{
//            //otherwise the player must stay in the lobby
//            System.out.println(playerID+" reconnected to the lobby");
//            try {
//                sendToPlayer(playerID, new ACK_NewConnection(playerID));
//            } catch (MsgNotDeliveredException e) {
//                throw new RuntimeException(e);
//                //TODO handle exception
//            }
//        }
//    }

    /**
     * verifies if a room has reached the maximum number of players
     * @param roomName the name of the room
     */
    private void verifyStart(String roomName){
        Room room = lookFor(roomName);
        if (room != null)
            room.verifyStart();
    }

    public void quitGameBeforeStart(String roomName, String player){
        Room room = lookFor(roomName);
        //if there was just one player in the room we destroy it
        if(room.getPlayers().size() == 1){
            closeRoom(roomName);
        }else{
            //if there was more than one player in the room we just remove the player from the room's players list
            room.removePlayerBeforeStart(player);
        }
        try {
            sendToPlayer(player, new AvailableGames(getGameNames(), true)); //true: we notify that we are coming back from the waiting room
        } catch (MsgNotDeliveredException e) {
            throw new RuntimeException(e);
        }
    }
}
