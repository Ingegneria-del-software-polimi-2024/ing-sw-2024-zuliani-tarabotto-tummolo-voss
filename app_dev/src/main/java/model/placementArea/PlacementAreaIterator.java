package model.placementArea;

import model.cards.Card;
import model.cards.PlayableCards.PlayableCard;
import model.objective.Shape;

import java.util.*;
import java.util.stream.Collectors;
/*
the placement area iterator allows to iterate on the cards placed on the table in different order depending on the
objective we are going to check
 */
public class PlacementAreaIterator implements Iterator<Coordinates> {
    private Coordinates currentPlace;
    private List<Coordinates> availableCoordinates;
    @Override
    public boolean hasNext() {
        try{
            availableCoordinates.get(availableCoordinates.indexOf(currentPlace)+1);
            return true;
        }catch(IndexOutOfBoundsException ex){return false;}
    }

    @Override
    public Coordinates next(){
        currentPlace = availableCoordinates.get(availableCoordinates.indexOf(currentPlace)+1);
        return currentPlace;
    }

    public Coordinates current() {return currentPlace;}

//Constructor
    public PlacementAreaIterator(HashMap<Coordinates, PlayableCard> disp, Shape shape){
        switch (shape){
            case TOPRIGHTL:
                currentPlace = disp.keySet().stream().reduce(new Coordinates(0,0), Coordinates::topRightFirst);
                availableCoordinates = new ArrayList<Coordinates>(disp.keySet());
                availableCoordinates.sort((a, b) -> a.topRightFirst(b).equals(a)? 1 : -1);
            case TOPLEFTL:
                currentPlace = disp.keySet().stream().reduce(new Coordinates(0,0), Coordinates::topLeftFirst);
                availableCoordinates = new ArrayList<Coordinates>(disp.keySet());
                availableCoordinates.sort((a, b) -> a.topLeftFirst(b).equals(a)? 1 : -1);
            case BOTTOMLEFTL:
                currentPlace = disp.keySet().stream().reduce(new Coordinates(0,0), Coordinates::bottomLeftFirst);
                availableCoordinates = new ArrayList<Coordinates>(disp.keySet());
                availableCoordinates.sort((a, b) -> a.bottomLeftFirst(b).equals(a)? 1 : -1);
            case BOTTOMRIGHTL:
                currentPlace = disp.keySet().stream().reduce(new Coordinates(0,0), Coordinates::bottomRightFirst);
                availableCoordinates = new ArrayList<Coordinates>(disp.keySet());
                availableCoordinates.sort((a, b) -> a.bottomRightFirst(b).equals(a)? 1 : -1);
            case ASCENDINGDIAGONAL:
                currentPlace = disp.keySet().stream().reduce(new Coordinates(0,0), Coordinates::topRightFirst);
                availableCoordinates = new ArrayList<Coordinates>(disp.keySet());
                availableCoordinates.sort((a, b) -> a.topRightFirst(b).equals(a)? 1 : -1);
            case DESCENDINGDIAGONAL:
                currentPlace = disp.keySet().stream().reduce(new Coordinates(0,0), Coordinates::bottomRightFirst);
                availableCoordinates = new ArrayList<Coordinates>(disp.keySet());
                availableCoordinates.sort((a, b) -> a.bottomRightFirst(b).equals(a)? 1 : -1);
        }
    }

}
