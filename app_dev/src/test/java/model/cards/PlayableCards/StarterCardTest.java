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

/**
 * Test class for the {@link StarterCard} class.
 */
public class StarterCardTest extends TestCase {

    /**
     * Tests that the {@link StarterCard#parse(int)} method successfully parses a valid starter card ID.
     *
     * @throws JsonProcessingException if an unexpected error occurs during parsing.
     */
    @Test
    public void testParse_ValidId() throws JsonProcessingException {
        // Given a valid starter card ID
        int id = 81;

        // When the parse method is called
        StarterCard starterCard = StarterCard.parse(id);

        // Then the starter card should be parsed correctly
        assertNotNull(starterCard);
        assertTrue(starterCard.getId() == 81);
    }

    /**
     * Tests that the {@link StarterCard#parse(int)} method returns null for an invalid starter card ID.
     *
     * @throws JsonProcessingException if an unexpected error occurs during parsing.
     */
    @Test
    public void testParse_InvalidId() throws JsonProcessingException {
        // Given an invalid starter card ID
        int id = 8;

        // When the parse method is called
        StarterCard starterCard = StarterCard.parse(id);

        // Then the method should return null
        assertNull(starterCard);
    }

    /**
     * Tests that the {@link StarterCard#getBlockedElements()} method retrieves the blocked elements correctly.
     *
     * @throws JsonProcessingException if an unexpected error occurs during retrieval.
     */
    @Test
    public void testGetBlockedElements() throws JsonProcessingException {
        // Given a starter card with blocked elements
        StarterCard starterCard = StarterCard.parse(81);

        // When the getBlockedElements method is called
        Element[] blockedElements = starterCard.getBlockedElements();

        // Then the blocked elements should be retrieved correctly
        assertNotNull(blockedElements);
    }

    /**
     * Tests that the {@link StarterCard#getBackFaceCorners()} method retrieves the back face corners correctly.
     *
     * @throws JsonProcessingException if an unexpected error occurs during retrieval.
     */
    @Test
    public void testGetBackFaceCorners() throws JsonProcessingException {
        // Given a starter card with back face corners
        StarterCard starterCard = StarterCard.parse(81);

        // When the getBackFaceCorners method is called
        Element[] backFaceCorners = starterCard.getBackFaceCorners();

        // Then the back face corners should be retrieved correctly
        assertNotNull(backFaceCorners);
    }

    /**
     * Tests that the {@link StarterCard#getCorner(int)} method retrieves the corner from the face side
     * for a valid index.
     *
     * @throws JsonProcessingException if an unexpected error occurs during retrieval.
     */
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

    /**
     * Tests that the {@link StarterCard#getCorner(int)} method retrieves the corner from the back face
     * for a valid index.
     *
     * @throws JsonProcessingException if an unexpected error occurs during retrieval.
     */
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
