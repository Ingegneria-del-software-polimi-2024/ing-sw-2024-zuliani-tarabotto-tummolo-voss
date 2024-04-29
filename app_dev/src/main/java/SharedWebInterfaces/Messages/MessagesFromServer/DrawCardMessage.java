package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.ViewAPI_Interface;

public class DrawCardMessage implements MessageFromServer{
    private String player;
    private int lastDrawnCard;
    private int cardSource;

    public DrawCardMessage(String player, int lastDrawnCard, int cardSource) {
        this.player = player;
        this.lastDrawnCard = lastDrawnCard;
        this.cardSource = cardSource;
    }

    @Override
    public void execute(ViewAPI_Interface view)  {
        view.updateHand(player, lastDrawnCard);
        view.updateCardSource(cardSource);
    }

}
