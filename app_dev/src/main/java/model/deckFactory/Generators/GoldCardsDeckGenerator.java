package model.deckFactory.Generators;


import model.deckFactory.Deck;
import model.deckFactory.GoldenDeck;
import model.deckFactory.PlayableDeck;

public class GoldCardsDeckGenerator extends PlayableDeckGenerator {
    @Override
    public PlayableDeck generateDeckBasedOnType(){
        return new GoldenDeck();
    }
}
