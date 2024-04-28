package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.Messages.GeneralAPI_Interface;
import SharedWebInterfaces.Messages.ViewAPI_Interface;

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
