package SharedWebInterfaces.Messages.MessagesFromServer.Errors;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;

/**
 * The type Cant place card message.
 * This message is sent to the client when the server receives a request to place a card in a position that is not allowed
 * or from a player that should not place a card there.
 */
public class CantPlaceCardMessage implements MessageFromServer {
    /**
     * The player sending the request to place a card.
     */
    private String sender;
    /**
     * The Card that the player tried to place.
     */
    private PlayableCard card;
    /**
     * The coordinates where the player tried to place the card.
     */
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
