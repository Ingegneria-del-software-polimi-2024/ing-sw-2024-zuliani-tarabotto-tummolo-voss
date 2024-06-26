package model.PointsStrategy;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import model.enums.Artifact;
import model.placementArea.PlacementArea;

import java.io.Serializable;

/**
 * Interface of the strategy pattern that allows to count points
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public interface Points extends Serializable {
    /**
     * counts the points
     * @param placementArea the disposition of placed card belonging to the player
     * @return number of points given by this objective
     */
    int count(PlacementArea placementArea);

    /**
     * @return number of points provided per unity of satisfied elements
     */
    int getPoints();

    /**
     * @return The artifact associated with the points strategy.
     */
    Artifact getArtifact();
}

