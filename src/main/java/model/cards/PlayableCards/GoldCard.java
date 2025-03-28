package model.cards.PlayableCards;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.enums.Element;
import model.PointsStrategy.*;
import model.placementArea.PlacementArea;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

/**
 * Represents a GoldCard, which is a type of PlayableCard with specific properties
 * such as blocked elements and placement constraints.
 */
public class GoldCard extends PlayableCard {

    /**
     * The element that is blocked by this GoldCard.
     */
    @JsonProperty("element")
    private Element blockedElement;

    /**
     * The placement constraints for this GoldCard, represented as a map of elements to their respective constraints.
     */
    private Map<Element, Integer> placementConstraint;

    /**
     * The points strategy associated with this GoldCard.
     */
    private Points points;


    /**
     * json parsing
     *
     * @param id the id
     * @return GoldCard gold card
     * @throws Exception the exception
     */
    public static GoldCard parse(int id) throws Exception {
        int targetId = id; // ID to search for

        try {
            ClassLoader classLoader = ResourceCard.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("GoldenCards.json");

            if (inputStream == null) {
                throw new IOException("Resource file not found: GoldenCards.json");
            }
            // Read the JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(inputStream);

            // Look for the object with the specified ID
            JsonNode targetNode = null;
            for (JsonNode node : rootNode.get("GoldenCards")) {
                if (node.get("id").asInt() == targetId) {
                    targetNode = node;
                    break;
                }
            }

            // Extract the JSON string based on the ID
            if (targetNode != null) {


                String jsonString = targetNode.toString();

                ObjectMapper mapper = new ObjectMapper();
                mapper.registerSubtypes(ResourcePoints.class);  // Register SimplePoints as a subtype of Points
                mapper.registerSubtypes(SimplePoints.class);
                mapper.registerSubtypes(CornersPoints.class);
                // Parse the JSON string into a GoldenCard object
                GoldCard goldenCard = mapper.readValue(jsonString, GoldCard.class);

                return goldenCard;

            } else {
                System.out.println("Object with ID " + targetId + " not found.");
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Counts the points of the GoldCard based on the given placement area.
     *
     * @param placementArea the placement area to count points from.
     * @return the points counted.
     */
    @Override
    public int countPoints(PlacementArea placementArea) {
        return points.count(placementArea);
    }


    //////////////// GETTER METHODS ////////////////////////////////
    @Override
    public Element getBlockedElement() {
        return blockedElement;
    }

    @Override
    public Points getPoints() {
        return points;
    }

    public Map<Element, Integer> getPlacementConstraint() {
        return Collections.unmodifiableMap(placementConstraint);
    }

    /**
     * IGNORE THIS METHOD(needed for code implementation)
     *
     * @return null
     */
    @Override
    public Element[] getBackFaceCorners() {
        return null;
    }

    /**
     * IGNORE THIS METHOD(needed for code implementation)
     *
     * @return null
     */
    @Override
    public Element[] getBlockedElements() {
        return null;
    }

///////////////////// FOR TESTING PURPOSES ONLY METHODS //////////////////////////////
//    @Override
//    public void printCard() {
//        System.out.println("Card ID: " + getId());
//
//        //prints the front face of the card
//        System.out.println("FRONT FACE");
//        for (Corner c : getCorners()) {printCorner(c);}
//        System.out.println(points.getPointsPolicy());
//        System.out.println();
//
//        //prints the back face of the card
//        System.out.println("BACK FACE");
//        System.out.println("Blocked element: " + getBlockedElement());
//
//    }
//    public void printCorner(Corner c) {
//        if (c.getElement() != null) System.out.println("Corner_" + c.getId() + ": " + c.getElement());
//        else if (c.getArtifact() != null) System.out.println("Corner_" + c.getId() + ": " + c.getArtifact());
//        else System.out.println("Corner_" + c.getId() + ": empty");
//    }

}

