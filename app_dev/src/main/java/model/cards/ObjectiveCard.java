package model.cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.cards.PlayableCards.ResourceCard;
import model.objective.*;
import model.placementArea.PlacementArea;

import javax.swing.text.Element;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * Represents an ObjectiveCard which is a type of Card.
 * ObjectiveCards have a specific objective that grants points based on the placement area.
 */
public class ObjectiveCard  implements Card {

    /**
     * The ID of the ObjectiveCard.
     */
    @JsonProperty("id")
    private char id;

    /**
     * The objective associated with the ObjectiveCard.
     */
    @JsonProperty("objective")
    private Objective objective;


    /**
     * json parsing
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    public static ObjectiveCard parse(int id) throws JsonProcessingException {

        try {

            ClassLoader classLoader = ResourceCard.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("ObjectiveCards.json");

            if (inputStream == null) {
                throw new IOException("Resource file not found: ObjectiveCards.json");
            }
            // Read the JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(inputStream);

            // Look for the object with the specified ID
            JsonNode targetNode = null;
            for (JsonNode node : rootNode.get("ObjectiveCards")) {
                if (node.get("id").asInt() == id) {
                    targetNode = node;
                    break;
                }
            }

            // Extract the JSON string based on the ID
            if (targetNode != null) {
                String jsonString = targetNode.toString();

                // Convert JSON to object
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerSubtypes(LShapeObjective.class, ElementObjective.class, DiagonalShapeObjective.class, ArtifactObjective.class);
                ObjectiveCard objectiveCard = mapper.readValue(jsonString, ObjectiveCard.class);

                return objectiveCard;

            } else {
                System.out.println("Object with ID " + id + " not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * based on the card's pointsPolicy the number of points granted by it is returned
     * @param placementArea
     * @return int
     */
    public int countPoints(PlacementArea placementArea){
        return objective.countObjectivePoints(placementArea);
    }

    public Objective getObjective(){
        return objective;
    }


    ////////////////////// GETTER METHODS //////////////////////////////////////////////
    public int getId() { return id; }

    /////////////////////// TESTING RELATED METHODS ONLY ///////////////////////////////
//    public void printCard() {
//        System.out.println("Card ID: " + getId());
//        objective.printObjective();
//        System.out.println();
//    }
}
