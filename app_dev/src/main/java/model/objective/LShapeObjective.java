package model.objective;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.cards.PlayableCards.Corner;
import model.enums.Element;
import model.placementArea.*;

import java.util.Arrays;
import java.util.List;

@JsonTypeName("LShapeObjective")

public class LShapeObjective implements Objective{
    @JsonProperty("elements")
    public List<Element> element;

    @JsonProperty("shape")
    private Shape shape;

    public int countObjectivePoints(PlacementArea placementArea) {return 3 * placementArea.verifyObjective(shape, element);}

    public List<Element> getElement() {
        return element;
    }

}