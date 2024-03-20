package model.cards.PlayableCards;

import model.cards.Card;
import java.util.Collections;
import java.util.List;
import model.enums.*;
import model.placementArea.PlacementArea;

public abstract class PlayableCard implements Card {
    private String type;//utile per parsing
    protected int id;
    protected boolean faceSide;
    protected List<Corner> corners;

    public void flipCard() {
        this.faceSide = !this.faceSide;
    }
    public Corner getCorner(int index) {
        // Iterate through the corners list and find the corner with the matching ID
        for (Corner corner : corners) {
            if (corner.getId() == index) {
                return corner;
            }
        }

        // If no corner with the given ID is found, return null
        return null;
    }
    public List<Corner> getCorners() {
        return Collections.unmodifiableList(corners);
    }
    public boolean isFaceSide() {
        return faceSide;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public abstract Element getBlockedElement ();

    //calls a different count points method based on the points policy of the card
    public abstract int countPoints(PlacementArea placementArea);



}

