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
    @JsonProperty("shape")
    private Shape shape;

    /**
     *
     * @param placementArea the disposition to find objectives in
     * @return the number of points rellated to this objective
     */
    @Override
    public int countObjectivePoints(PlacementArea placementArea) {return 2 * placementArea.verifyObjective(shape, element);}


    @Override
    public Element getElement() {
        return element.get(0);
    }

    @Override
    public Artifact getArtifact() {
        return null;
    }

    @Override
    public Shape getShape() {
        return shape;
    }
}

