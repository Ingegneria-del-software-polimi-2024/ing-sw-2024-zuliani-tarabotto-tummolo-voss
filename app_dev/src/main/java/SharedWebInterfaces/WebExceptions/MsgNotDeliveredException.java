package SharedWebInterfaces.WebExceptions;

import SharedWebInterfaces.Messages.Message;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;

/**
 * The type Msg not delivered exception.
 * This exception is thrown when a message is not delivered to the server
 */
public class MsgNotDeliveredException extends Exception {
    /**
     * The message that was not delivered
     */
    Message msg;

    /**
     * Instantiates a new Msg not delivered exception.
     *
     * @param msg the msg that was not delivered
     */
    public MsgNotDeliveredException(Message msg){
        this.msg = msg;
    }
}
