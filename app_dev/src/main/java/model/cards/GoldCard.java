package main.java.model.cards;
import main.java.model.Points.Points;
import main.java.model.enums.Element;

import java.util.HashMap;

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
    }



}