package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.placementArea.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class PlaceableCardsMessage implements MessageFromServer{

    private List<Coordinates> availablePlaces;
    private boolean[] canBePlaced;

    public PlaceableCardsMessage(List<Coordinates> availablePlaces, boolean[] canBePlaced){
        this.availablePlaces = availablePlaces;
        this.canBePlaced = canBePlaced;
    }

    /**
     * Notify of the correct placement of a card
     * @param view the view interface of the receiver
     */
    @Override
    public void execute(ViewAPI_Interface view) {
        view.setAvailablePlaces(availablePlaces);
        view.setCanBePlaced(canBePlaced);
    }
}
