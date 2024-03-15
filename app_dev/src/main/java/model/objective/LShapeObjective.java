package model.objective;

import model.enums.Element;
import model.placementArea.PlacementArea;

public class LShapeObjective {
    private Element element;

    public int countObjectivePoints(PlacementArea placementArea) {
        switch (element){
            case INSECTS:
                return 3 * placementArea.verifyObjective(Shape.TopLeftL, element);
            case ANIMALS:
                return 3 * placementArea.verifyObjective(Shape.TopRightL, element);
            case MUSHROOMS:
                return 3 * placementArea.verifyObjective(Shape.BottomRightL, element);
            case VEGETALS:
                return 3 * placementArea.verifyObjective(Shape.BottomLeftL, element);
        }
        return 0;
    }
}