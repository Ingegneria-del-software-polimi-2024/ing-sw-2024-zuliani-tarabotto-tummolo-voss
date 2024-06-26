package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.Messages.Message;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.io.Serializable;

/**
 * The interface Message from server.
 * This interface is used to define the methods that a message from the server must implement.
 * All the messages exchanged after the start of the game are considered messages from server
 */
public interface MessageFromServer extends Serializable, Message {
    /**
     * Executes the message using the interface of the client.
     *
     * @param view the view
     */
    public abstract void execute(ViewAPI_Interface view);

}
