package main.java.model.deckFactory;

public abstract class DeckGenerator {

    public Deck generateDeck() {
        Deck deck = generateDeckBasedOnType();
        deck.generate();
        return deck;
    }

    public abstract Deck generateDeckBasedOnType();


}

