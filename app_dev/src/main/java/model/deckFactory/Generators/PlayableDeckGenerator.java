package model.deckFactory.Generators;

import model.deckFactory.Deck;
import model.deckFactory.PlayableDeck;

public abstract class PlayableDeckGenerator implements DeckGenerator {

    /**
     * calls the type specific generateDeckBasedOnType method
     * and the Deck.generate() method which initializes the cards via the json parsing
     * @return deck PlayableDeck
     */
        public PlayableDeck generateDeck(){
            PlayableDeck deck = generateDeckBasedOnType();
            deck.generate();
            return deck;
        }

    /**
     * creates new empty Deck of the expected type
     */
    public abstract PlayableDeck generateDeckBasedOnType();


}
