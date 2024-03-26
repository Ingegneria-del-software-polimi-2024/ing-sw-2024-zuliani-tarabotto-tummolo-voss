package model.cards;

import junit.framework.TestCase;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import model.objective.Objective;
import model.placementArea.PlacementArea;
import org.junit.jupiter.api.Test;




public class ObjectiveCardTest extends TestCase {



        @Test
        public void testParse_ValidId() throws JsonProcessingException {
            // Given a valid objective card ID
            int id = 87; // Assuming the ID 1 exists in the ObjectiveCards.json file

            // When the parse method is called
            ObjectiveCard objectiveCard = ObjectiveCard.parse(id);

            // Then the objective card should be parsed correctly
            assertNotNull(objectiveCard);
            assertTrue(objectiveCard.getId() > 0); // ID should be positive
        }

        @Test
        public void testParse_InvalidId() throws JsonProcessingException {
            // Given an invalid objective card ID
            int id = -1;

            // When the parse method is called
            ObjectiveCard objectiveCard = ObjectiveCard.parse(id);

            // Then the method should return null
            assertNull(objectiveCard);
        }




}