package model.cards.PlayableCards;

import model.PointsStrategy.Points;
import model.cards.Card;

import java.io.Serializable;
import java.util.*;

import model.enums.*;
import model.placementArea.PlacementArea;

/**
 * Represents an abstract PlayableCard which defines the basic structure and behavior
 * for all playable cards in the game. This class should be extended by specific types
 * of playable cards.
 */
public abstract class PlayableCard implements Card {
    /**
     * The type of the card.
     */
    private String type;

    /**
     * The unique identifier of the card.
     */
    protected int id;

    /**
     * Indicates if the card is face up (true) or face down (false).
     */
    protected boolean faceSide;

    /**
     * The list of corners on the front face of the card.
     */
    protected List<Corner> corners;

    /**
     * The points strategy associated with the card.
     */
    protected Points points;

    /**
     * The list of corners on the back face of the card, necessary for text-based user interface (TUI).
     */
    protected List<Corner> backCorners;

    /**
     * based on the card's pointsPolicy the number of points granted by it is returned
     *
     * @param placementArea PlacementArea
     * @return int int
     */
    public abstract int countPoints(PlacementArea placementArea);


    /**
     * Sets face side.
     *
     * @param faceSide the face side
     */
//////////////// SETTER METHODS //////////////////////
    public void setFaceSide(boolean faceSide) {this.faceSide = faceSide;}


    /**
     * Gets corner.
     *
     * @param index the index
     * @return the corner
     */
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

    /**
     * Gets placement constraint.
     *
     * @return the placement constraint
     */
    public Map<Element, Integer> getPlacementConstraint () {return null;}

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() { return type; }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() { return id; }

    /**
     * For GoldCard and ResourceCard returns the Element on the back of the card.
     * Returns null for StarterCard
     *
     * @return the blocked element
     */
    public abstract Element getBlockedElement ();

    /**
     * For StarterCard returns the array of blocked elements present on the front face of the card
     * Returns null for GoldCard and for ResourceCard
     *
     * @return the element [ ]
     */
    public abstract Element[] getBlockedElements();

    /**
     * Only needed for StarterCard, ignore for GoldCard and for ResourceCard
     *
     * @return the element [ ]
     */
    public abstract Element[] getBackFaceCorners();

    /**
     * Gets corners.
     *
     * @return the corners
     */
    public List<Corner> getCorners() {
        return Collections.unmodifiableList(corners);
    }

    /**
     * Gets face side.
     *
     * @return the face side
     */
    public boolean getFaceSide() {
        return faceSide;
    }

    /**
     * Get points points.
     *
     * @return the points
     */
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
    //public abstract void printCard();
}

