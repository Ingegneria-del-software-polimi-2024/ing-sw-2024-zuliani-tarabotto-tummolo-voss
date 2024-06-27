package model.objective;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.cards.PlayableCards.Corner;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.*;

import java.util.Arrays;
import java.util.List;



/**
 * objective in L shape composed of three cards identified by their relative coordinates
 * referred to the card standing alone
 */
@JsonTypeName("LShapeObjective")
public class LShapeObjective implements Objective{
    /**
     * The Element.
     */
    @JsonProperty("elements")
    public List<Element> element;

    /**
     * The Shape of the objective.
     */
    @JsonProperty("shape")
    private Shape shape;

    /**
     * This method counts the objective points based on the placementArea.
     * It returns thrice the result of verifying the objective with the given shape and elements.
     *
     * @param placementArea the disposition to find objectives in
     * @return the number of points related to this objective
     */
    public int countObjectivePoints(PlacementArea placementArea) {return 3 * placementArea.verifyObjective(shape, element);}

    /**
     * This method returns the second element of the objective.
     *
     * @return the second element
     */
    @Override
    public Element getElement() {
        return element.get(1);
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