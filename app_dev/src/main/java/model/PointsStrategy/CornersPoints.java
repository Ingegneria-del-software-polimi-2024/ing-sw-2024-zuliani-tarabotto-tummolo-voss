package model.PointsStrategy;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.placementArea.PlacementArea;

@JsonTypeName("CornersPoints")
public class CornersPoints implements Points {
    private final int points = 2; //we always get two points per corner covered by the placed card
    @Override
    public int count (PlacementArea placementArea) {
        return this.points * placementArea.getNumberNearbyCards();
    }
    @Override
    public int getPoints() {
        return points;
    }
    @Override
    public String getPointsPolicy() {
        return ("Points policy -> CornersPoints || points given per corner covered: " + 2);
    }
}
