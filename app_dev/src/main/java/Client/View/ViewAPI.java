package Client.View;

import SharedWebInterfaces.ViewAPI_Interface;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.Coordinates;

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

    
    @Override
    public void setState(String state) {
        this.state = state;
    }
    @Override
    public void setGoldDeck(int[] deck){goldDeck = deck;}
    @Override
    public void setResourceDeck(int[] deck){resourceDeck = deck;}
    @Override
    public void setStarterDeck(int[] deck){starterDeck = deck;}
    @Override
    public void setObjectiveDeck(int[] deck){objectiveDeck = deck;}
    @Override
    public void setPlayers(String[] players){}
    @Override
    public void setGameId(String gameId) {

    }
    @Override
    public void setStarterCard(int starterCard){}
    @Override
    public void chooseSecretObjective(int obj1, int obj2){}
    @Override
    public void setSecretObjective(int secretObjective, String player){this.secretObjective = secretObjective;}
    @Override
    public void setPlaceableCard(int card, boolean placeable) {
    }
    @Override
    public void addCardToDisposition(int card, Coordinates coordinates, boolean faceside) {
    }
    @Override
    public void setPoints(int points) {
    }
    @Override
    public void updateArtifacts(HashMap<Artifact, Integer> artifacts) {
    }
    @Override
    public void updateElements(HashMap<Element, Integer> elements) {

    }
    @Override
    public void updateHand(String player, int[] hand) {

    }
    @Override
    public void updateCardSource(int cardSource) {

    }
    @Override
    public void endGame(HashMap<String, Integer> finalPoints) {

    }

}
