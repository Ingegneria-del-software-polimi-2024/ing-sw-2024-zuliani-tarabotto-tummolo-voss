package model.deckFactory.Generators;

import model.deckFactory.Deck;
import model.deckFactory.Generators.DeckGenerator;
import model.deckFactory.PlayableDeck;
import model.deckFactory.StarterDeck;

public class StarterCardsDeckGenerator extends PlayableDeckGenerator {
    @Override
    public PlayableDeck generateDeckBasedOnType(){
        return new StarterDeck();
    }
}