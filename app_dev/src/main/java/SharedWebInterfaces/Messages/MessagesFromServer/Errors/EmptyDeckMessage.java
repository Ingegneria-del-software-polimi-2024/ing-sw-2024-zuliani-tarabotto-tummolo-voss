package SharedWebInterfaces.Messages.MessagesFromServer.Errors;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Empty deck message.
 */
public class EmptyDeckMessage implements MessageFromServer {
    private int source;
    @Override
    public void execute(ViewAPI_Interface view) {
        view.cantDrawCard(source);
    }

    /**
     * Instantiates a new Empty deck message.
     *
     * @param source the source
     */
    public EmptyDeckMessage(int source) {
        this.source = source;
    }
}
