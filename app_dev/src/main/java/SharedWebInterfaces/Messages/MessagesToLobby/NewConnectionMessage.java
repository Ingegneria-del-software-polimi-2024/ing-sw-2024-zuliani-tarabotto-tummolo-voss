package SharedWebInterfaces.Messages.MessagesToLobby;

import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_NewConnection;
import SharedWebInterfaces.SharedInterfaces.ClientHandlerInterface;
import SharedWebInterfaces.Messages.MessagesFromLobby.ACK_RoomChoice;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;

import java.io.IOException;
import java.io.Serializable;

public class NewConnectionMessage implements Serializable, MessageToLobby {//todo implements MessageFromClient...

    private String username;
    //private String roomName;
    //private int expectedPlayers;

    private ClientHandlerInterface handler;

    public void execute(Lobby lobby){
        lobby.addConnection(username, handler);
        //For debug purpose only
//        lobby.enterRoom(username, roomName, expectedPlayers);
        //For debug purpose only
//        System.out.println(username+" has entered the room "+roomName);

        //sending an ACK to the player
    }

    @Override
    public String getSender() {
        return username;
    }

    public NewConnectionMessage(String username) {
        this.username = username;
    }

    public void setHandler(ClientHandlerInterface handler) {
        this.handler = handler;
    }

}
