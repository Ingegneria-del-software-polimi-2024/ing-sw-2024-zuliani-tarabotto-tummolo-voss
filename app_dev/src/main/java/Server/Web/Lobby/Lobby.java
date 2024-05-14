package Server.Web.Lobby;

import Client.Web.RMI_ServerHandler;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.SharedInterfaces.ControllerInterface;
import SharedWebInterfaces.Messages.MessagesToLobby.MessageToLobby;
import SharedWebInterfaces.Messages.MessagesToLobby.NewConnectionMessage;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
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

    public Lobby(int port){
        try {
            rooms = new ArrayList<Room>();
            players = new HashMap<String, ClientHandlerInterface>();
            socketManager = FirstSocketManager.getInstance(this, port);
            Thread listenForNewConnection = new Thread(socketManager);
            listenForNewConnection.start();
            rmiManager = new First_RMI_Manager(this, port + 3); //the ports must be different!!!
            queue = new LobbyMessageQueue();
        }catch (RemoteException | RuntimeException e){
            throw new RuntimeException("Can't create lobby, control the connection parameters", e);
        }
    }

    public void start() throws MsgNotDeliveredException, StartConnectionFailedException {
        //Starts dequeueing messages
        while(true){
            MessageToLobby msg = queue.getNextMessage();
            if(msg != null){
                try{
                    msg.execute(this);
                }catch (RuntimeException e){
                    if(e.getCause() instanceof MsgNotDeliveredException)
                        throw (MsgNotDeliveredException)e.getCause();
                    if(e.getCause() instanceof Remote)
                        throw new StartConnectionFailedException();
                    throw new RuntimeException("Execution couldn't happen due to an unknown error");
                }
            }
        }
    }

    public void addConnection(String name, ClientHandlerInterface handlerInterface){
        if (players.containsKey(name))
            throw new RuntimeException();//TODO is this good? IDK
        players.put(name, handlerInterface);
    }
    private void createRoom(String roomName, String playerName, int expectedPlayers){
        Room room = new Room(roomName, expectedPlayers);
        rooms.add(room);
        room.joinRoom(playerName, players.get(playerName));
    }

    public void enterRoom(String playerName, String roomName, int expectedPlayers){
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
            if(!r.isFull())
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

    public void sendToPlayer(String playerName, MessageFromServer msg) throws MsgNotDeliveredException {
        try {
            players.get(playerName).sendToClient(msg);
        } catch (RemoteException e) {
            throw new MsgNotDeliveredException(msg);
        }
    }

    public void newRMI_Connection(ServerHandlerInterface handlerInterface){
        try {
            rmiManager.newHandler(handlerInterface, getGameNames());
        } catch (RemoteException e) {
            throw new RuntimeException("Can't create connection with the new handler",e);
        }
    }
}
