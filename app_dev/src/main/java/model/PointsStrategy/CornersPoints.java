package model.PointsStrategy;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.placementArea.PlacementArea;

@JsonTypeName("CornersPoints")
public class CornersPoints implements Points {
    private int points;
    @Override
    public int count (PlacementArea placementArea) {
        return this.points * placementArea.getNumberNearbyCards();
    }
    @Override
    public int getPoints() {
        return points;
    }
}
