package model.deckFactory;
import model.cards.*;

import java.util.Collections;
import java.util.List;

// Abstract class defining common deck properties and functionalities

/**
 * Interface defining common deck functionalities
 */
public interface Deck {
    /**
     * a new deck is created by using json parsing to build the related cards from a specific file
     */
    public abstract void generate();

    /**
     * the cards in the deck are randomly shuffled
     */
    public abstract void shuffle();

    /**
     * returns the number of cards in the deck
     */
    public abstract int getSize();
}

