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

@JsonTypeName("LShapeObjective")
/**
 * objective in L shape composed of three cards identified by their relative coordinates
 * referred to the card standing alone
 */
public class LShapeObjective implements Objective{
    @JsonProperty("elements")
    public List<Element> element;

    @JsonProperty("shape")
    private Shape shape;

    /**
     * @param placementArea the disposition to find objectives in
     * @return the number of points rellated to this objective
     */
    public int countObjectivePoints(PlacementArea placementArea) {return 3 * placementArea.verifyObjective(shape, element);}

    /**
     *
     * @return a ordered list of elements that compose the objective
     */

    @Override
    public void printObjective() {
        System.out.println("type: LShapeObjective");
        System.out.println("element: " + this.element.toString());
        System.out.println("shape: " + this.shape.toString());
    }

    /**
     * This method returns the second element in the list of elements for this objective.
     * @return The second Element in the list of elements for this objective.
     */
    @Override
    public Element getElement() {
        return element.get(1);
    }

    /**
     * This method returns null as it is not applicable for this class.
     * @return null
     */
    @Override
    public Artifact getArtifact() {
        return null;
    }

    /**
     * This method returns the shape for this objective.
     * @return The Shape for this objective.
     */
    @Override
    public Shape getShape() {
        return shape;
    }

}