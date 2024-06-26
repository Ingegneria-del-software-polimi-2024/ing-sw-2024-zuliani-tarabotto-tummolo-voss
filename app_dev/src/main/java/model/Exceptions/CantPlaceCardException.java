package model.Exceptions;

import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;

public class CantPlaceCardException extends Exception{
    Coordinates coord;
    PlayableCard card =  null;

    public CantPlaceCardException(Coordinates coord, PlayableCard card) {
        this.coord = coord;
        this.card = card;
    }
    public CantPlaceCardException(Coordinates coord){
        this.coord = coord;
    }

    public Coordinates getCoord() {
        return coord;
    }

    public PlayableCard getCard() {
        return card;
    }
}
