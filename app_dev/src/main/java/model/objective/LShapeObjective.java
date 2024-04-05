package model.objective;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.enums.Element;
import model.placementArea.*;
@JsonTypeName("LShapeObjective")

public class LShapeObjective implements Objective{
    @JsonProperty("element")//riferito alle due carte -> aggiungere codice per inizializzazione
    private Element element;
    @JsonProperty("shape")
    private Shape shape;


    public int countObjectivePoints(PlacementArea placementArea) {return 3 * placementArea.verifyObjective(shape, element);}

    @Override
    public void printObjective() {
        System.out.println("type: LShapeObjective");
        System.out.println("element: " + this.element.toString());
        System.out.println("shape: " + this.shape.toString());
    }
}