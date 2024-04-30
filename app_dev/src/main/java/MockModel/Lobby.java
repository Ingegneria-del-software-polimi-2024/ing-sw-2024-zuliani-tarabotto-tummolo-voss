package MockModel;

import Server.FirstSocketManager;
import Server.First_RMI_Manager;
import SharedWebInterfaces.ClientHandlerInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {//TODO all the methods here must be sinchronized!! :)
    private int port;
    private ArrayList<Room> rooms;
    private HashMap<String, ClientHandlerInterface> players;
    private FirstSocketManager socketManager;
    private First_RMI_Manager rmiManager;
    public Lobby(int port){
        rooms = new ArrayList<Room>();
        players = new HashMap<String, ClientHandlerInterface>();
        socketManager = FirstSocketManager.getInstance(this, port);
        Thread listenForNewConnection = new Thread(socketManager);
        listenForNewConnection.start();
        rmiManager = new First_RMI_Manager();

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
}
