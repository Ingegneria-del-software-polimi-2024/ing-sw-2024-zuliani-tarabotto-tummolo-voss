package model.deckFactory.Generators;

import model.deckFactory.Deck;

import model.deckFactory.ObjectiveDeck;

public class ObjectiveCardsDeckGenerator extends DeckGenerator{
    @Override
    public Deck generateDeckBasedOnType(){
        return new ObjectiveDeck();
    }
}
