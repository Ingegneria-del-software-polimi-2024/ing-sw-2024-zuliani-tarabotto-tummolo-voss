package SharedWebInterfaces.Messages.MessagesFromServer;


import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;

import java.util.List;

/**
 * The type Update hand message.
 */
public class UpdateHandMessage implements MessageFromServer{

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
