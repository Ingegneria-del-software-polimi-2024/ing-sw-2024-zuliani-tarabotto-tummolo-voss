package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Turn player message.
 */
public class TurnPlayerMessage implements MessageFromServer{

    private String turnPlayer;

    /**
     * Instantiates a new Turn player message.
     *
     * @param turnPlayer the turn player
     */
    public TurnPlayerMessage(String turnPlayer){
        this.turnPlayer = turnPlayer;
    }

    /**
     * Changes the turn player of the receiver
     * @param view the view interface of the receiver
     */
    @Override
    public void execute(ViewAPI_Interface view) {
        view.setTurnPlayer(turnPlayer);
    }
}
