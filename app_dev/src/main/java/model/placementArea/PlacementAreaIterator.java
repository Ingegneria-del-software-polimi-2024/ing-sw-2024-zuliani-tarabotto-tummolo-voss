package model.placementArea;

import model.cards.Card;
import model.cards.PlayableCards.PlayableCard;
import model.objective.Shape;

import java.util.*;
import java.util.stream.Collectors;

/**
 * the placement area iterator allows to iterate on the cards placed on the table in different order depending on the
 * objective we are going to check
 */
public class PlacementAreaIterator implements Iterator<Coordinates> {
    private Coordinates currentPlace;
    private List<Coordinates> availableCoordinates;
    @Override
    /**
     * @return TRUE if there are still elements to iterate on
     */
    public boolean hasNext() {return availableCoordinates.indexOf(currentPlace) + 1 < availableCoordinates.size();}

    /**
     *
     * @return next element to iterate on
     */
    @Override
    public Coordinates next(){
        currentPlace = availableCoordinates.get(availableCoordinates.indexOf(currentPlace)+1);
        return currentPlace;
    }

    /**
     * Current coordinates.
     *
     * @return the current place when iterating
     */
    public Coordinates current() {return currentPlace;}

    /**
     * class constructor
     *
     * @param disp  the HashMap representing the PlacementArea
     * @param shape the type of shape can be L or diagonal
     */
    public PlacementAreaIterator(HashMap<Coordinates, PlayableCard> disp, Shape shape) {
        availableCoordinates = new ArrayList<Coordinates>(disp.keySet());
        switch(shape){
            case TOPRIGHTL:
                availableCoordinates.sort((a, b) -> a.topRightFirst(b).equals(a) ? -1 : 1);
                break;
            case TOPLEFTL:
                availableCoordinates.sort((a, b) -> a.topLeftFirst(b).equals(a) ? -1 : 1);
                break;
            case BOTTOMLEFTL:
                availableCoordinates.sort((a, b) -> a.bottomLeftFirst(b).equals(a) ? -1 : 1);
                break;
            case BOTTOMRIGHTL:
                availableCoordinates.sort((a, b) -> a.bottomRightFirst(b).equals(a) ? -1 : 1);
                break;
            case ASCENDINGDIAGONAL:
                availableCoordinates.sort((a, b) -> a.topRightFirst(b).equals(a) ? -1 : 1);
                break;
            case DESCENDINGDIAGONAL:

                availableCoordinates.sort((a, b) -> a.bottomRightFirst(b).equals(a) ? -1 : a.bottomRightFirst(b).equals(b)? 1 : 0);
                break;
        }
        currentPlace = availableCoordinates.get(0);

    }


}
