package main.java.model;

import main.java.model.deckFactory.Deck;
import main.java.model.deckFactory.DeckGenerator;
import main.java.model.deckFactory.ResourceCardsDeckGenerator;
import main.java.model.deckFactory.StarterCardsDeckGenerator;
import main.java.model.model.deckFactory.Deck;
import main.java.model.model.deckFactory.DeckGenerator;
import main.java.model.model.deckFactory.ResourceCardsDeckGenerator;
import main.java.model.model.deckFactory.StarterCardsDeckGenerator;

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
