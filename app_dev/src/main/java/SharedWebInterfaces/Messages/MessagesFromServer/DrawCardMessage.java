package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.Messages.ViewAPI_Interface;

public class DrawCardMessage implements MessageFromServer{
    private String player;
    private int[] hand;
    private int cardSource;

    public DrawCardMessage(String player, int[] hand, int cardSource) {
        this.player = player;
        this.hand = hand;
        this.cardSource = cardSource;
    }

    @Override
    public void execute(ViewAPI_Interface view)  {
        view.updateHand(player, hand);
        view.updateCardSource(cardSource);
    }

}
