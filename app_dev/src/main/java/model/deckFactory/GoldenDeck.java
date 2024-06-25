package model.deckFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.cards.Card;
import model.cards.PlayableCards.GoldCard;
import model.cards.PlayableCards.PlayableCard;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a deck of golden cards extending PlayableDeck.
 * This class overrides the generate method to populate the deck with GoldCard objects
 * parsed from a JSON resource file.
 */
public class GoldenDeck extends PlayableDeck {

    /**
     * Generates the deck by parsing GoldCard objects from a JSON resource file.
     * Cards are added to the deck from ID 41 to 80.
     * Each card is parsed using GoldCard.parse(int id), added to the deck, and its back corners are built.
     * Any exceptions encountered during parsing or deck generation are propagated as RuntimeExceptions.
     */
    @Override
    public void generate() {
        cards = new ArrayList<>();  // Initialize cards list

        for (int i = 41; i <= 80; i++) {
            try {
                GoldCard card = GoldCard.parse(i);
                addCard(card);
                card.buildBackCorners();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
