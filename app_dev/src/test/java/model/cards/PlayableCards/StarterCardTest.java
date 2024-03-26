package model.cards.PlayableCards;

import junit.framework.TestCase;



import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import junit.framework.TestCase;
import model.PointsStrategy.Points;
import model.PointsStrategy.SimplePoints;
import model.enums.Element;
import model.placementArea.PlacementArea;
import org.junit.jupiter.api.Test;

public class StarterCardTest extends TestCase {
    @Test
    public void testParse_ValidId() throws JsonProcessingException {
        // Given a valid starter card ID
        int id = 81;

        // When the parse method is called
        StarterCard starterCard = StarterCard.parse(id);

        // Then the starter card should be parsed correctly
        assertNotNull(starterCard);
        assertTrue(starterCard.getId() > 0); // ID should be positive
    }

    @Test
    public void testParse_InvalidId() throws JsonProcessingException {
        // Given an invalid starter card ID
        int id = -1;

        // When the parse method is called
        StarterCard starterCard = StarterCard.parse(id);

        // Then the method should return null
        assertNull(starterCard);
    }

    @Test
    public void testGetBlockedElements() throws JsonProcessingException {
        // Given a starter card with blocked elements
        StarterCard starterCard = StarterCard.parse(81);

        // When the getBlockedElements method is called
        Element[] blockedElements = starterCard.getBlockedElements();

        // Then the blocked elements should be retrieved correctly
        assertNotNull(blockedElements);
    }

    @Test
    public void testGetBackFaceCorners() throws JsonProcessingException {
        // Given a starter card with back face corners
        StarterCard starterCard = StarterCard.parse(81);

        // When the getBackFaceCorners method is called
        Element[] backFaceCorners = starterCard.getBackFaceCorners();

        // Then the back face corners should be retrieved correctly
        assertNotNull(backFaceCorners);
    }

    @Test
    public void testGetCorner_FaceSide() throws JsonProcessingException {
        // Given a starter card with a corner on the face side
        StarterCard starterCard = StarterCard.parse(81);

        // Set faceSide to true (assuming a method to set face side)
        starterCard.faceSide = true;

        // When the getCorner method is called with a valid index
        int index = 0; // Assuming corners are indexed from 0

        Corner corner = starterCard.getCorner(index);

        // Then the corner from the face side should be returned
        assertNotNull(corner);
    }

    @Test
    public void testGetCorner_BackSide() throws JsonProcessingException {
        // Given a starter card with back face corners
        StarterCard starterCard = StarterCard.parse(81);

        // Set faceSide to false (assuming a method to set face side)
        starterCard.faceSide = false;

        // When the getCorner method is called with a valid index
        int index = 0; // Assuming back face corners are indexed from 0

        Corner corner = starterCard.getCorner(index);

        // Then the corner from the back face corners should be returned
        assertNotNull(corner);
        assertEquals(starterCard.getBackFaceCorners()[index], corner.getElement()); // Check the element matches
    }


}