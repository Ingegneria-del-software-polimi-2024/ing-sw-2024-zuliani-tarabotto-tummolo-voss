package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.SharedInterfaces.Traslator;

/**
 * The type Play card message.
 */
public class PlayCardMessage implements MessageFromViewToModelController {

    private final int card;
    private final int x;
    private final int y;
    private final boolean faceSide;

    /**
     * Instantiates a new Play card message.
     *
     * @param card     the card
     * @param x        the x
     * @param y        the y
     * @param faceSide the face side
     */
    public PlayCardMessage(int card, int x, int y, boolean faceSide) {
        this.card = card;
        this.x = x;
        this.y = y;
        this.faceSide = faceSide;
    }


    @Override
    public void execute(ModelTranslator controller) {
        controller.playCard(card, x, y, faceSide);
    }

    //ignore this
    @Override
    public void execute(Traslator controller) {

    }
}
