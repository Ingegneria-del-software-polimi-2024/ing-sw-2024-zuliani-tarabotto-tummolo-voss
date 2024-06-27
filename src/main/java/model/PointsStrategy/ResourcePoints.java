package model.PointsStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.enums.*;
import model.placementArea.*;

/**
 * points returned when having a certain number of resources ini the PlacementArea
 */
@JsonTypeName("ResourcePoints")
public class ResourcePoints implements Points {

    /**
     * The number of points we always get per unity of satisfied elements.
     */
    private int points;

    /**
     * counts the points
     * @param placementArea the disposition of placed card belonging to the player
     * @return number of points given by this objective
     */
    @Override
    public int count(PlacementArea placementArea) {
        if (element != null) {
            return placementArea.getNumberElements(element);
        } else {
            return placementArea.getNumberArtifacts(artifact);
        }
    }

    /**
     * The Element associated with the ResourcePoints.
     */
    @JsonProperty("element")
    Element element;

    /**
     * The Artifact associated with the ResourcePoints.
     */
    @JsonProperty("artifact")
    Artifact artifact;

    /**
     * @return number of points provided per unity of satisfied elements
     */
    @Override
    public int getPoints() {
        return points;
    }

    /**
     * @return the Artifact associated with the ResourcePoints
     */
    public Artifact getArtifacts() {
        return artifact;
    }

    /**
     * @return the points policy for ResourcePoints
     */
    @Override
    public Artifact getArtifact(){
        return this.artifact;
    }
}
