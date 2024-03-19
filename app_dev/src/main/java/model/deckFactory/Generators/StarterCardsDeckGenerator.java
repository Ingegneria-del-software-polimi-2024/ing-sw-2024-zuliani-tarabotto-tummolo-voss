package model.deckFactory.Generators;

import model.deckFactory.Deck;
import model.deckFactory.Generators.DeckGenerator;
import model.deckFactory.StarterDeck;

public class StarterCardsDeckGenerator extends DeckGenerator {
    @Override
    public Deck generateDeckBasedOnType(){
        return new StarterDeck();
    }
}