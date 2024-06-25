package model.Exceptions;

import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;

/**
 * The type Cant place card exception.
 */
public class CantPlaceCardException extends Exception{
    /**
     * The Coord.
     */
    Coordinates coord;
    /**
     * The Card.
     */
    PlayableCard card =  null;

    /**
     * Instantiates a new Cant place card exception.
     *
     * @param coord the coord
     * @param card  the card
     */
    public CantPlaceCardException(Coordinates coord, PlayableCard card) {
        this.coord = coord;
        this.card = card;
    }

    /**
     * Instantiates a new Cant place card exception.
     *
     * @param coord the coord
     */
    public CantPlaceCardException(Coordinates coord){
        this.coord = coord;
    }

    /**
     * Gets coord.
     *
     * @return the coord
     */
    public Coordinates getCoord() {
        return coord;
    }

    /**
     * Gets card.
     *
     * @return the card
     */
    public PlayableCard getCard() {
        return card;
    }
}
