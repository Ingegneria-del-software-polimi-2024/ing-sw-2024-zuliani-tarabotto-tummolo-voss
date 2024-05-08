package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Pawn;

public class StarterCardMessage implements MessageFromServer{
    private PlayableCard starterCard;
    private String pawnColor;
    public StarterCardMessage(PlayableCard starterCard, String pawnColor){
        this.starterCard = starterCard;
        this.pawnColor = pawnColor;
    }
    @Override
    public void execute(ViewAPI_Interface view) {
        view.setStarterCard(starterCard);
        view.setPawnColor(pawnColor);
    }
}
