package model.objective;

import model.enums.Element;
import model.placementArea.PlacementArea;

public class DiagonalShapeObjective implements Objective{
    private Element element;

    @Override
    public int countObjectivePoints(PlacementArea placementArea) {
        if(element.equals(Element.MUSHROOMS) || element.equals(Element.ANIMALS)){
            return 2 * placementArea.verifyObjective(Shape.AscendingDiagonal, element);
        }else{
            return 2 * placementArea.verifyObjective(Shape.DescendingDiagonal, element);
        }
    }
}
