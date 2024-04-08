package model.objective;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.enums.Element;
import model.placementArea.*;

import java.util.List;

public class DiagonalShapeObjective implements Objective{
    @JsonProperty("elements")
    protected List<Element> element;
    @JsonProperty("shape")
    private Shape shape;

    @Override
    public int countObjectivePoints(PlacementArea placementArea) {return 2 * placementArea.verifyObjective(shape, element);}

    @Override
    public void printObjective() {
        System.out.println("type: DiagonalShapeObjective");
        System.out.println(this.element.toString());
        System.out.println(this.shape.toString());
    }
}
