package model.cards.PlayableCards;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.PointsStrategy.SimplePoints;
import model.cards.PlayableCards.Corner;
import model.enums.Element;
import model.placementArea.PlacementArea;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a StarterCard which is a type of PlayableCard.
 * StarterCards do not grant any points and have blocked elements on their back face.
 */
public class StarterCard extends PlayableCard{

    /**
     * Elements that are blocked on the front face of the card.
     */
    @JsonProperty("blockedElements")
    private Element[] blockedElements;

    /**
     * Elements on the back face corners of the card.
     */
    @JsonProperty("backFaceCorners")
    private Element[] backFaceCorners;

    /**
     * Gets the blocked elements on the front face of this starter card.
     *
     * @return the blocked elements.
     */
    @Override
    public Element[] getBlockedElements() {
        return blockedElements;
    }

    /**
     * Gets the elements on the back face corners of this starter card.
     *
     * @return the back face corner elements.
     */
    @Override
    public Element[] getBackFaceCorners() {
        return backFaceCorners;
    }
    /**
     * List of corners on the back face, used for TUI (Text User Interface).
     */
    private List<Corner> backCorners = new ArrayList<>();

    /**
     * Flag to initialize the corners.
     */
    private boolean initializeCorners = true;


    /**
     * json parsing
     *
     * @param id the id
     * @return starter card
     * @throws JsonProcessingException the json processing exception
     */
    public static StarterCard parse(int id) throws JsonProcessingException {
        int targetId = id; // ID to search for
        try {
            ClassLoader classLoader = ResourceCard.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("StarterCards.json");

            if (inputStream == null) {
                throw new IOException("Resource file not found: StarterCards.json");
            }
            // Read the JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(inputStream);

            // Look for the object with the specified ID
            JsonNode targetNode = null;
            for (JsonNode node : rootNode.get("StarterCards")) {
                if (node.get("id").asInt() == targetId) {
                    targetNode = node;
                    break;
                }
            }

            // Extract the JSON string based on the ID
            if (targetNode != null) {
                String jsonString = targetNode.toString();

                // Convert JSON to object
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerSubtypes(SimplePoints.class);
                StarterCard starterCard = mapper.readValue(jsonString, StarterCard.class);
                Element[] backFaceCorners = starterCard.getBackFaceCorners();

                return starterCard;

            } else {
                System.out.println("Object with ID " + targetId + " not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public int countPoints(PlacementArea placementArea) {
        //always returns 0 because StarterCard don't give any points
        return 0;
    }



    ///////////////////// GETTER METHODS ///////////////////////////////////////////////////
    @Override
    public Corner getCorner(int index) {
        if(initializeCorners){
            //we initialize backCorners
            for(int i = 0; i < backFaceCorners.length; i++){
                backCorners.add(i, new Corner(backFaceCorners[i]));
                backCorners.get(i).setId(i);
            }
            initializeCorners = false;
        }

        if(!getFaceSide()){
            for(Corner corner : backCorners){
                if(corner.getId() == index) return corner;
            }
        };
        for (Corner corner : corners) {
            if (corner.getId() == index) {
                return corner;
            }
        }
        return null;
    }

    /**
     * IGNORE THIS METHOD(needed for code implementation)
     * @return null
     */
    @Override
    public Element getBlockedElement() {
        return null;
    }



    ///////////////////// TESTING RELATED METHODS ONLY /////////////////////////////////////
//    @Override
//    public void printCard() {
////        System.out.println("Card ID: " + getId());
////        //stampa il fronte della carta
////        System.out.println("FRONT FACE");
////        for (Corner c : getCorners()){System.out.println("Corner_" + getCorners().indexOf(c) + ": "+c.getElement());}
////        for (Element el : getBlockedElements()) {System.out.println("Blocked elements: " + el.toString());}
////        System.out.println();
////        //stampa il retro della carta
////        System.out.println("BACK FACE");
////        int i = 0;
////        for(Element el : getBackFaceCorners()){
////            System.out.println("Corner_" + i + ": " + el.toString());
////            i++;
////        }
////
////        System.out.println();
//    }

}
