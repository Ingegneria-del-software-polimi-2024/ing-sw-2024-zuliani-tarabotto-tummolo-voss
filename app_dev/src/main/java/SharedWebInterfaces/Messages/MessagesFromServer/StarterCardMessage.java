package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Pawn;

public class StarterCardMessage implements MessageFromServer{
    private PlayableCard starterCard;
    private String pawnColor;
    private String player;
    public StarterCardMessage(String player, PlayableCard starterCard, String pawnColor){
        this.player = player;
        this.starterCard = starterCard;
        this.pawnColor = pawnColor;
    }

    /**
     * Sets the starter card and the pawn color of the receiver
     * @param view the view interface of the receiver
     */
    @Override
    public void execute(ViewAPI_Interface view) {
        view.setStarterCard(starterCard);
        //view.setPawnColor(player, pawnColor);
    }
}
