package model.PointsStrategy;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import model.placementArea.PlacementArea;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
/**
 * Interface of the strategy pattern that allows to count points
 */
public interface Points {
    /**
     * counts the points
     * @param placementArea the disposition of placed card belonging to the player
     * @return number of points given by this objective
     */
    int count(PlacementArea placementArea);

    /**
     *
     * @return number of points provided per unity of satisfied elements
     */
    int getPoints();  //Is this still needed? these are the points that have to be multiplied by the hidden corners

    String getPointsPolicy();
}

