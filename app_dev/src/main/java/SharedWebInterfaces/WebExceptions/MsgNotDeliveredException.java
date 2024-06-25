package SharedWebInterfaces.WebExceptions;

import SharedWebInterfaces.Messages.Message;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;

/**
 * The type Msg not delivered exception.
 */
public class MsgNotDeliveredException extends Exception {
    /**
     * The Msg.
     */
    Message msg;

    /**
     * Instantiates a new Msg not delivered exception.
     *
     * @param msg the msg
     */
    public MsgNotDeliveredException(Message msg){
        this.msg = msg;
    }
}
