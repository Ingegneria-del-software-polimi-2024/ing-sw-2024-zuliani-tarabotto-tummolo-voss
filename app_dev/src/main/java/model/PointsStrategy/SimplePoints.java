package model.PointsStrategy;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("SimplePoints")

public class SimplePoints implements Points {
    private int points;


    @Override
    public int getPoints() {
        return points;
    }
   @Override
    public int count() {
        return this.points;
    }


}
