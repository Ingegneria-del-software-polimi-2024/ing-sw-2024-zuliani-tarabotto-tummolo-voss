package model.objective;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.*;

import java.util.List;

/**
 * objective in a diagonal shape composed of three cards identified by their relative coordinates
 * referred to the one on the right
 */
public class DiagonalShapeObjective implements Objective{
    /**
     * The Element.
     */
    @JsonProperty("elements")
    protected List<Element> element;

    /**
     * The Shape.
     */
    @JsonProperty("shape")
    private Shape shape;

    /**
     * This method counts the objective points based on the placementArea.
     * It returns twice the result of verifying the objective with the given shape and elements.
     *
     * @param placementArea the disposition to find objectives in
     * @return the number of points related to this objective
     */
    @Override
    public int countObjectivePoints(PlacementArea placementArea) {return 2 * placementArea.verifyObjective(shape, element);}

    /**
     * This method returns the first element of the objective.
     *
     * @return the first element
     */
    @Override
    public Element getElement() {
        return element.get(0);
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
     *
     * @return the shape
     */
    @Override
    public Shape getShape() {
        return shape;
    }
}

