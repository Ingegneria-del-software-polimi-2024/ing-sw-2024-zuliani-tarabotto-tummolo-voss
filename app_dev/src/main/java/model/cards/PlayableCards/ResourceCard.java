package model.cards.PlayableCards;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.PointsStrategy.Points;
import model.PointsStrategy.SimplePoints;
import model.enums.Element;

import java.io.File;
import java.io.IOException;

public class ResourceCard extends PlayableCard {




    private Element element;




    public static ResourceCard parse(int id) throws JsonProcessingException {

        int targetId = id; // ID to search for

        try {
            // Read the JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/ResourceCards.json"));

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
/*
                // Access data from the object
                System.out.println("ResourceCard ID: " + resourceCard.getId());
                System.out.println("Number of corners: " + resourceCard.getCorners().size());




                System.out.println("Resource Card Details:");
                System.out.println("  ID: " + resourceCard.getId());
                System.out.println("  Face Side: " + resourceCard.isFaceSide());
                System.out.println("  Type: " + resourceCard.getType());
                System.out.println("  Element: " + resourceCard.getElement());

                System.out.println("\nCorners:");
                List<Corner> corners = resourceCard.getCorners();
                for (int i = 0; i < corners.size(); i++) {
                    Corner corner = corners.get(i);
                    System.out.println("  Corner " + (i + 1) + ":");
                    System.out.println("    ID: " + corner.getId());
                    System.out.println("    Element: " + corner.getElement());
                    System.out.println("    Artifact: " + corner.getArtifact());
                    System.out.println("    Available: " + corner.isAvailable());
                }


// Access the points information
                Points cardPoints = resourceCard.getPoints();


                if (cardPoints instanceof SimplePoints) {
                    SimplePoints simplePoints = (SimplePoints) cardPoints; // Cast to access specific methods
                    System.out.println("Points Value: " + simplePoints.getPoints());
                } else {
                    // Handle other concrete implementations if they exist
                    System.out.println("Points Information: (Implementation specific details)");
                }

                Class<? extends Points> pointsClass = cardPoints.getClass();
                System.out.println("Points type: " + pointsClass.getSimpleName());
*/

                return resourceCard;

            } else {
                System.out.println("Object with ID " + targetId + " not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Points getPoints() {
        return points;
    }

    private Points points;

    public Element getElement() {
        return element;
    }


}