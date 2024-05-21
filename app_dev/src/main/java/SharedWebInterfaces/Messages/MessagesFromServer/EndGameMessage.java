package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.util.HashMap;

public class EndGameMessage implements MessageFromServer{

    private HashMap<String, Integer> finalPoints;

    public EndGameMessage(HashMap<String, Integer> finalPoints) {
        this.finalPoints = finalPoints;
    }

    /**
     * Notifies the receiver of the end of the game
     * @param view the view interface of the receiver
     */
    @Override
    public void execute(ViewAPI_Interface view) {
        view.setFinalPoints(finalPoints);
    }
}
