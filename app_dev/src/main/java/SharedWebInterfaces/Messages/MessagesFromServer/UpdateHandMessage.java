package SharedWebInterfaces.Messages.MessagesFromServer;


import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.PlayableCards.PlayableCard;

import java.util.List;

public class UpdateHandMessage implements MessageFromServer{

    private List<PlayableCard> hand;
    public UpdateHandMessage(List<PlayableCard> hand){
        this.hand = hand;
    }
    @Override
    public void execute(ViewAPI_Interface view) {

    }
}
