package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;

import java.util.List;

/**
 * The type Draw deck card message.
 * This message is sent by the server to the clients to notify the drawing of a card from a specified source and thus to update that source.
 */
public class DrawDeckCardMessage implements MessageFromServer{

    /**
     * The new deck.
     * This deck will be substituted to the old one in the receiver's model.
     */
    private List<PlayableCard> deck;
    /**
     * The source of the card.
     * This is the index of the source in the receiver's model.
     */
    private int cardSource;

    /**
     * Instantiates a new Draw deck card message.
     *
     * @param deck       the deck
     * @param cardSource the card source
     */
    public DrawDeckCardMessage(List<PlayableCard> deck, int cardSource) {
        this.deck = deck;
        this.cardSource = cardSource;
    }

    /**
     * The receiver is notified of the drawing of a card from a specified source.
     * The new state of the source and of the deck are contained.
     * @param view the view interface of the receiver
     */
    @Override
    public void execute(ViewAPI_Interface view)  {
        view.updateCardSource(deck, cardSource);
    }

}