package Server.Web.Lobby;

import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_NewConnection;
import SharedWebInterfaces.Messages.MessagesFromLobby.AlreadyExistingNameMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class Lobby implements ControllerInterface {//TODO all the methods here must be sinchronized!! :)
    private ArrayList<Room> rooms;
    private HashMap<String, ClientHandlerInterface> players;
    private FirstSocketManager socketManager;
    private First_RMI_Manager rmiManager;
    private LobbyMessageQueue queue;

    public Lobby(int portSocket, int portRMI){
        try {
            rooms = new ArrayList<Room>();
            players = new HashMap<String, ClientHandlerInterface>();
            socketManager = FirstSocketManager.getInstance(this, portSocket);
            Thread listenForNewConnection = new Thread(socketManager);
            listenForNewConnection.start();
            rmiManager = First_RMI_Manager.getInstance(this, portRMI); //the ports must be different!!!
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
                System.out.println("Arrived a new message:"+  msg.getClass());
                try{
                    msg.execute(this);
                }catch (RuntimeException e){
                    if(e.getCause() instanceof MsgNotDeliveredException)
                        throw (MsgNotDeliveredException)e.getCause();
                    if(e.getCause() instanceof Remote)
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
        //if the name is already in use, we have to notify the client
        if (players.containsKey(name)){
            try {
                handlerInterface.sendToClient(new AlreadyExistingNameMessage(name));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            System.out.println("The name chosen was already taken");
            return;
        }
        //if the name isn't taken we add the player to the lobby's players list
        players.put(name, handlerInterface);
        System.out.println("Added a player: "+name);
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
        Room room = lookFor(roomName);
        if (room == null){
            createRoom(roomName, playerName, expectedPlayers);

        }else{
            room.joinRoom(playerName, players.get(playerName));

        }
    }

    public void verifyStart(String roomName){
        Room room = lookFor(roomName);
        if (room != null)
            room.verifyStart();
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
     *
     * @param playerName the name of the player
     * @return the name of the room in which the player is
     */
    public String isInRoom(String playerName){
        for(Room r : rooms){
            if(r.contains(playerName))
                return r.getName();
        }
        return null;
    }

    /**
     * adds a message to the queue of incoming messages
     * @param msg the incoming message to be added
     */
    public void enqueueMessage(MessageToLobby msg){
        queue.enqueueMessage(msg);
        System.out.println("Enqueued the following message: "+msg.getClass());
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

    ///////////////////////////////////////////////PRIVATE METHODS//////////////////////////////////////////////////////

    /**
     * creates a new room inserting the player who requested the creation of the room
     * @param roomName the name of the room
     * @param playerName who requested the creation of the room
     * @param expectedPlayers number of players to play with
     */
    private void createRoom(String roomName, String playerName, int expectedPlayers){
        Room room = new Room(roomName, expectedPlayers);
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

}
