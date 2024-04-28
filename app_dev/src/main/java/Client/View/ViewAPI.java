package Client.View;

import SharedWebInterfaces.ViewAPI_Interface;

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
    private int[] starterDeck;
    private int[] objectiveDeck;
    private int secretObjective;
    private int[] commonObjectives;
    private String pawn;
    private int playerPoints;
    private String state;

    //suggestion: use a hashmap to map player's names to points
    //private HashMap<String, Integer> players;

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

    public void setGoldDeck(int[] deck){goldDeck = deck;}
    public void setResourceDeck(int[] deck){resourceDeck = deck;}
    public void setStarterDeck(int[] deck){starterDeck = deck;}
    public void setObjectiveDeck(int[] deck){objectiveDeck = deck;}
    public void setPlayers(String[] players){}
    public void setStarterCard(int starterCard){}
    public void chooseSecretObjective(int obj1, int obj2){}
    public void setSecretObjective(int secretObjective, String player){this.secretObjective = secretObjective;}

}
