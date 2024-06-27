package model.cards.PlayableCards;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.PointsStrategy.Points;
import model.PointsStrategy.SimplePoints;
import model.deckFactory.GoldenDeck;
import model.enums.Element;
import model.placementArea.PlacementArea;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Represents a ResourceCard which is a type of PlayableCard with specific elements and points strategies.
 */
public class ResourceCard extends PlayableCard {

    /**
     * The element that is blocked on the back face of the card.
     */
    @JsonProperty("element")
    private Element blockedElement;
    //private Points points;


    /**
     * json parsing
     *
     * @param id the id
     * @return resource card
     * @throws JsonProcessingException the json processing exception
     */
    public static ResourceCard parse(int id) throws JsonProcessingException {
        int targetId = id; // ID to search for
        try {

            ClassLoader classLoader = ResourceCard.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("ResourceCards.json");

            if (inputStream == null) {
                throw new IOException("Resource file not found: ResourceCards.json");
            }

            // Read the JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(inputStream);

            // Look for the object with the specified ID
            JsonNode targetNode = null;
            for (JsonNode node : rootNode.get("ResourceCards")) {
                if (node.get("id").asInt() == targetId) {
                    targetNode = node;
                    break;
                }
            }

            // Extract the JSON string based on the ID
            if (targetNode != null) {
                String jsonString = targetNode.toString();
                //System.out.println("Extracted JSON based on ID " + targetId + ": " + jsonString);


                // Convert JSON to object
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerSubtypes(SimplePoints.class);
                ResourceCard resourceCard = mapper.readValue(jsonString, ResourceCard.class);
                return resourceCard;

            } else {
                System.out.println("Object with ID " + targetId + " not found.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Counts the points for this resource card based on its points strategy and the given placement area.
     *
     * @param placementArea the placement area to calculate points from.
     * @return the number of points granted by this card.
     */
    @Override
    public int countPoints(PlacementArea placementArea) {
        return points.count(placementArea);
    }



    //////////////////// GETTER METHODS ////////////////////////////////////////////////
    @Override
    public Element getBlockedElement() { return blockedElement; }
    public Points getPoints() { return points; }
    /**
     * IGNORE THIS METHOD(needed for code implementation)
     * @return null
     */
    @Override
    public Element[] getBlockedElements(){return null;}
    /**
     * IGNORE THIS METHOD(needed for code implementation)
     * @return null
     */
    @Override
    public Element[] getBackFaceCorners() { return null; }



    /////////////////// TESTED RELATED ONLY METHODS ////////////////////////////////////
//    public void printCorner(Corner c) {
//        if (c.getElement() != null) System.out.println("Corner_" + c.getId() + ": " + c.getElement());
//        else if (c.getArtifact() != null) System.out.println("Corner_" + c.getId() + ": " + c.getArtifact());
//        else System.out.println("Corner_" + c.getId() + ": empty");
//    }
//
//    @Override
//    public void printCard() {
//        System.out.println("Card ID: " + getId());
//
//        //stampa il fronte della carta
//        System.out.println("FRONT FACE");
//        for (Corner c : getCorners()) {
//            printCorner(c);
//        }
//        System.out.println(points.getPointsPolicy());
//        //stampa il retro della carta
//        System.out.println("BACK FACE");
//        System.out.println("Blocked element: " + getBlockedElement());
//
//    }
}
