package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.Messages.GeneralAPI_Interface;
import SharedWebInterfaces.Messages.ViewAPI_Interface;

public class StarterCardMessage implements MessageFromServer{
    private int starterCard;
    public StarterCardMessage(int starterCard){
        this.starterCard = starterCard;
    }

    //TODO: ELIMINATE
    @Override
    public void execute(GeneralAPI_Interface api) {

    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.setStarterCard(starterCard);
    }
}
