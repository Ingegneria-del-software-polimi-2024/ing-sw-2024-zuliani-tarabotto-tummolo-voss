package model.cards.PlayableCards;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.enums.Element;
import model.PointsStrategy.*;
import model.placementArea.PlacementArea;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GoldCard extends PlayableCard{
    @JsonProperty("element")
    private Element blockedElement;
    private Map<Element, Integer> placementConstraint;
    private Points points;

    public  static GoldCard parse(int id)  throws Exception {

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

                // Access parsed properties of the GoldenCard object
                //System.out.println(goldenCard.getId());
                //System.out.println(goldenCard.getPlacementConstraint());


                return goldenCard;


            }else {
                System.out.println("Object with ID " + targetId + " not found.");
            }
        } catch (
                IOException e) {
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



    public Map<Element, Integer> getPlacementConstraint () {
        return Collections.unmodifiableMap(placementConstraint); // Return an unmodifiable map
    }

    public Points getPoints () {
        return points;
    }

    @Override
    public int countPoints (PlacementArea placementArea) {
        return points.count(placementArea);
    }

    //DA ELIMINARE SERVE PER TEST A CONSOLE
    @Override
    public void printCard() {
        System.out.println("Card ID: " + getId());

        //Stampa il fronte della carta
        System.out.println("FRONT FACE");
        for (Corner c : getCorners()) {printCorner(c);}
        System.out.println(points.getPointsPolicy());
        System.out.println();

        //stampa il retro della carta
        System.out.println("BACK FACE");
        System.out.println("Blocked element: " + getBlockedElement());

    }
    public void printCorner(Corner c) {
        if (c.getElement() != null) System.out.println("Corner_" + c.getId() + ": " + c.getElement());
        else if (c.getArtifact() != null) System.out.println("Corner_" + c.getId() + ": " + c.getArtifact());
        else System.out.println("Corner_" + c.getId() + ": empty");
    }


}