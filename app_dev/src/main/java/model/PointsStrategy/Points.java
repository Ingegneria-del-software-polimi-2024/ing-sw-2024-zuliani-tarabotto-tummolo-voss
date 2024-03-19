package model.PointsStrategy;


import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

public interface Points {
    int count();
    int getPoints();  //Is this still needed? these are the points that have to be multiplied by the hidden corners
}

