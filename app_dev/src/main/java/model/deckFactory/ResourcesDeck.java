package model.deckFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.cards.PlayableCards.ResourceCard;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;

/**
 * A class representing a deck of resource cards.
 * This deck is initialized with ResourceCard objects generated from JSON data.
 */
public class ResourcesDeck extends PlayableDeck {

    /**
     * Generates the deck of resource cards by parsing JSON data and initializing ResourceCard objects.
     * Each ResourceCard is parsed from a JSON resource file and added to the deck.
     */
    @Override
    public void generate() {
        cards = new ArrayList<>();  // Initialize cards list

        // Loop through IDs of resource cards (1 to 40)
        for (int i = 1; i <= 40; i++) {
            try {
                // Parse ResourceCard from JSON based on ID
                ResourceCard card = ResourceCard.parse(i);
                addCard(card);  // Add parsed card to the deck
                card.buildBackCorners();  // Build back corners for TUI printing (if needed)
            } catch (JsonProcessingException e) {
                e.printStackTrace();  // Handle JSON processing exceptions
            }
        }
    }
}
