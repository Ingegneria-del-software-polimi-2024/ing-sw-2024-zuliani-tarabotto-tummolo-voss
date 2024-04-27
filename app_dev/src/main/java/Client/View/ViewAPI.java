package Client.View;

import SharedWebInterfaces.Messages.ViewAPI_Interface;
import model.PointsStrategy.Points;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;

/**
 * ViewAPI Class contains:
 *   - methods called after user input to modify the view or the model
 *   - methods used by the server interface to modify the view
 */
public class ViewAPI implements ViewAPI_Interface {
    private ArrayList<CardApi> hand;
    private HashMap<Point, CardApi> placementArea;
    private int[] goldDeck;
    private int[] resourceDeck;
    private int secretObjective;
    private int[] commonObjectives;
    private String pawn;
    private int playerPoints;
    private String state;

    public void setInitialData (int[] goldDeck, int[] resourceDeck, int[] commonObjectives, int[] serverHand) {
        this.commonObjectives = commonObjectives;
        this.goldDeck = goldDeck;
        this.resourceDeck = resourceDeck;
        for(int i = 0; i < serverHand.length; i ++) {
            hand.add(new CardApi(String.valueOf(serverHand[i]), true));
        }
    }

    public void setPawn(String color) {
        this.pawn = color;
    }

    public void addCardToHand(String cardId) {
        hand.add(new CardApi(cardId, true));
    }
    public void removeCardFromHand(String cardId) {
        for(CardApi card: hand){
            if(card.getId() == cardId){ hand.remove(card);}
        }
    }

    public void addCardToPlacementArea(String cardId, boolean face, int x, int y){
        placementArea.put( new Point(x,y), new CardApi(cardId, face));
    }

    public void setPlayerPoints(int points) {
        this.playerPoints = points;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

}
