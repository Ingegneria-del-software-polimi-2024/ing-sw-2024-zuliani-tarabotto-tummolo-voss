package model.player;

import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.placemetArea.*;

import java.util.List;

public class Player {
    private String nickname;
    private List<PlayableCard> hand;
    private ObjectiveCard secretObjective;
    private PlacementArea placementArea;
    private int points;

    public void drawCard(PlayableCard card){
        //takes a card and puts it in the hand
    }

    //takes the card from the hand and places it in the placementArea
    public void playCard(PlayableCard card, Coordinates coordinates){
        takeFromHand(card);
        placementArea.addCard(card, coordinates);
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

    public List<PlayableCard> getPlayingHand(){return hand;}

    //auxiliary method for the method playCard() removes and returns
    //card from the hand
    private PlayableCard takeFromHand(PlayableCard card){
        hand.remove(card);
        return card;
    }
}
