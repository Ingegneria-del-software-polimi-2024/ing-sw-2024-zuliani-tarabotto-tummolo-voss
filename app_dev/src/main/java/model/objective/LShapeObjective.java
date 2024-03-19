package model.objective;

import model.enums.Element;
import model.placementArea.PlacementArea;

public class LShapeObjective {
    private Element element;
    private Shape shape;

    public int countObjectivePoints(PlacementArea placementArea) {return 3 * placementArea.verifyObjective(shape, element);}
}