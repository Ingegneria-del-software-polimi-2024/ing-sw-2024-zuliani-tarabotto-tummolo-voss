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

    /**
     * The element type for this objective.
     */
    @JsonProperty("element")
    private Element element;

    /**
     * @param placementArea the disposition to find objectives in
     * @return the number of points rellated to this objective
     */
    public int countObjectivePoints(PlacementArea placementArea){return 2*(placementArea.getNumberElements(element)/3);}

    /**
     * This method returns the element type for this objective.
     *
     * @return The element type for this objective.
     */
    @Override
    public Element getElement() {
        return element;
    }

    /**
     * This method returns null as it is not applicable for this class.
     *
     * @return null
     */
    @Override
    public Artifact getArtifact() {
        return null;
    }

    /**
     * This method returns null as it is not applicable for this class.
     *
     * @return null
     */
    @Override
    public Shape getShape() {
        return null;
    }

}
