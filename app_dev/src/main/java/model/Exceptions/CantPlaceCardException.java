package model.Exceptions;

import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;

/**
 * This exception is thrown when a card cannot be placed in the specified coordinates.
 */
public class CantPlaceCardException extends Exception{

    /**
     * The coordinates where the card cannot be placed.
     */
    Coordinates coord;
    /**
     * The card that cannot be placed.
     */
    PlayableCard card =  null;

    /**
     * Constructs a new CantPlaceCardException with the specified coordinates and card.
     * @param coord The coordinates where the card cannot be placed.
     * @param card The card that cannot be placed.
     */
    public CantPlaceCardException(Coordinates coord, PlayableCard card) {
        this.coord = coord;
        this.card = card;
    }

    /**
     * Constructs a new CantPlaceCardException with the specified coordinates.
     * @param coord The coordinates where the card cannot be placed.
     */
    public CantPlaceCardException(Coordinates coord){
        this.coord = coord;
    }

    /**
     * @return The coordinates where the card cannot be placed.
     */
    public Coordinates getCoord() {
        return coord;
    }

    /**
     * @return The card that cannot be placed.
     */
    public PlayableCard getCard() {
        return card;
    }
}
