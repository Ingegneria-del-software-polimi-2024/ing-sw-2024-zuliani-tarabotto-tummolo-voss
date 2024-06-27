package model.deckFactory;

import model.cards.PlayableCards.PlayableCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A base class representing a playable deck of cards.
 * This abstract class provides methods to manipulate and access a collection of PlayableCard objects.
 */
public abstract class PlayableDeck implements Deck {

    /**
     * The list of PlayableCard objects representing the deck of playable cards.
     * This list holds all playable cards that have been added to the deck.
     */
    protected ArrayList<PlayableCard> cards;

    /**
     * Shuffles the order of cards in the deck.
     * This method randomizes the sequence of cards using the Collections.shuffle() method.
     */
    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Removes and returns the first card from the deck.
     *
     * @return The extracted PlayableCard object from the front (head) of the deck.
     */
    public PlayableCard extract() {
        PlayableCard extracted = cards.get(0);
        cards.remove(0);
        return extracted;
    }

    /**
     * Adds a PlayableCard to the end (tail) of the deck.
     *
     * @param card The PlayableCard object to be added to the deck.
     */
    public void addCard(PlayableCard card) {
        cards.add(card);
    }

    ////////////// GETTER METHODS ////////////////////////

    /**
     * Retrieves the list of PlayableCard objects in the deck.
     *
     * @return The list of PlayableCard objects currently in the deck.
     */
    public List<PlayableCard> getCards() {
        return cards;
    }

    /**
     * Retrieves the current number of cards in the deck.
     *
     * @return The number of PlayableCard objects in the deck.
     */
    @Override
    public int getSize() {
        return cards.size();
    }

    ////////////// TEST RELATED METHODS ONLY ////////////////////

    /**
     * Retrieves a PlayableCard from the deck based on its ID.
     *
     * @param id The ID of the PlayableCard to retrieve.
     * @return The PlayableCard object with the specified ID.
     * @throws IllegalArgumentException If no PlayableCard with the specified ID is found in the deck.
     */
    public PlayableCard getCard(int id) throws IllegalArgumentException {
        for (PlayableCard c : cards) {
            if (c.getId() == id)
                return c;
        }
        throw new IllegalArgumentException("Card with ID " + id + " not found in the deck.");
    }
}
