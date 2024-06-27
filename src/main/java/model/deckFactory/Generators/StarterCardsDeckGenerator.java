package model.deckFactory.Generators;

import model.deckFactory.Deck;
import model.deckFactory.Generators.DeckGenerator;
import model.deckFactory.PlayableDeck;
import model.deckFactory.StarterDeck;

/**
 * Class StarterCardsDeckGenerator is responsible for generating a deck of starter cards.
 * It extends PlayableDeckGenerator and provides an implementation for generating a StarterDeck.
 */
public class StarterCardsDeckGenerator extends PlayableDeckGenerator {

    /**
     * Generates a new instance of StarterDeck.
     * This method overrides the generateDeckBasedOnType method in PlayableDeckGenerator
     * to specify the type of deck to be generated as a StarterDeck.
     *
     * @return a new instance of StarterDeck.
     */
    @Override
    public PlayableDeck generateDeckBasedOnType() {
        return new StarterDeck();
    }
}
