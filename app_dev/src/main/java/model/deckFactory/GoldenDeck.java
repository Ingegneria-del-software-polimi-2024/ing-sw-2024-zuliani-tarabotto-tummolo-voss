package main.java.model.deckFactory;
import main.java.model.cards.Card;
import main.java.model.cards.GoldCard;
import java.util.ArrayList;


public class GoldenDeck extends Deck {
    private  final int numCards = 40;

    @Override
    public GoldenDeck deckCreation() {
        for (int i = 41; i < 41 + numCards; i++) {
            cards.add(new GoldCard( ( char)i ) );
        }
        return this;
    }

    public void print() {System.out.println("i'm a golden deck");}
}
