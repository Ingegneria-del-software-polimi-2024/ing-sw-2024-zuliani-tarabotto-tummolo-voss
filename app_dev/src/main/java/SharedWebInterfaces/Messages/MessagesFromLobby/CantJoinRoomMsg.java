package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Cant join room msg.
 */
public class CantJoinRoomMsg implements MessageFromServer {
    private boolean creating;
    @Override
    public void execute(ViewAPI_Interface view) {
        if (creating)
            view.cantCreateRoom();
        else
            view.cantJoinRoom();
    }

    /**
     * Instantiates a new Cant join room msg.
     *
     * @param creating the creating
     */
    public CantJoinRoomMsg(boolean creating) {
        this.creating = creating;
    }
}
