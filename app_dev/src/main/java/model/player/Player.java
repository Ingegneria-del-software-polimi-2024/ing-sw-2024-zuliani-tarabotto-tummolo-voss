package model.player;

//import model.cards.ObjectiveCard;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.cards.PlayableCards.StarterCard;
import model.deckFactory.PlayableDeck;
import model.enums.Pawn;
import model.placementArea.*;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String nickname;
    private List<PlayableCard> hand;
    private ObjectiveCard secretObjective;
    private PlacementArea placementArea;
    private int points;
    private PlayableCard starterCard;
    private Pawn pawnColor;

    public Player() {
        //constructor
        this.hand = new ArrayList<>();
        this.placementArea = new PlacementArea();
    }
    public void drawCard(PlayableCard card){
        //takes a card and puts it in the hand
        hand.add(card);
    }

    //takes the card from the hand and places it in the placementArea
    //updates the player's points score based on the card placed
    public void playCard(PlayableCard card, Coordinates coordinates){
        takeFromHand(card);
        this.points += placementArea.addCard(coordinates, card);
        return;
    }

    public void playStarterCard(){
        placementArea.addCard(starterCard);
    }
    public void flipHand(){}
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public String getNickname() {
        return nickname;
    }
    public int getPoints() {
        return points;
    }

    public void setStarterCard(PlayableCard starterCard) {
        this.starterCard = starterCard;
    }
    public PlayableCard getStarterCard() {
        return this.starterCard;
    }

    public List<PlayableCard> getPlayingHand(){return hand;}

    public PlacementArea getPlacementArea() {
        return placementArea;
    }

    public void setSecretObjective(ObjectiveCard secretObjective) {
        this.secretObjective = secretObjective;
    }

    public void setPawnColor(Pawn pawnColor) { this.pawnColor = pawnColor; }

    public ObjectiveCard getSecretObjective() {
        return secretObjective;
    }

    //auxiliary method for the method playCard() removes and returns
    //card from the hand
    private PlayableCard takeFromHand(PlayableCard card){
        hand.remove(card);
        return card;
    }

    //calculates the total points of a single player's secreteObjective cards
    //WHY? SECRET OBJECTIVE IS JUST ONE CARD, WHY USE A LIST
    public void calculateSecretObj(){
        points = points + secretObjective.countPoints(placementArea);
    }

    //calculates the points of a single commonObjective card
    public void calculateSingleCommonObj(ObjectiveCard objectiveCard) {
        points = points + objectiveCard.countPoints(placementArea);
    }
}

