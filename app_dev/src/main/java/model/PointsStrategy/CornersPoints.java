package model.PointsStrategy;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.enums.Artifact;
import model.placementArea.PlacementArea;

/**
 * points returned when covering surrounding corners
 */
@JsonTypeName("CornersPoints")
public class CornersPoints implements Points {
    private final int points = 2; //we always get two points per corner covered by the placed card
    /**
     * counts the points
     * @param placementArea the disposition of placed card belonging to the player
     * @return number of points given by this objective
     */
    @Override
    public int count (PlacementArea placementArea) {
        return this.points * placementArea.getNumberNearbyCards();
    }

    /**
     *
     * @return number of points provided per unity of satisfied elements
     */
    @Override
    public int getPoints() {
        return points;
    }
    @Override
    public String getPointsPolicy() {
        return ("Points policy -> CornersPoints || points given per corner covered: " + 2);
    }
    @Override
    public Artifact getArtifact(){
        return null;
    }
}
