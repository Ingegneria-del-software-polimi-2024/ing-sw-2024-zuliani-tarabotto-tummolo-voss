package model.cards.PlayableCards;

import model.cards.Card;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.enums.*;
import model.placementArea.PlacementArea;

public abstract class PlayableCard implements Card {
    private String type; //utile per parsing
    protected int id;
    protected boolean faceSide;
    protected List<Corner> corners;


    /**
     * based on the card's pointsPolicy the number of points granted by it is returned
     * @param placementArea PlacementArea
     * @return int
     */
    public abstract int countPoints(PlacementArea placementArea);


    //////////////// SETTER METHODS //////////////////////
    public void setFaceSide(boolean faceSide) {this.faceSide = faceSide;}



    //////////////// GETTER METHODS ///////////////////////////
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

    //IDK if we wanna keep this, this allows to get the placement constraints also for Resource Cards
    // (and Starter Cards but not useful)
    public Map<Element, Integer> getPlacementConstraint () {return null;}
    public String getType() { return type; }
    public int getId() { return id; }

    /**
     * For GoldCard and ResourceCard returns the Element on the back of the card.
     * Returns null for StarterCard
     */
    public abstract Element getBlockedElement ();

    /**
     * For StarterCard returns the array of blocked elements present on the front face of the card
     * Returns null for GoldCard and for ResourceCard
     */
    public abstract Element[] getBlockedElements();

    /**
     * Only needed for StarterCard, ignore for GoldCard and for ResourceCard
     */
    public abstract Element[] getBackFaceCorners();
    public List<Corner> getCorners() {
        return Collections.unmodifiableList(corners);
    }
    public boolean getFaceSide() {
        return faceSide;
    }



    ////////////////////// TESTING RELATED METHODS ONLY //////////////////////
    public abstract void printCard();
}

