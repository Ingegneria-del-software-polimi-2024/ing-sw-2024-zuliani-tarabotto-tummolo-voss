package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

public class TurnPlayerMessage implements MessageFromServer{

    private String turnPlayer;

    public TurnPlayerMessage(String turnPlayer){
        this.turnPlayer = turnPlayer;
    }
    @Override
    public void execute(ViewAPI_Interface view) {
        view.setTurnPlayer(turnPlayer);
    }
}
