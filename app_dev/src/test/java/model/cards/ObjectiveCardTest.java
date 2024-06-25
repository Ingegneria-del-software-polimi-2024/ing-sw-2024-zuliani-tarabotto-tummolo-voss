package model.cards;

import junit.framework.TestCase;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import model.objective.Objective;
import model.placementArea.PlacementArea;
import org.junit.jupiter.api.Test;


/**
 * Test class for the {@link ObjectiveCard} class.
 */
public class ObjectiveCardTest extends TestCase {

    /**
     * Tests that the {@link ObjectiveCard#parse(int)} method successfully parses a valid objective card ID.
     *
     * @throws JsonProcessingException if an unexpected error occurs during parsing.
     */
    @Test
    public void testParse_ValidId() throws JsonProcessingException {
        // Given a valid objective card ID
        int id = 87; // Assuming the ID 1 exists in the ObjectiveCards.json file

        // When the parse method is called
        ObjectiveCard objectiveCard = ObjectiveCard.parse(id);

        // Then the objective card should be parsed correctly
        assertNotNull(objectiveCard);
        assertTrue(objectiveCard.getId() == 87);
    }

    /**
     * Tests that the {@link ObjectiveCard#parse(int)} method returns null for an invalid objective card ID.
     *
     * @throws JsonProcessingException if an unexpected error occurs during parsing.
     */
    @Test
    public void testParse_InvalidId() throws JsonProcessingException {
        // Given an invalid objective card ID
        int id = 18;

        // When the parse method is called
        ObjectiveCard objectiveCard = ObjectiveCard.parse(id);

        // Then the method should return null
        assertNull(objectiveCard);
    }


}
