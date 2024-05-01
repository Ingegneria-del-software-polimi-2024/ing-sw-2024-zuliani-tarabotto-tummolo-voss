package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.ViewAPI_Interface;

import java.io.Serializable;

public class ACK_RoomChoice implements Serializable, MessageFromLobby {//Todo implements messagefromserver

    private String user;
    public String game;
    public ACK_RoomChoice(String user, String game){
        this.user = user;
        this.game = game;
    }

    public String getUser() {
        return user;
    }

    public String getGame() {
        return game;
    }

    @Override
    public void execute() {

    }
}
