package model.objective;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.PlacementArea;

import java.io.Serializable;
import java.util.List;

/**
 * The interface Objective.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
/**
 * inteface used for the objectives strategy pattern: identifies the right way to count points
 */
public interface Objective extends Serializable {
    /**
     * counting points given by the objective
     *
     * @param placementArea the placement area
     * @return the int
     */
    public int countObjectivePoints(PlacementArea placementArea);

    /**
     * Print objective.
     */
    public void printObjective();

    /**
     * Gets element.
     *
     * @return the element
     */
    public Element getElement();

    /**
     * Gets artifact.
     *
     * @return the artifact
     */
    public Artifact getArtifact();

    /**
     * Gets shape.
     *
     * @return the shape
     */
    public Shape getShape();
}
