package model.deckFactory.Generators;

import model.deckFactory.Deck;

import model.deckFactory.ObjectiveDeck;

/**
 * GoldCardsDeckGenerator is responsible for generating a deck of gold cards.
 * It extends PlayableDeckGenerator and overrides the method to generate a deck based on type.
 */
public class ObjectiveCardsDeckGenerator implements DeckGenerator {


    /**
     * creates a new ObjectiveDeck
     * and calls the generate() method which initializes the cards via json parsing
     * @return
     */
    public ObjectiveDeck generateDeck() {
        ObjectiveDeck deck = new ObjectiveDeck();
        deck.generate();
        return deck;
    }
}
