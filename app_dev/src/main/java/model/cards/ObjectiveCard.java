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

public class ObjectiveCard  implements Card {
    @JsonProperty("id")
    private char id;
    @JsonProperty("objective")
    private Objective objective;


    public int countPoints(PlacementArea placementArea){
        return objective.countObjectivePoints(placementArea);
    }

    public static ObjectiveCard parse(int id) throws JsonProcessingException {

        int targetId = id; // ID to search for




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
                if (node.get("id").asInt() == targetId) {
                    targetNode = node;
                    break;
                }
            }
            //System.out.println("Extracted JSON based on ID " + targetId + ": " );


            // Extract the JSON string based on the ID
            if (targetNode != null) {
                String jsonString = targetNode.toString();
                //System.out.println("Extracted JSON based on ID " + targetId + ": " + jsonString);

                // Convert JSON to object
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerSubtypes(LShapeObjective.class, ElementObjective.class, DiagonalShapeObjective.class, ArtifactObjective.class);
                ObjectiveCard objectiveCard = mapper.readValue(jsonString, ObjectiveCard.class);
                //System.out.println(objectiveCard.getId());



                return objectiveCard;

            } else {
                System.out.println("Object with ID " + targetId + " not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getId() {
        return id;
    }
}
