package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type End game message.
 * This message is sent by the server to notify the clients that the game has ended.
 * It contains the final points of each player and the winner(s) of the game.
 */
public class EndGameMessage implements MessageFromServer{
    /**
     * The Final points for each player.
     */
    private HashMap<String, Integer> finalPoints;
    /**
     * The winners of the game.
     */
    private ArrayList<String> winners;

    /**
     * Instantiates a new End game message.
     *
     * @param finalPoints the final points
     * @param winners     the winners
     */
    public EndGameMessage(HashMap<String, Integer> finalPoints, ArrayList winners) {
        this.finalPoints = finalPoints;
        this.winners = winners;
    }

    /**
     * Notifies the receiver of the end of the game
     * @param view the view interface of the receiver
     */
    @Override
    public void execute(ViewAPI_Interface view) {
        view.setFinalPoints(finalPoints, winners);
    }
}
