package model.deckFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.cards.ObjectiveCard;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a deck of objective cards implementing the Deck interface.
 * This class initializes and manages a list of ObjectiveCard objects.
 */
public class ObjectiveDeck implements Deck {

    /**
     * The list of ObjectiveCard objects representing the deck of objective cards.
     * This list holds all objective cards that have been generated or added to the deck.
     */
    private ArrayList<ObjectiveCard> cards;


    /**
     * Generates the deck by parsing ObjectiveCard objects from a JSON resource file.
     * Cards are added to the deck from ID 87 to 102.
     * Each card is parsed using ObjectiveCard.parse(int id) and added to the cards list.
     * Any exceptions encountered during parsing or deck generation are propagated as RuntimeExceptions.
     */
    @Override
    public void generate() {
        cards = new ArrayList<>();  // Initialize cards list

        for (int i = 87; i <= 102; i++) {
            try {
                ObjectiveCard card = ObjectiveCard.parse(i);
                addCard(card);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Adds a card to the back (tail/end) of the cards list.
     *
     * @param card .
     */
    public void addCard(ObjectiveCard card) {
        cards.add(card);
    }

    /**
     * Shuffles the cards in the deck using Collections.shuffle().
     */
    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Removes and returns the first card in the deck.
     * Throws an IllegalStateException if the deck is empty.
     *
     * @return The extracted ObjectiveCard.
     */
    public ObjectiveCard extract() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty!");
        }
        ObjectiveCard extracted = cards.get(0);
        cards.remove(0);
        return extracted;
    }

    ///////////////////// GETTER METHODS ////////////////////////////////////////////

    /**
     * Returns the number of cards in the deck.
     * @return The size of the deck.
     */
    @Override
    public int getSize() {
        return cards.size();
    }

    /**
     * Returns the ObjectiveCard at the specified index in the cards list.
     *
     * @param i The index of the card to retrieve.
     * @return The ObjectiveCard at the specified index.
     */
    public ObjectiveCard get(int i) {
        return cards.get(i);
    }

    /**
     * Returns the entire list of ObjectiveCards in the deck.
     *
     * @return The list of ObjectiveCards.
     */
    public ArrayList<ObjectiveCard> getCards() {
        return cards;
    }

    ////////////////// TEST RELATED METHODS ONLY ///////////////////

    /**
     * Retrieves a card from the deck based on its ID.
     * This method is for testing purposes only.
     *
     * @param id The ID of the card to retrieve.
     * @return The ObjectiveCard with the specified ID, or null if not found.
     */
    public ObjectiveCard getCard(int id) {
        for (int i = 0; i < cards.size(); i++) {
            ObjectiveCard c = cards.get(i);
            if (c.getId() == id)
                return c;
        }
        return null;
    }
}
