package model.player;

//import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.PlayableDeck;
import model.placementArea.*;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String nickname;
    private List<PlayableCard> hand;
    //private ObjectiveCard secretObjective;
    private PlacementArea placementArea;
    private int points;
    private PlayableCard startCard;

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

    public void setStartCard(PlayableCard startCard) {
        this.startCard = startCard;
    }
    public PlayableCard getStartCard() {
        return this.startCard;
    }

    public List<PlayableCard> getPlayingHand(){return hand;}

    //auxiliary method for the method playCard() removes and returns
    //card from the hand
    private PlayableCard takeFromHand(PlayableCard card){
        hand.remove(card);
        return card;
    }
}
