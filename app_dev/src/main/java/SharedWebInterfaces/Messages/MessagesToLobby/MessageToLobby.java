package SharedWebInterfaces.Messages.MessagesToLobby;


import Server.Web.Lobby.Lobby;
import SharedWebInterfaces.Messages.Message;

import java.io.IOException;
import java.io.Serializable;

/**
 * The interface Message to lobby.
 * This interface is used to send messages to the lobby.
 */
public interface MessageToLobby extends Message, Serializable {
    /**
     * Executes the message using the class Lobby, a controller for the infrastructure of the rooms.
     *
     * @param lobby the lobby
     */
    public abstract void execute(Lobby lobby);

    /**
     * Gets the sender of the message.
     *
     * @return the sender
     */
    public abstract String getSender();
}
