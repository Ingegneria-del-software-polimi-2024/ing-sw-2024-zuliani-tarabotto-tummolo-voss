package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.GameState.TurnState;

public class StateMessage implements MessageFromServer{

    private TurnState state;

    public StateMessage(TurnState state) {
        this.state = state;
    }

    @Override
    public void execute(ViewAPI_Interface viewApi){
        viewApi.setState(state);
    }
}
