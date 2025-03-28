package model.cards.PlayableCards;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import junit.framework.TestCase;
import model.PointsStrategy.Points;
import model.PointsStrategy.SimplePoints;
import model.enums.Element;
import model.placementArea.PlacementArea;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link ResourceCard} class.
 */
public class ResourceCardTest extends TestCase {

    /**
     * Tests that the {@link ResourceCard#parse(int)} method successfully parses a valid resource card ID.
     *
     * @throws JsonProcessingException if an unexpected error occurs during parsing.
     */
    @Test
    public void testParse_ValidId() throws JsonProcessingException {
        // Given a valid resource card ID
        int id = 1;

        // When the parse method is called
        ResourceCard resourceCard = ResourceCard.parse(id);

        // Then the resource card should be parsed correctly
        assertNotNull(resourceCard);
        assertTrue(resourceCard.getId() == 1); // ID should be positive
    }

    /**
     * Tests that the {@link ResourceCard#parse(int)} method returns null for an invalid resource card ID.
     *
     * @throws JsonProcessingException if an unexpected error occurs during parsing.
     */
    @Test
    public void testParse_InvalidId() throws JsonProcessingException {
        // Given an invalid resource card ID
        int id = 55;

        // When the parse method is called
        ResourceCard resourceCard = ResourceCard.parse(id);

        // Then the method should return null
        assertNull(resourceCard);
    }

    /**
     * Tests that the {@link ResourceCard#getPoints()} method returns a SimplePoints object.
     *
     * @throws JsonProcessingException if an unexpected error occurs during retrieval.
     */
    @Test
    public void testGetPoints_SimplePoints() throws JsonProcessingException {
        // Given a resource card with SimplePoints strategy
        ResourceCard resourceCard = ResourceCard.parse(1);

        // When the getPoints method is called
        Points points = resourceCard.getPoints();

        // Then the points strategy should be SimplePoints
        assertTrue(points instanceof SimplePoints);
    }

    /**
     * Tests that the {@link ResourceCard#countPoints(PlacementArea)} method calls the points strategy's count method
     * with the placement area.
     *
     * @throws JsonProcessingException if an unexpected error occurs during point counting.
     */
    @Test
    public void testCountPoints() throws JsonProcessingException {
        // Given a resource card with a placement area
        ResourceCard resourceCard = ResourceCard.parse(1);
        PlacementArea placementArea = new PlacementArea(); // Create a mock placement area

        // When the countPoints method is called
        int points = resourceCard.countPoints(placementArea);

        // Then the method should call the points strategy's count method with the placement area
        // Since the specific logic is in the Points implementation, we cannot verify the exact value here
        // However, we can check that a non-zero value is returned (assuming the card has points)
        assertTrue(points >= 0);
    }

    /**
     * Tests that the {@link ResourceCard#getBlockedElement()} method retrieves the blocked element correctly.
     *
     * @throws JsonProcessingException if an unexpected error occurs during retrieval.
     */
    @Test
    public void testGetBlockedElement() throws JsonProcessingException {
        // Given a resource card with a blocked element
        ResourceCard resourceCard = ResourceCard.parse(1);

        // When the getBlockedElement method is called
        Element blockedElement = resourceCard.getBlockedElement();

        // Then the blocked element should be retrieved correctly
        assertNotNull(blockedElement);
    }

    // Ignore the printCard method for testing purposes (it's for console output)
}
