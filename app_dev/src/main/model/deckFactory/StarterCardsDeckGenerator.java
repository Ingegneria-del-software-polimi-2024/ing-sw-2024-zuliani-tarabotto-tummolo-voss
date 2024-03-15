package main.model.deckFactory;

public class StarterCardsDeckGenerator extends DeckGenerator {
    @Override
    public Deck generateDeckBasedOnType() {
        return new StarterDeck();
    }
}
