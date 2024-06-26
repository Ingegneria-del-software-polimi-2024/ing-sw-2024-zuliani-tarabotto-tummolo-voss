package model.player;

import model.Exceptions.CantPlaceCardException;
import model.Exceptions.KickOutOfGameException;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Pawn;
import model.placementArea.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {

    /**
     * the nickname of the player
     */
    private String nickname;

    /**
     * the hand of the player
     */
    private List<PlayableCard> hand;

    /**
     * the secret objective of the player
     */
    private ObjectiveCard secretObjective = null;

    /**
     * the placement area of the player
     */
    private PlacementArea placementArea;

    /**
     * the points of the player
     */
    private int points;

    /**
     * the starter card of the player
     */
    private PlayableCard starterCard;

    /**
     * the color of the pawn of the player
     */
    private Pawn pawnColor;

    /**
     * the activity status of the player
     */
    private Boolean active;

    /**
     * the mutex for the activity status of the player
     */
    private final Object activityMutex = new Object();


    /**
     * class constructor
     */
    public Player() {
        this.hand = new ArrayList<>();
        this.placementArea = new PlacementArea();
        this.active = true;
    }

    /**
     * adds the PlayableCard card to the hand of the Player
     * card is selected from one of the available card sources on the CommonTable(goldDeck etc..)
     * @param card
     */
    public void drawCard(PlayableCard card){
        hand.add(card);
    }

    /**
     * takes the card from the hand and places it on the placementArea at the specified coordinates
     * it then updates the Player's points based on the card placed
     * @param card
     * @param coordinates
     */
    public void playCard(PlayableCard card, Coordinates coordinates) throws CantPlaceCardException {
        this.points += placementArea.addCard(coordinates, card);
        takeFromHand(card);
    }

    /**
     * the starterCard is placed on the PlacementsArea at the default position (0,0)
     */
    public void playStarterCard() throws KickOutOfGameException {
        placementArea.addCard(starterCard);
    }

    /**
     * auxiliary method for the playCard() method, removes and returns PlayableCard card from the hand
     * @param card
     * @return
     */
    private PlayableCard takeFromHand(PlayableCard card){
        hand.remove(card);
        return card;
    }

    /**
     * checks if the Player's secretObjective was satisfied at least once and if so the points counter is updated
     * @return 1 if the objective is satisfied at least once, else 0
     */
    public int calculateSecretObj(){

        int objP = 0;
        if(secretObjective!= null)
            objP += secretObjective.countPoints(placementArea);

        points = points + objP;

        if (objP>0)
            return 1;
        else
            return 0;
    }

    /**
     * checks if the ObjectiveCard commonObjective was satisfied at least once and if so the points counter is updated
     * @param commonObjective the objective to be verified
     * @return 1 if the objective is satisfied at least once, else 0
     */
    public int calculateSingleCommonObj(ObjectiveCard commonObjective) {
        int objPoints = commonObjective.countPoints(placementArea);
        points = points + objPoints;
        if (objPoints>0)
            return 1;
        else
            return 0;
    }

    /////////////// SETTER METHODS ///////////////////////////

    /**
     * sets the nickname of the player
     * @param nickname
     */
    public void setNickname(String nickname) { this.nickname = nickname; }

    /**
     * sets the points of the player
     * @param points
     */
    public void setPoints(int points) { this.points = points; }

    /**
     * sets the starterCard of the player
     * @param starterCard
     */
    public void setStarterCard(PlayableCard starterCard) { this.starterCard = starterCard; }

    /**
     * sets the secretObjective of the player
     * @param secretObjective
     */
    public void setSecretObjective(ObjectiveCard secretObjective) { this.secretObjective = secretObjective; }

    /**
     * sets the color of the pawn of the player
     * @param pawnColor
     */
    public void setPawnColor(Pawn pawnColor) { this.pawnColor = pawnColor; }

    /**
     * sets the activity status of the player to active
     */
    public void setActive() {
        synchronized (activityMutex) {
            this.active = true;
        }
    }

    /**
     * sets the activity status of the player to inactive
     */
    public void setInactive(){
        synchronized (activityMutex){
            this.active = false;
        }
    }

    ///////////////// GETTER METHODS ///////////////////////////////////////

    /**
     * returns the nickname of the player
     * @return nickname
     */
    public String getNickname() {return nickname;}

    /**
     * returns the points of the player
     * @return points
     */
    public int getPoints() {return points;}

    /**
     * returns the starterCard of the player
     * @return starterCard
     */
    public PlayableCard getStarterCard() {return this.starterCard;}

    /**
     * returns the hand of the player
     * @return hand
     */
    public List<PlayableCard> getPlayingHand(){return hand;}

    /**
     * returns the placementArea of the player
     * @return placementArea
     */
    public PlacementArea getPlacementArea() { return placementArea; }

    /**
     * returns the secretObjective of the player
     * @return secretObjective
     */
    public ObjectiveCard getSecretObjective() { return secretObjective; }

    /**
     * returns the color of the pawn of the player
     * @return pawnColor
     */
    public Pawn getPawnColor(){return pawnColor;}

    /**
     * returns the activity status of the player
     * @return active
     */
    public boolean isActive(){
        synchronized (activityMutex){
            return active;
        }
    }

}

