package model.deckFactory.Generators;

import model.deckFactory.Deck;

import model.deckFactory.ObjectiveDeck;

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
