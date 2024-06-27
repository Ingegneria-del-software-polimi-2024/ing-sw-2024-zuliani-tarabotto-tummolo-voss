package model.deckFactory.Generators;


import model.deckFactory.Deck;
import model.deckFactory.GoldenDeck;
import model.deckFactory.PlayableDeck;

/**
 * The type Gold cards deck generator.
 */
public class GoldCardsDeckGenerator extends PlayableDeckGenerator {
    @Override
    public PlayableDeck generateDeckBasedOnType(){
        return new GoldenDeck();
    }
}
