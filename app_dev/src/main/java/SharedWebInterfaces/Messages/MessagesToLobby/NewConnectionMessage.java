package SharedWebInterfaces.Messages.MessagesToLobby;

import MockModel.Lobby;
import SharedWebInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_RoomChoice;

import java.io.IOException;
import java.io.Serializable;

public class NewConnectionMessage implements Serializable, MessageToLobby {//todo implements MessageFromClient...

    private String username;
    private String roomName;
    private int expectedPlayers;

    private ClientHandlerInterface handler;

    public void execute(Lobby lobby) throws IOException {
        lobby.addConnection(username, handler);
        System.out.println("Added Connection");
        lobby.enterRoom(username, roomName, expectedPlayers);
        //For debug purpose only
        System.out.println(username+" has entered the room "+roomName);

        //sending an ACK to the player
        lobby.sendToPlayer(username, new ACK_RoomChoice(username,roomName));

    }

    @Override
    public String getSender() {
        return username;
    }

    public NewConnectionMessage(String username, String roomName, int expectedPlayers) {
        this.expectedPlayers = expectedPlayers;
        this.username = username;
        this.roomName = roomName;
    }

    public void setHandler(ClientHandlerInterface handler) {
        this.handler = handler;
    }

}
