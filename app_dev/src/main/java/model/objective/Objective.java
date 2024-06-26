package model.objective;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.PlacementArea;

import java.io.Serializable;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

/**
 * inteface used for the objectives strategy pattern: identifies the right way to count points
 */
public interface Objective extends Serializable {

    /**
     * counting points given by the objective
     */
    public int countObjectivePoints(PlacementArea placementArea);

    /**
     * This method returns the element for this objective.
     *
     * @return The Element for this objective.
     */
    public Element getElement();

    /**
     * This method returns the artifact for this objective.
     *
     * @return The Artifact for this objective.
     */
    public Artifact getArtifact();

    /**
     * This method returns the shape for this objective.
     *
     * @return The Shape for this objective.
     */
    public Shape getShape();
}
