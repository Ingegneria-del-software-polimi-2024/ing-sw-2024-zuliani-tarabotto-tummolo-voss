package model.objective;

import model.enums.Element;
import model.placemetArea.PlacementArea;

public class ElementObjective implements Objective{
    private Element element;

    public int countObjectivePoints(PlacementArea placementArea){return 2*(placementArea.getNumberElements(element)/3);}

}
