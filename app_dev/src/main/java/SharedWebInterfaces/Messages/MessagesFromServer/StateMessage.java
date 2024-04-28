package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.ViewAPI_Interface;

public class StateMessage implements MessageFromServer{

    private String state;

    public StateMessage(String state) {
        this.state = state;
    }

    @Override
    public void execute(ViewAPI_Interface viewApi){
        viewApi.setState(state);
    }
}
