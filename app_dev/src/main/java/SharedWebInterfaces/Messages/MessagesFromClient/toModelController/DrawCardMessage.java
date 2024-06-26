package SharedWebInterfaces.Messages.MessagesFromClient.toModelController;

import Server.ModelTranslator;
import SharedWebInterfaces.SharedInterfaces.Traslator;

/**
 * The type Draw card message.
 * This message is sent from the view to the model controller to draw a card from the deck
 */
public class DrawCardMessage implements MessageFromViewToModelController {

    /**
     * The Card source to draw from.
     */
    private int cardSource;

    /**
     * Instantiates a new Draw card message.
     *
     * @param cardSource the card source
     */
    public DrawCardMessage(int cardSource) {
        this.cardSource = cardSource;
    }

    @Override
    public void execute(ModelTranslator controller) {
        controller.drawCard(cardSource);
    }

    //ignore this
    @Override
    public void execute(Traslator controller) {

    }
}
