package SharedWebInterfaces.Messages.MessagesFromServer.Errors;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;

/**
 * The type Cant place card message.
 */
public class CantPlaceCardMessage implements MessageFromServer {
    private String sender;
    private PlayableCard card;
    private Coordinates coord;

    @Override
    public void execute(ViewAPI_Interface view) {
        view.cantPlaceACard(card, coord);
    }

    /**
     * Instantiates a new Cant place card message.
     *
     * @param sender the sender
     * @param card   the card
     * @param coord  the coord
     */
    public CantPlaceCardMessage(String sender, PlayableCard card, Coordinates coord) {
        this.sender = sender;
        this.card = card;
        this.coord = coord;
    }
}
