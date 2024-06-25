package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;

import java.util.List;

/**
 * The type Draw deck card message.
 */
public class DrawDeckCardMessage implements MessageFromServer{

    private List<PlayableCard> deck;
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