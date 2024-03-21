package model.cards.PlayableCards;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.PointsStrategy.Points;
import model.PointsStrategy.SimplePoints;
import model.enums.Element;
import model.placementArea.PlacementArea;

import java.io.File;
import java.io.IOException;

public class ResourceCard extends PlayableCard {
    @JsonProperty("element")
    private Element blockedElement;
    private Points points;


    public static ResourceCard parse(int id) throws JsonProcessingException {

        int targetId = id; // ID to search for

        try {
            // Read the JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File("app_dev/src/main/resources/ResourceCards.json"));

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


    @Override
    public Element getBlockedElement() {
        return blockedElement;
    }

    //ignore this function(needed in StarterCard class)
    @Override
    public Element[] getBlockedElements(){return null;}
    //ignore this function(needed in StarterCard class)
    @Override
    public Element[] getBackFaceCorners() {
        return null;
    }
    public Points getPoints() {
        return points;
    }
    @Override
    public int countPoints(PlacementArea placementArea) {
        return points.count(placementArea);
    }

    @Override
    public void printCard() {
        System.out.println("Card ID: " + getId());
        if (getFaceSide()) {
            for (Corner c : getCorners()) {
                printCorner(c);
            }
            System.out.println(points.getPointsPolicy());
            System.out.println();
        } else {
            System.out.println("Blocked element: " + getBlockedElement());
        }
    }

    public void printCorner(Corner c) {
        if (c.getElement() != null) System.out.println("Corner_" + c.getId() + ": " + c.getElement());
        else if (c.getArtifact() != null) System.out.println("Corner_" + c.getId() + ": " + c.getArtifact());
        else System.out.println("Corner_" + c.getId() + ": empty");
    }


}