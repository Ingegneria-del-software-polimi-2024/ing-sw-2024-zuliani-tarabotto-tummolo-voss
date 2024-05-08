package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;

import java.util.List;

public class DrawCardMessage implements MessageFromServer{

    private List<PlayableCard> deck;
    private int cardSource;

    public DrawCardMessage(List<PlayableCard> deck, int cardSource) {
        this.deck = deck;
        this.cardSource = cardSource;
    }

    @Override
    public void execute(ViewAPI_Interface view)  {
        view.updateCardSource(deck, cardSource);
    }

}
