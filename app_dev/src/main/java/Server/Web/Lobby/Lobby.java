package Server.Web.Lobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.Messages.MessagesToLobby.NewConnectionMessage;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class Lobby implements ControllerInterface {//TODO all the methods here must be sinchronized!! :)
    private int port;
    private ArrayList<Room> rooms;
    private HashMap<String, ClientHandlerInterface> players;
    private FirstSocketManager socketManager;
    private First_RMI_Manager rmiManager;

    private LobbyMessageQueue queue;

    public Lobby(int port) throws RemoteException, AlreadyBoundException {
        rooms = new ArrayList<Room>();
        players = new HashMap<String, ClientHandlerInterface>();
        socketManager = FirstSocketManager.getInstance(this, port);
        Thread listenForNewConnection = new Thread(socketManager);
        listenForNewConnection.start();
        rmiManager = new First_RMI_Manager(this, port+3); //the ports must be different!!!
        queue = new LobbyMessageQueue();
    }

    public void addConnection(String name, ClientHandlerInterface handlerInterface){
        if (players.containsKey(name))
            throw new RuntimeException();//TODO is this good? IDK
        players.put(name, handlerInterface);
    }
    private void createRoom(String roomName, String playerName, int expectedPlayers) throws RemoteException {
        Room room = new Room(roomName, expectedPlayers);
        rooms.add(room);
        room.joinRoom(playerName, players.get(playerName));
    }

    public void enterRoom(String playerName, String roomName, int expectedPlayers) throws RemoteException {
        Room room = lookFor(roomName);
        if (room == null){
            createRoom(roomName, playerName, expectedPlayers);
            //for debug purpose only
            System.out.println("correctly created room: "+roomName);
        }else{
            room.joinRoom(playerName, players.get(playerName));
            //for debug purpose only
            System.out.println("correctly joined room: "+roomName);
        }
    }

    private Room lookFor(String roomName){
        for(Room r : rooms)
            if(r.getName().equals(roomName)){
                return r;
            }
        return null;
    }

    public ArrayList<String> getGameNames(){
        if(rooms.isEmpty())
            return null;
        ArrayList<String> retVal = new ArrayList<String>();

        for(Room r : rooms){
            if(r.isFull())
                retVal.add(r.getName());
        }
        return retVal;
    }

    public String getPlayerName(ClientHandlerInterface handlerInterface){
        for(String name : players.keySet())
            if(players.get(name) == handlerInterface)
                return name;
        return null;
    }
    public String isInRoom(String playerName){
        for(Room r : rooms){
            if(r.contains(playerName))
                return r.getName();
        }
        return null;
    }
    public void enqueueMessage(MessageToLobby msg){
        queue.enqueueMessage(msg);
        System.out.println("Enqueued the following message: "+msg.getClass());
    }
    public void start() throws IOException {
        System.out.println("started dequeueing messages...");
        while(true){
            MessageToLobby msg = queue.getNextMessage();
            if(msg != null){//todo more appropriate control
                System.out.println("Found a new msg");
                System.out.println("msg is instance of NewConnectionMSG: "+ (msg instanceof NewConnectionMessage));
                System.out.println("Type of the message is: "+msg.getClass());
                msg.execute(this);

            }
        }
    }
    public void sendToPlayer(String playerName, MessageFromServer msg) throws IOException {
        players.get(playerName).sendToClient(msg);
    }

    public void newRMI_Connection(String registry, String host, int port) throws AlreadyBoundException, NotBoundException, IOException {
        rmiManager.newHandler(registry, host, port, getGameNames());
    }
}
