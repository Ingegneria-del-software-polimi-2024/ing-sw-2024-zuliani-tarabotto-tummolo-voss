package main.model;

import main.model.deckFactory.Deck;
import main.model.deckFactory.DeckGenerator;
import main.model.deckFactory.ResourceCardsDeckGenerator;
import main.model.deckFactory.StarterCardsDeckGenerator;
import main.model.model.deckFactory.Deck;
import main.model.model.deckFactory.DeckGenerator;
import main.model.model.deckFactory.ResourceCardsDeckGenerator;
import main.model.model.deckFactory.StarterCardsDeckGenerator;

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
