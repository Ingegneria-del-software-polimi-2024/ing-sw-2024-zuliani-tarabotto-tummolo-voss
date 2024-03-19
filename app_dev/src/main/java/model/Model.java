package model;

import model.deckFactory.*;
import model.deckFactory.Generators.DeckGenerator;
import model.deckFactory.Generators.GoldCardsDeckGenerator;
import model.deckFactory.Generators.ResourceCardsDeckGenerator;
import model.deckFactory.Generators.StarterCardsDeckGenerator;

public class Model {
    public static void main(String[] args) throws Exception {
        RunDeckCreation runDeckCreation = new RunDeckCreation();
        runDeckCreation.RunDeckCreation();
    }
}
