package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Interrupt connection message.
 * This message is sent from the server to the client to notify the client that the connection can be interrupted.
 */
public class InterruptConnectionMessage implements MessageFromServer{
    @Override
    public void execute(ViewAPI_Interface view) {}
}
