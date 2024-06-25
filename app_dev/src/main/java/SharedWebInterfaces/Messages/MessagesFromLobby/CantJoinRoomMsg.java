package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

public class CantJoinRoomMsg implements MessageFromServer {
    private boolean creating;
    @Override
    public void execute(ViewAPI_Interface view) {
        if (creating)
            view.cantCreateRoom();
        else
            view.cantJoinRoom();
    }

    public CantJoinRoomMsg(boolean creating) {
        this.creating = creating;
    }
}
