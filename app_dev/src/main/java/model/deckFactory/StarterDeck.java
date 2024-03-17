package main.java.model.deckFactory;

import main.java.model.cards.GoldCard;
import main.java.model.cards.StarterCard;

public class StarterDeck extends Deck {

    private final int numCards = 6;
    public StarterDeck deckCreation() {
        for (int i = 81; i < numCards + 81; i++) {
            cards.add(new StarterCard( ( char)i ) );
        }
        return this;
    }
}
