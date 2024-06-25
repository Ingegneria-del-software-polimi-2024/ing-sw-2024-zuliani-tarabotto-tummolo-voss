package model.objective;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.PlacementArea;

/**
 * The type Element objective.
 */
@JsonTypeName("ElementObjective")
/**
 * objective based on the number of elements of the tipe @element present on the placementArea
 */
public class ElementObjective implements Objective{
    @JsonProperty("element")
    private Element element;

    /**
     * @param placementArea the disposition to find objectives in
     * @return the number of points rellated to this objective
     */
    public int countObjectivePoints(PlacementArea placementArea){return 2*(placementArea.getNumberElements(element)/3);}

    @Override
    public void printObjective() {
        System.out.println("type: ElementObjective");
        System.out.println(this.element.toString());
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public Artifact getArtifact() {
        return null;
    }

    @Override
    public Shape getShape() {
        return null;
    }

}
