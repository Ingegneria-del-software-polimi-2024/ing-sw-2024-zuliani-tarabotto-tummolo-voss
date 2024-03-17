package main.java.model;
import main.java.model.deckFactory.*;
import main.java.model.enums.DeckType;

public class Model {
    public static void main(String[] args) throws ClassNotFoundException {

        Deck gold = DeckGenerator.generate(DeckType.GOLD);


    }
}
