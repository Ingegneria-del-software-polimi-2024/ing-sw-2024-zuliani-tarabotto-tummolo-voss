package main.java.model.deckFactory;

public class GoldCardsDeckGenerator {

    public Deck generateDeckBasedOnType() {
        return new GoldenDeck().deckCreation();
    }
}
