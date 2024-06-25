package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;

import java.util.List;

/**
 * The type Draw open card message.
 */
public class DrawOpenCardMessage implements MessageFromServer{
    private List<PlayableCard> deck;
    private int cardSource;
    private int index;

    @Override
    public void execute(ViewAPI_Interface view) {
        view.updateOpenCards(deck, cardSource);
    }

    /**
     * Instantiates a new Draw open card message.
     *
     * @param deck       the deck
     * @param cardSource the card source
     * @param index      the index
     */
    public DrawOpenCardMessage(List<PlayableCard> deck, int cardSource, int index) {
        this.deck = deck;
        this.cardSource = cardSource;
        this.index = index;
    }
}
