package model.objective;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.PlacementArea;

/**
 * objective based on the number of elements of the tipe @element present on the placementArea
 */
@JsonTypeName("ElementObjective")
public class ElementObjective implements Objective{

    /**
     * The Element of the objective.
     */
    @JsonProperty("element")
    private Element element;

    /**
     * This method counts the objective points based on the placementArea.
     * It returns twice the quotient of the number of elements of the specified type divided by 3.
     *
     * @param placementArea the disposition to find objectives in
     * @return the number of points related to this objective
     */
    public int countObjectivePoints(PlacementArea placementArea){return 2*(placementArea.getNumberElements(element)/3);}

    /**
     * This method returns the element of the objective.
     *
     * @return the element
     */
    @Override
    public Element getElement() {
        return element;
    }

    /**
     * This method returns the artifact of the objective.
     * In this class, it always returns null.
     *
     * @return null
     */
    @Override
    public Artifact getArtifact() {
        return null;
    }

    /**
     * This method returns the shape of the objective.
     * In this class, it always returns null.
     *
     * @return null
     */
    @Override
    public Shape getShape() {
        return null;
    }

}
