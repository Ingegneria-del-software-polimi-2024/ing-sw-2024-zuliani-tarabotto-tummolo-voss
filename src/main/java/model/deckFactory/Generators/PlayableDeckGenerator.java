package model.deckFactory.Generators;

import model.deckFactory.Deck;
import model.deckFactory.PlayableDeck;

/**
 * Abstract class PlayableDeckGenerator provides a blueprint for generating playable decks.
 * It implements the DeckGenerator interface and includes methods for generating decks
 * and initializing cards via JSON parsing.
 */
public abstract class PlayableDeckGenerator implements DeckGenerator {

    /**
     * calls the type specific generateDeckBasedOnType method
     * and the Deck.generate() method which initializes the cards via the json parsing
     *
     * @return deck PlayableDeck
     */
    public PlayableDeck generateDeck(){
            PlayableDeck deck = generateDeckBasedOnType();
            deck.generate();
            return deck;
        }

    /**
     * creates new empty Deck of the expected type
     *
     * @return the playable deck
     */
    public abstract PlayableDeck generateDeckBasedOnType();


}
