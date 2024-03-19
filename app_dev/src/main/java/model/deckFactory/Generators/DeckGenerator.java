package model.deckFactory.Generators;

import model.deckFactory.Deck;


public abstract class DeckGenerator {

    public Deck generateDeck(){
        Deck deck = generateDeckBasedOnType();
        deck.generate();
        return deck;
    }

    public abstract Deck generateDeckBasedOnType();



}
