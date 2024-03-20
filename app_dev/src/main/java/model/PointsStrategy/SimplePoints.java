package model.PointsStrategy;

import com.fasterxml.jackson.annotation.JsonTypeName;
import model.placementArea.PlacementArea;

@JsonTypeName("SimplePoints")

public class SimplePoints implements Points {

    private int points;
    @Override
    public int getPoints() {
        return points;
    }
    @Override
    public int count(PlacementArea placementArea) {
        return this.points;
    }


}
