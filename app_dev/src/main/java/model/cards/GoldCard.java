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



    public GoldCard (char id) {
        //json parsing
    }


    public int countPoints() {
        return this.pointsPolicy.count();
    };

    public boolean checkIfPlaceable () {
        // checks if the card can be placed anywhere in the placementArea
    };



}