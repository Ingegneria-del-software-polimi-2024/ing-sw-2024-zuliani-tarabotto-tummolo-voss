package com.prova.jsonparsing.cardsImplementation.PlayableCards;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prova.jsonparsing.PointsStrategy.CornersPoints;
import com.prova.jsonparsing.PointsStrategy.Points;
import com.prova.jsonparsing.PointsStrategy.ResourcePoints;
import com.prova.jsonparsing.PointsStrategy.SimplePoints;
import com.prova.jsonparsing.enums.Element;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GoldCard extends PlayableCard{




    private String type; //da controllare dove vieme usato da jackson
    private String element;
    private Map<Element, Integer> placementConstraint;
    private Points points;

    public  static GoldCard parse(int id)  throws Exception {

        int targetId = id; // ID to search for

        try {
            // Read the JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File("com/prova/jsonparsing/resources/GoldenCards.json"));

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
                    System.out.println(goldenCard.getId());
                    System.out.println(goldenCard.getPlacementConstraint());


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



            // Getters for card attributes






            public String getElement () {
                return element;
            }

            public Map<Element, Integer> getPlacementConstraint () {
                return Collections.unmodifiableMap(placementConstraint); // Return an unmodifiable map
            }

            public Points getPoints () {
                return points;
            }


}
