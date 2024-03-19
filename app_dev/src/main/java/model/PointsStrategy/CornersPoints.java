package model.PointsStrategy;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.placementArea.PlacementArea;

@JsonTypeName("CornersPoints")
public class CornersPoints implements Points {
    private int points;
    private PlacementArea placementArea;
    @Override
    public int count () {
        return this.placementArea.getNumberNearbyCards();
    }
    @Override
    public int getPoints() {
        return points;
    }
}
