package SharedWebInterfaces.Messages.MessagesFromServer;

import java.io.Serializable;

public class ACK_RoomChoice implements Serializable {//Todo implements messagefromserver

    private String user;
    public String game;
    public void execute(){}

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
}
