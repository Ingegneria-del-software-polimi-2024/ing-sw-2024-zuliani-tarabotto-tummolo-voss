package model.cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.PointsStrategy.SimplePoints;
import model.cards.PlayableCards.ResourceCard;
import model.objective.*;

import java.io.File;
import java.io.IOException;

public class ObjectiveCard  implements Card {
    @JsonProperty("id")
    private char id;
    @JsonProperty("objective")
    private Objective objective;




    public static ObjectiveCard parse(int id) throws JsonProcessingException {

        int targetId = id; // ID to search for

        try {
            // Read the JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File("app_dev/src/main/resources/ObjectiveCards.json"));

            // Look for the object with the specified ID
            JsonNode targetNode = null;
            for (JsonNode node : rootNode.get("ObjectiveCards")) {
                if (node.get("id").asInt() == targetId) {
                    targetNode = node;
                    break;
                }
            }

            // Extract the JSON string based on the ID
            if (targetNode != null) {
                String jsonString = targetNode.toString();
                System.out.println("Extracted JSON based on ID " + targetId + ": " + jsonString);


                // Convert JSON to object
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerSubtypes(LShapeObjective.class, ElementObjective.class, DiagonalShapeObjective.class, ArtifactObjective.class);
                ObjectiveCard objectiveCard = mapper.readValue(jsonString, ObjectiveCard.class);

                return objectiveCard;

            } else {
                System.out.println("Object with ID " + targetId + " not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
