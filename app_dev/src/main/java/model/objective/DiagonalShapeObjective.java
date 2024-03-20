package model.objective;

import model.enums.Element;
import model.placementArea.*;

public class DiagonalShapeObjective implements Objective{
    private Element element;
    private Shape shape;

    @Override
    public int countObjectivePoints(PlacementArea placementArea) {return 2 * placementArea.verifyObjective(shape, element);}
}
