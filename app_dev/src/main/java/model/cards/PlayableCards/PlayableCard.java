package model.cards.PlayableCards;

import model.PointsStrategy.Points;
import model.cards.Card;

import java.io.Serializable;
import java.util.*;

import model.enums.*;
import model.placementArea.PlacementArea;

public abstract class PlayableCard implements Card {
    private String type;
    protected int id;
    protected boolean faceSide;
    protected List<Corner> corners;
    protected Points points;
    protected List<Corner> backCorners;//necessary for TUI


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
        //NECESSARY FOR TUI PRINTING
        if(!getFaceSide()){
            for (Corner corner : backCorners) {
                if (corner.getId() == index) {
                    return corner;
                }
            }
            //return new Corner();
        } //return new Corner();
        // Iterate through the corners list and find the corner with the matching ID
        for (Corner corner : corners) {
            if (corner.getId() == index) {
                return corner;
            }
        }
        // If no corner with the given ID is found, return null
        return null;
    }

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

    public Points getPoints(){
        return points;
    }

    /**
     * this function is only auxiliary for the TUI
     */
    public void buildBackCorners(){
        backCorners = new ArrayList<>();
        for(int i = 0; i<4; i++){
            backCorners.add(new Corner());
        }
        backCorners.get(0).setId(0);
        backCorners.get(1).setId(1);
        backCorners.get(2).setId(2);
        backCorners.get(3).setId(3);
    }




    ////////////////////// TESTING RELATED METHODS ONLY //////////////////////
    public abstract void printCard();
}

