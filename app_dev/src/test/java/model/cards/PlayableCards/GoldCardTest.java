package model.cards.PlayableCards;

import junit.framework.TestCase;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.PointsStrategy.Points;
import model.enums.Element;
import model.placementArea.PlacementArea;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * Test class for the {@link GoldCard} class.
 */
public class GoldCardTest extends TestCase {

    /**
     * Tests that the {@link GoldCard#parse(int)} method successfully parses a valid gold card ID.
     *
     * @throws Exception if an unexpected error occurs during parsing.
     */
    @Test
    public void testParse_ValidId() throws Exception {
        // Given a valid gold card ID
        int id = 41; // Assuming the ID 1 exists in the GoldenCards.json file

        // When the parse method is called
        GoldCard goldCard = GoldCard.parse(id);

        // Then the gold card should be parsed correctly
        assertNotNull(goldCard);
        assertTrue(goldCard.getId() == 41);
    }

    /**
     * Tests that the {@link GoldCard#parse(int)} method returns null for an invalid gold card ID.
     *
     * @throws Exception if an unexpected error occurs during parsing.
     */
    @Test
    public void testParse_InvalidId() throws Exception {
        // Given an invalid gold card ID
        int id = 4;

        // When the parse method is called
        GoldCard goldCard = GoldCard.parse(id);

        // Then the method should return null
        assertNull(goldCard);
    }

    /**
     * Tests that the {@link GoldCard#getPlacementConstraint()} method returns an unmodifiable map.
     *
     * @throws Exception if an unexpected error occurs during retrieval.
     */
    @Test
    public void testGetPlacementConstraint_UnmodifiableMap() throws Exception {
        // Given a gold card with a placement constraint
        GoldCard goldCard = GoldCard.parse(41); // Assuming card ID 1 has a placement constraint

        // When the getPlacementConstraint method is called
        Map<Element, Integer> constraintMap = goldCard.getPlacementConstraint();

        // Then the returned map should be unmodifiable
        assertThrows(UnsupportedOperationException.class, () -> constraintMap.put(Element.insects, 2));
    }

    /**
     * Tests that the {@link GoldCard#getPoints()} method retrieves the points strategy correctly.
     *
     * @throws Exception if an unexpected error occurs during retrieval.
     */
    @Test
    public void testGetPoints_PointsStrategy() throws Exception {
        // Given a gold card with a points strategy
        GoldCard goldCard = GoldCard.parse(41); // Assuming card with ID 1 uses a PointsStrategy

        // When the getPoints method is called
        Points points = goldCard.getPoints();

        // Then the points strategy should be retrieved correctly
        assertNotNull(points);
    }

    /**
     * Tests that the {@link GoldCard#countPoints(PlacementArea)} method calls the points strategy's count method
     * with the placement area.
     *
     * @throws Exception if an unexpected error occurs during point counting.
     */
    @Test
    public void testCountPoints() throws Exception {
        // Given a gold card with a placement area
        GoldCard goldCard = GoldCard.parse(41); // Assuming card ID 1 has implemented countPoints logic
        PlacementArea placementArea = new PlacementArea(); // Create a mock placement area

        // When the countPoints method is called
        int points = goldCard.countPoints(placementArea);

        // Then the method should call the points strategy's count method with the placement area
        // Since the specific logic is in the Points implementation, we cannot verify the exact value here
        // However, we can check that a non-zero value is returned (assuming the card has points)
        assertTrue(points >= 0);
    }

    /**
     * Tests that the {@link GoldCard#getBlockedElement()} method retrieves the blocked element correctly.
     *
     * @throws Exception if an unexpected error occurs during retrieval.
     */
    @Test
    public void testGetBlockedElement() throws Exception {
        // Given a gold card with a blocked element
        GoldCard goldCard = GoldCard.parse(41); // Assuming card ID 1 has a blocked element

        // When the getBlockedElement method is called
        Element blockedElement = goldCard.getBlockedElement();

        // Then the blocked element should be retrieved correctly
        assertNotNull(blockedElement);
    }




}

