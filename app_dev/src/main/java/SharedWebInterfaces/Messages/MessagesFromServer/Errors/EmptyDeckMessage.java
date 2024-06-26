package SharedWebInterfaces.Messages.MessagesFromServer.Errors;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

/**
 * The type Empty deck message.
 * This message is sent to the client when the deck is empty and the player tries to draw a card.
 */
public class EmptyDeckMessage implements MessageFromServer {
    /**
     * The Source from which the player tried to draw the card.
     */
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
