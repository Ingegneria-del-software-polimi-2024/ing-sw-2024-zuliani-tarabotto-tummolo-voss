package model.Player;

import model.cards.ObjectiveCard;
import model.cards.PlayableCard;
import model.placementArea.Coordinates;
import model.placementArea.PlacementArea;

import java.util.List;
import java.util.Set;

public class Player {
    private String nickname;
    private List<PlayableCard> hand;
    private ObjectiveCard secretObjective;
    private PlacementArea placementArea;
    private int points;

    public PlayableCard drawGoldCard(PlayableCard card){}
    public PlayableCard drawResourceCard(PlayableCard card){}
    public void playCard(PlayableCard card, Coordinates coordinates){}
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


}
