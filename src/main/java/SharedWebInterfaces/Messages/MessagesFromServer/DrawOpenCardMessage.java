package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;

import java.util.List;

/**
 * The type Draw open card message.
 * This message is sent from the server to the clients to inform the client that a card has been drawn from the open cards on the table,
 * and thus it updates that card.
 * Note that if an open card is updated also the corresponding deck is updated because the first card of the deck is drawn and put in the open cards area.
 */
public class DrawOpenCardMessage implements MessageFromServer{
    /**
     * The deck from which the first card is drawn.
     */
    private List<PlayableCard> deck;
    /**
     * The source of the card.
     */
    private int cardSource;
    /**
     * The index of the open card.
     */
    int index;
    @Override
    public void execute(ViewAPI_Interface view) {
        view.updateOpenCards(deck, cardSource);
    }

    /**
     * Instantiates a new Draw open card message.
     *
     * @param deck       the deck
     * @param cardSource the card source
     * @param index      the index of the open card
     */
    public DrawOpenCardMessage(List<PlayableCard> deck, int cardSource, int index) {
        this.deck = deck;
        this.cardSource = cardSource;
        this.index = index;
    }
}
