package model.objective;

import model.enums.Element;
import model.placementArea.*;

public class LShapeObjective implements Objective{
    private Element element;
    private Shape shape;

    public int countObjectivePoints(PlacementArea placementArea) {return 3 * placementArea.verifyObjective(shape, element);}
}