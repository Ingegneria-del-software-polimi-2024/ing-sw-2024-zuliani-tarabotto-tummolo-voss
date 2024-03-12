package model;

import model.deckFactory.Deck;
import model.deckFactory.DeckGenerator;
import model.deckFactory.ResourceCardsDeckGenerator;
import model.deckFactory.StarterCardsDeckGenerator;

public class Model {
    public static void main(String[] args){

        DeckGenerator goldenDeckGenerator = new GoldCardsDeckGenerator();
        DeckGenerator resourcesDeckGenerator = new ResourceCardsDeckGenerator();
        DeckGenerator starterDeckGenerator = new StarterCardsDeckGenerator();

        Deck goldenDeck = goldenDeckGenerator.generateDeck();
        Deck resourcesDeck = resourcesDeckGenerator.generateDeck();
        Deck starterDeck = starterDeckGenerator.generateDeck();

    }
}
