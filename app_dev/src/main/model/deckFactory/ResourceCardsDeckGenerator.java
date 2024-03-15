package main.model.deckFactory;

public class ResourceCardsDeckGenerator extends DeckGenerator {
    @Override
    public Deck generateDeckBasedOnType() {
        return new ResourcesDeck();
    }
}
