package model.player;

//import model.cards.ObjectiveCard;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.cards.PlayableCards.StarterCard;
import model.deckFactory.PlayableDeck;
import model.placementArea.*;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String nickname;
    private List<PlayableCard> hand;
    private List<ObjectiveCard> secretObjective;
    private PlacementArea placementArea;
    private int points;
    private PlayableCard starterCard;

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

    //auxiliary method for the method playCard() removes and returns
    //card from the hand
    private PlayableCard takeFromHand(PlayableCard card){
        hand.remove(card);
        return card;
    }

    //calculates the total points of a single player's secreteObjective cards
    public void calculateSecretObj(){

        int i;

        for(i=0; i< secretObjective.size(); i++){

            points = points + secretObjective.get(i).countPoints(placementArea);
        }
    }

    //calculates the points of a single commonObjective card
    public void calculateSingleCommonObj(ObjectiveCard objectiveCard) {

        points = points + objectiveCard.countPoints(placementArea);
    }
}

