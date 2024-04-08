package model.objective;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import model.enums.Element;
import model.placementArea.PlacementArea;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

public interface Objective {
    public int countObjectivePoints(PlacementArea placementArea);
    public void printObjective();
}
