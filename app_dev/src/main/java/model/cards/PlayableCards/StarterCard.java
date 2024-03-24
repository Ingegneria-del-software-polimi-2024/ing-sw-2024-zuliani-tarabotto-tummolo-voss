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
import java.util.List;

public class StarterCard extends PlayableCard{

    @JsonProperty("blockedElements")
    private Element[] blockedElements;
    @JsonProperty("backFaceCorners")
    private Element[] backFaceCorners;

    public Element[] getBlockedElements() {
        return blockedElements;
    }

    public Element[] getBackFaceCorners() {
        return backFaceCorners;
    }

    public static StarterCard parse(int id) throws JsonProcessingException {

        int targetId = id; // ID to search for

        try {
            // Read the JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File("app_dev/src/main/resources/StarterCards.json"));

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
                //System.out.println("Extracted JSON based on ID " + targetId + ": " + jsonString);


                // Convert JSON to object
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerSubtypes(SimplePoints.class);
                StarterCard starterCard = mapper.readValue(jsonString, StarterCard.class);

                System.out.println(starterCard.getId());
                Element[] backFaceCorners = starterCard.getBackFaceCorners();
                for (Element element : backFaceCorners) {
                    System.out.println(element);
                }


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
    public Element getBlockedElement() {
        return null;
    }

    @Override
    public int countPoints(PlacementArea placementArea) {
        return 0;
    }

    @Override
    public Corner getCorner(int index) {
        if(!isFaceSide()) return new Corner(backFaceCorners[index]);
        for (Corner corner : corners) {
            if (corner.getId() == index) {
                return corner;
            }
        }
        return null;
    }
}