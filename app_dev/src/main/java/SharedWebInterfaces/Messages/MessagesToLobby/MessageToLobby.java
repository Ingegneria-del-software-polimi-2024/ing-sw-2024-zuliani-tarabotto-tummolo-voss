package SharedWebInterfaces.Messages.MessagesToLobby;


import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.Message;

import java.io.IOException;
import java.io.Serializable;

/**
 * The interface Message to lobby.
 */
public interface MessageToLobby extends Message, Serializable {
    /**
     * Execute.
     *
     * @param lobby the lobby
     */
    public abstract void execute(Lobby lobby);

    /**
     * Gets sender.
     *
     * @return the sender
     */
    public abstract String getSender();
}
