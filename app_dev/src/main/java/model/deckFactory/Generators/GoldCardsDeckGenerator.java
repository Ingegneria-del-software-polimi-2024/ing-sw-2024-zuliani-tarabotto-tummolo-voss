package model.deckFactory.Generators;


import model.deckFactory.Deck;
import model.deckFactory.GoldenDeck;

public class GoldCardsDeckGenerator extends DeckGenerator {
    @Override
    public Deck generateDeckBasedOnType(){
        return new GoldenDeck();
    }
}
