package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.io.Serializable;

/**
 * The type Ack room choice.
 */
public class ACK_RoomChoice implements MessageFromServer {

    private String user;
    /**
     * The Game.
     */
    public String game;

    /**
     * Instantiates a new Ack room choice.
     *
     * @param user the user
     * @param game the game
     */
    public ACK_RoomChoice(String user, String game){
        this.user = user;
        this.game = game;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets game.
     *
     * @return the game
     */
    public String getGame() {
        return game;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.setGameId(game);
    }
}
