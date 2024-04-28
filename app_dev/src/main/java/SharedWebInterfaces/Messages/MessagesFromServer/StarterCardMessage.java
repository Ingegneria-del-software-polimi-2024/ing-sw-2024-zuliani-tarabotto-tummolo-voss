package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.Messages.GeneralAPI_Interface;
import SharedWebInterfaces.ViewAPI_Interface;

public class StarterCardMessage implements MessageFromServer{
    private int starterCard;
    public StarterCardMessage(int starterCard){
        this.starterCard = starterCard;
    }
    @Override
    public void execute(ViewAPI_Interface view) {
        view.setStarterCard(starterCard);
    }
}
