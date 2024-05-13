package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

public class TurnPlayerMessage implements MessageFromServer{

    private String turnPlayer;

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
