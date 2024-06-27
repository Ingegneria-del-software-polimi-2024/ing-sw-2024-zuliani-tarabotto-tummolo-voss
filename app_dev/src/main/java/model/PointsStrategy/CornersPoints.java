package model.PointsStrategy;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.enums.Artifact;
import model.placementArea.PlacementArea;

/**
 * The CornersPoints class implements the Points interface and defines the points returned when covering surrounding corners.
 *
 */
@JsonTypeName("CornersPoints")
public class CornersPoints implements Points {

    /**
     * The number of points we always get per corner covered by the placed card.
     */
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
     * Returns the number of points provided per unity of satisfied elements.
     *
     * @return The number of points provided per unity of satisfied elements.
     */
    @Override
    public int getPoints() {
        return points;
    }


    /**
     * Returns the artifact for CornersPoints.
     *
     * @return The artifact for CornersPoints.
     */
    @Override
    public Artifact getArtifact(){
        return null;
    }
}
