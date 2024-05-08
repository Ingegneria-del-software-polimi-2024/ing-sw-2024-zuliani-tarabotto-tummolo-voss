package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.io.Serializable;

public class ACK_RoomChoice implements MessageFromServer {

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
    public void execute(ViewAPI_Interface view) {

    }
}
