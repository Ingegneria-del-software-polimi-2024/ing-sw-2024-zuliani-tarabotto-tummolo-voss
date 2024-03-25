package model.deckFactory.Generators;

import model.deckFactory.Deck;

import model.deckFactory.ObjectiveDeck;

public class ObjectiveCardsDeckGenerator implements DeckGenerator {

    //public Deck generateDeckBasedOnType(){
        //return new ObjectiveDeck();
    //}

    public ObjectiveDeck generateDeck() {
        ObjectiveDeck deck = new ObjectiveDeck();
        deck.generate();
        return deck;
    }
}
