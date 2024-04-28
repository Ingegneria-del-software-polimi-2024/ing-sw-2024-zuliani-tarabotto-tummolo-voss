package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.ViewAPI_Interface;
import model.placementArea.Coordinates;

import java.util.ArrayList;

public class PlaceableCardsMessage implements MessageFromServer{

    private ArrayList<Coordinates> availablePlaces;
    private boolean[] canBePlaced;

    public PlaceableCardsMessage(ArrayList<Coordinates> availablePlaces, boolean[] canBePlaced){
        this.availablePlaces = availablePlaces;
        this.canBePlaced = canBePlaced;
    }

    @Override
    public void execute(ViewAPI_Interface view) {

    }
}
