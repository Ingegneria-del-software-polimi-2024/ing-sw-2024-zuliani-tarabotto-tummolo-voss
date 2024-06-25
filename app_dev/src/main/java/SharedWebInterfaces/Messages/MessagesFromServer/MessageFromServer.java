package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.Messages.Message;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.io.Serializable;

/**
 * The interface Message from server.
 */
public interface MessageFromServer extends Serializable, Message {
    /**
     * Execute.
     *
     * @param view the view
     */
    public abstract void execute(ViewAPI_Interface view);

}
