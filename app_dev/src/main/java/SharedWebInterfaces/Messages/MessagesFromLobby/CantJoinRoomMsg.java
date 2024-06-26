package SharedWebInterfaces.Messages.MessagesFromLobby;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Cant join room msg.
 * This class is used to display a message to the user when he can't join a room
 */
public class CantJoinRoomMsg implements MessageFromServer {
    /**
     * The Creating boolean.
     * True if we can't create a room, false if we can't join an already existing room.
     */
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
