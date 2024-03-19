package model.deckFactory.Generators;

import model.deckFactory.Deck;
import model.deckFactory.Generators.DeckGenerator;
import model.deckFactory.ResourcesDeck;

public class ResourceCardsDeckGenerator extends DeckGenerator {
    @Override
    public Deck generateDeckBasedOnType(){
        return new ResourcesDeck();
    }
}