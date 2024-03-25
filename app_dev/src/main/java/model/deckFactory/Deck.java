package model.deckFactory;
import model.cards.*;

import java.util.Collections;
import java.util.List;

// Abstract class defining common deck properties and functionalities
public interface Deck {

    /*protected List<Card> cards; // List to store cards

    public abstract void generate();

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card extract() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty!");
        }
        return cards.remove(0); // Extract and remove the first card (head/beginning of list)
    }

    public void addCard(Card card) {
        cards.add(card); // Add card to the back (tail/end of list)
    }

    public int getSize() {
        return cards.size();
    }
    public boolean checkDeck() {
        return cards.isEmpty();
    }


    }*/
    public abstract void generate();
    public abstract void shuffle();
    public abstract int getSize();
}

