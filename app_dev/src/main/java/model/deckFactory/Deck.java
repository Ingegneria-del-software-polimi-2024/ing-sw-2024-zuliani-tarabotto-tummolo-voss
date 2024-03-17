package main.java.model.deckFactory;
import main.java.model.cards.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collections;

// Abstract class defining common deck properties and functionalities
public abstract class Deck {
    ArrayList<Card> cards; // Array to store cards

    public void shuffleCards() {
        Collections.shuffle(cards); //java library function to shuffle a collection
    }

    public Card drawCards() {
        return cards.removeFirst();
    }

    public abstract Deck deckCreation();

}
