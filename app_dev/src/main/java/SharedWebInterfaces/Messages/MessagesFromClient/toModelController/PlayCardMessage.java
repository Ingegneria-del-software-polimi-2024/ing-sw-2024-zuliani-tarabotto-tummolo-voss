package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.SharedInterfaces.Traslator;

/**
 * The type Play card message.
 * This class is used to send a message to the model controller to play a card.
 * The message contains the card to play, the x and y coordinates of the card and if the card should be played face side up or down.
 */
public class PlayCardMessage implements MessageFromViewToModelController {

    /**
     * The Card to play.
     */
    private final int card;
    /**
     * The X coordinate of the card.
     */
    private final int x;
    /**
     * The Y coordinate of the card.
     */
    private final int y;
    /**
     * The Face side of the card, true when facing up else false.
     */
    private final boolean faceSide;

    /**
     * Instantiates a new Play card message.
     *
     * @param card     the card
     * @param x        the x
     * @param y        the y
     * @param faceSide the face side of the card
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
