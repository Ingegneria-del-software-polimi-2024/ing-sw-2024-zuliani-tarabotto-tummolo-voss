package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_RoomChoice;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;

import java.io.IOException;
import java.io.Serializable;

public class NewConnectionMessage implements Serializable, MessageToLobby {//todo implements MessageFromClient...

    private String username;
    private String roomName;
    private int expectedPlayers;

    private ClientHandlerInterface handler;

    public void execute(Lobby lobby){
        lobby.addConnection(username, handler);
        //For debug purpose only
        System.out.println("Added Connection");

        //we could move the following lines in a different message if needed
        lobby.enterRoom(username, roomName, expectedPlayers);
        //For debug purpose only
        System.out.println(username+" has entered the room "+roomName);

        //sending an ACK to the player
        try {
            lobby.sendToPlayer(username, new ACK_RoomChoice(username,roomName));
        } catch (MsgNotDeliveredException e) {
            throw new RuntimeException(e);
        }

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
