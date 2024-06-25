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
     * The Element.
     */
    @JsonProperty("element")
    Element element;
    /**
     * The Artifact.
     */
    @JsonProperty("artifact")
    Artifact artifact;

    /**
     *
     * @return number of points provided per unity of satisfied elements
     */
    @Override
    public int getPoints() {
        return points;
    }

    /**
     * Gets artifacts.
     *
     * @return the artifacts
     */
    public Artifact getArtifacts() {
        return artifact;
    }

    @Override
    public String getPointsPolicy() { return ("Points policy -> ResourcePoints || Artifact requested: " + getArtifacts() + " || points given per Artifact: " + getPoints());}

    @Override
    public Artifact getArtifact(){
        return this.artifact;
    }
}
