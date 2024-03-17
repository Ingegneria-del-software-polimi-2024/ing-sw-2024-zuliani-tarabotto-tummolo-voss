package main.java.model.deckFactory;
import main.java.model.cards.Card;
import main.java.model.cards.GoldCard;
import main.java.model.cards.ResourceCard;

import java.util.ArrayList;


public class ResourcesDeck extends Deck {

    private final int numCards = 40;
    public ResourcesDeck deckCreation() {
        for (int i = 1; i <= numCards; i++) {
            cards.add(new ResourceCard( ( char)i ) );
        }
        return this;
    }
}

