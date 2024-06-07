package SharedWebInterfaces.Messages.MessagesFromServer.Errors;

import SharedWebInterfaces.Messages.MessagesFromServer.MessageFromServer;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;

public class CantPlaceCardMessage implements MessageFromServer {
    private String sender;
    private PlayableCard card;
    private Coordinates coord;

    @Override
    public void execute(ViewAPI_Interface view) {
        view.cantPlaceACard(card, coord);
    }

    public CantPlaceCardMessage(String sender, PlayableCard card, Coordinates coord) {
        this.sender = sender;
        this.card = card;
        this.coord = coord;
    }
}
