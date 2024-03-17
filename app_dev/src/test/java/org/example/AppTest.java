package test.java.org.example;

import main.java.model.deckFactory.Deck;
import main.java.model.deckFactory.DeckGenerator;
import main.java.model.enums.DeckType;

public class AppTest {

    Deck gold = DeckGenerator.generate(DeckType.GOLD);

    public AppTest() throws ClassNotFoundException {
    }
}
