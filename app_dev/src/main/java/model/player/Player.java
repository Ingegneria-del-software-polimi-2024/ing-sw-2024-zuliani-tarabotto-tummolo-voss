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
    private String nickname;
    private List<PlayableCard> hand;
    private ObjectiveCard secretObjective = null;
    private PlacementArea placementArea;
    private int points;
    private PlayableCard starterCard;
    private Pawn pawnColor;

    private Boolean active;
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
    //auxiliary method for the method playCard() removes and returns
    //card from the hand

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
     *@param commonObjective the objective to be verified
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
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setPoints(int points) { this.points = points; }
    public void setStarterCard(PlayableCard starterCard) { this.starterCard = starterCard; }
    public void setSecretObjective(ObjectiveCard secretObjective) { this.secretObjective = secretObjective; }
    public void setPawnColor(Pawn pawnColor) { this.pawnColor = pawnColor; }
    public void setActive() {
        synchronized (activityMutex) {
            this.active = true;
        }
    }
    public void setInactive(){
        synchronized (activityMutex){
            this.active = false;
        }
    }

    ///////////////// GETTER METHODS ///////////////////////////////////////
    public String getNickname() {return nickname;}
    public int getPoints() {return points;}
    public PlayableCard getStarterCard() {return this.starterCard;}
    public List<PlayableCard> getPlayingHand(){return hand;}
    public PlacementArea getPlacementArea() { return placementArea; }
    public ObjectiveCard getSecretObjective() { return secretObjective; }
    public Pawn getPawnColor(){return pawnColor;}
    public boolean isActive(){
        synchronized (activityMutex){
            return active;
        }
    }

}

