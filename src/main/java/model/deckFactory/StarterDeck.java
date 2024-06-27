package model.deckFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.cards.PlayableCards.StarterCard;

import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;

/**
 * A class representing a deck of starter cards.
 * This deck is initialized with StarterCard objects generated from JSON data.
 */
public class StarterDeck extends PlayableDeck {

    /**
     * Generates the deck of starter cards by parsing JSON data and initializing StarterCard objects.
     * Each StarterCard is parsed from a JSON resource file and added to the deck.
     */
    @Override
    public void generate() {
        cards = new ArrayList<>();  // Initialize cards list

        // Loop through IDs of starter cards (81 to 86)
        for (int i = 81; i <= 86; i++) {
            try {
                // Parse StarterCard from JSON based on ID
                StarterCard card = StarterCard.parse(i);
                addCard(card);  // Add parsed card to the deck
                card.buildBackCorners();  // Build back corners for TUI printing (if needed)
            } catch (JsonProcessingException e) {
                e.printStackTrace();  // Handle JSON processing exceptions
            }
        }
    }
}
