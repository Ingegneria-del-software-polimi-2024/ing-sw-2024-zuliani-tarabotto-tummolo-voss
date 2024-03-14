package model.cards;
import model.Points.Points;
import model.enums.Element;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GoldCard extends PlayableCard{
    private Element element;
    private Points pointsPolicy;
    private HashMap<Element, Integer> placementConstraint;


    /*
    public GoldCard (Element element, Point pointsPolicy, HashMap<Element, Integer> placementConstraint, char id, boolean faceSide, Corner[] corners) {
        super(id, faceSide, corners);
        this.element = element;
        this.pointsPolicy = pointsPolicy;
        this.placementConstraint = placementConstraint;
    }
    */

    public void setElement (Element element) {
        this.element = element;
    }

    public void setPointsPolicy(Points pointsPolicy) {
        this.pointsPolicy = pointsPolicy;
    }

    public void setPlacementConstraint (HashMap<Element, Integer> placementConstraint) {
        this.placementConstraint = placementConstraint;
    }

    public int countPoints() {
        return this.pointsPolicy.count();
    };

    public boolean checkIfPlaceble () {
        //////
    };



}