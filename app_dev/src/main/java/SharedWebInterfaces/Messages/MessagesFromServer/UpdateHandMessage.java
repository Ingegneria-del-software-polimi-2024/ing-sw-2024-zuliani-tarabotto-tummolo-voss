package SharedWebInterfaces.Messages.MessagesFromServer;


import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;

import java.util.List;

/**
 * The type Update hand message.
 * This message is used to notify the client of the new hand of a player.
 * It is used after a player has drawn a card from the deck.
 */
public class UpdateHandMessage implements MessageFromServer{
    /**
     * The new hand of the player.
     */
    private List<PlayableCard> hand;

    /**
     * Instantiates a new Update hand message.
     *
     * @param hand the hand
     */
    public UpdateHandMessage(List<PlayableCard> hand){
        this.hand = hand;
    }

    /**
     * Sets a new hand on the client that receives the message
     * @param view the view interface of the receiver
     */
    @Override
    public void execute(ViewAPI_Interface view) {
        view.setHand(hand);
    }
}
