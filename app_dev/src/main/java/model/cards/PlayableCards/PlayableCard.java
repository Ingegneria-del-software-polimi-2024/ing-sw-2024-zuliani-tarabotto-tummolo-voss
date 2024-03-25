package model.cards.PlayableCards;

import model.cards.Card;
import java.util.Collections;
import java.util.List;
import model.enums.*;
import model.placementArea.PlacementArea;

public abstract class PlayableCard implements Card {
    private String type; //utile per parsing
    protected int id;
    protected boolean faceSide;
    protected List<Corner> corners;

    public void flipCard() {
        this.faceSide = !this.faceSide;
    }
    public Corner getCorner(int index) {
        //returns a void corner if the card is facedown
        if(!getFaceSide()) return new Corner();
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
    public boolean getFaceSide() {
        return faceSide;
    }

    public void setFaceSide(boolean faceSide) {this.faceSide = faceSide;}

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    //for gold and resource cards returns the element on the back of the cart
    //returns null for start cards
    public abstract Element getBlockedElement ();

    //for start card returns the array of blocked elements contained in the front face of the card
    //returns null for gold and resource cards
    public abstract Element[] getBlockedElements();

    //only needed for starter card, ignore for gold and resource card
    public abstract Element[] getBackFaceCorners();

    //calls a different count points method based on the points policy of the card
    public abstract int countPoints(PlacementArea placementArea);

    //function to print card onto console
    public abstract void printCard();



}

