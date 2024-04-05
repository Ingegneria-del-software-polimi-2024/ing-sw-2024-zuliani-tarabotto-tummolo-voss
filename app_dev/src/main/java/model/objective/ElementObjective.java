package model.objective;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.enums.Element;
import model.placementArea.PlacementArea;
@JsonTypeName("ElementObjective")
public class ElementObjective implements Objective{
    @JsonProperty("element")
    private Element element;

    public int countObjectivePoints(PlacementArea placementArea){return 2*(placementArea.getNumberElements(element)/3);}

    @Override
    public void printObjective() {
        System.out.println("type: ElementObjective");
        System.out.println(this.element.toString());
    }
}
