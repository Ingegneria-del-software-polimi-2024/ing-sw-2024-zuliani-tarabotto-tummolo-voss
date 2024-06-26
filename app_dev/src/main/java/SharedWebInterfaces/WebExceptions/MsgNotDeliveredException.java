package SharedWebInterfaces.WebExceptions;

import SharedWebInterfaces.Messages.Message;
import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;

public class MsgNotDeliveredException extends Exception {
    Message msg;

    public MsgNotDeliveredException(Message msg){
        this.msg = msg;
    }
}
