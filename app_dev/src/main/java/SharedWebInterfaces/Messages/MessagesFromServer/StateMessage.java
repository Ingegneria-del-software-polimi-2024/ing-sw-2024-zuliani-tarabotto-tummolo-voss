package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.GameState.TurnState;

/**
 * The type State message.
 * This message is used to notify the client of the new game state.
 */
public class StateMessage implements MessageFromServer{
    /**
     * The new game State.
     */
    private TurnState state;

    /**
     * Instantiates a new State message.
     *
     * @param state the state
     */
    public StateMessage(TurnState state) {
        this.state = state;
    }

    /**
     * sets a new game state on the receiver
     * @param viewApi the view interface of the receiver
     */
    @Override
    public void execute(ViewAPI_Interface viewApi){
        viewApi.setState(state);
    }
}
