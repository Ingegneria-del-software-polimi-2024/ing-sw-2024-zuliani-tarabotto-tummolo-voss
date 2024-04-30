package SharedWebInterfaces.Messages.MessagesFromClient;

import MockModel.Lobby;
import SharedWebInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromServer.ACK_RoomChoice;

import java.io.Serializable;
import java.rmi.RemoteException;

public class NewConnectionMessage implements Serializable {//todo implements MessageFromClient...

    private String username;
    private String roomName;
    private int expectedPlayers;

    public void execute(Lobby lobby, ClientHandlerInterface handler) throws RemoteException {
        lobby.addConnection(username, handler);
        lobby.enterRoom(username, roomName, expectedPlayers);
        //For debug purpose only
        System.out.println(username+" has entered the room "+roomName);
    }

    public NewConnectionMessage(String username, String roomName, int expectedPlayers) {
        this.expectedPlayers = expectedPlayers;
        this.username = username;
        this.roomName = roomName;
    }
}
