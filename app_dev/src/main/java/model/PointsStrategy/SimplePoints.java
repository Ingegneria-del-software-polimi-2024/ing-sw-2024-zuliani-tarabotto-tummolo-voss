package model.PointsStrategy;

import com.fasterxml.jackson.annotation.JsonTypeName;
import model.enums.Artifact;
import model.placementArea.PlacementArea;

/**
 * points returning a constant number
 */
@JsonTypeName("SimplePoints")
public class SimplePoints implements Points {

    private int points;

    /**
     *
     * @return number of points provided per unity of satisfied elements
     */
    @Override
    public int getPoints() {
        return points;
    }

    /**
     * counts the points
     * @param placementArea the disposition of placed card belonging to the player
     * @return number of points given by this objective
     */
    @Override
    public int count(PlacementArea placementArea) {
        return this.points;
    }

    @Override
    public String getPointsPolicy() { return ("Points policy -> SimplePoints || points given: " + getPoints());}

    @Override
    public Artifact getArtifact(){
        return null;
    }
}
