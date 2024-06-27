package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.placementArea.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Placeable cards message.
 * This message is used to notify the client of the available places where a card can be placed and of the
 * cards of their hand that can be placed face side.
 */
public class PlaceableCardsMessage implements MessageFromServer{

    /**
     * The Available places of the player's disposition.
     */
    private List<Coordinates> availablePlaces;
    /**
     * The boolean array that indicates if a card in the player's hand can be placed face side.
     */
    private boolean[] canBePlaced;

    /**
     * Instantiates a new Placeable cards message.
     *
     * @param availablePlaces the available places
     * @param canBePlaced     the can be placed
     */
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
