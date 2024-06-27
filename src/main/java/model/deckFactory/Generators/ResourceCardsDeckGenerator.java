package model.deckFactory.Generators;

import model.deckFactory.Deck;
import model.deckFactory.Generators.DeckGenerator;
import model.deckFactory.PlayableDeck;
import model.deckFactory.ResourcesDeck;

/**
 * Class ResourceCardsDeckGenerator is responsible for generating a deck of resource cards.
 * It extends PlayableDeckGenerator and provides an implementation for generating a ResourcesDeck.
 */
public class ResourceCardsDeckGenerator extends PlayableDeckGenerator {

    /**
     * Generates a new instance of ResourcesDeck.
     * This method overrides the generateDeckBasedOnType method in PlayableDeckGenerator
     * to specify the type of deck to be generated as a ResourcesDeck.
     *
     * @return a new instance of ResourcesDeck.
     */
    @Override
    public PlayableDeck generateDeckBasedOnType(){
        return new ResourcesDeck();
    }
}