package Client.View;

import SharedWebInterfaces.ViewAPI_Interface;
import model.cards.Card;
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
    private HashMap<Coordinates, CardApi> placementArea;
    private CardApi starterCard;
    private int[] goldDeck;
    private int[] resourceDeck;
    private int[] starterDeck;
    private int[] objectiveDeck;
    private int secretObjective;
    private int[] commonObjectives;
    private int[] chooseSecretObjectives;
    private String pawn;
    private int playerPoints;
    private String state;
    private String[] players;
    private String gameId;
    private HashMap<Artifact, Integer> availableArtifacts;
    private HashMap<Element, Integer> availableElements;
    private String playerId;// the id of THIS client

    public ViewAPI() {
        for(Element el : Element.values()) {
            availableElements.put(el, 0);
        }
        for(Artifact ar : Artifact.values()){
            availableArtifacts.put(ar, 0);
        }
    }



    /////////// FROM CLIENT ACTIONS ////////////////////////////////////////////////////////////////////////////////////

    /////////// FROM SERVER ACTIONS ////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void setState(String state) { this.state = state; }

    @Override
    public void setGoldDeck(int[] deck){goldDeck = deck;}

    @Override
    public void setResourceDeck(int[] deck){resourceDeck = deck;}

    @Override
    public void setStarterDeck(int[] deck){starterDeck = deck;}

    @Override
    public void setObjectiveDeck(int[] deck){objectiveDeck = deck;}

    @Override
    public void setPlayers(String[] players){ this.players = players;}

    @Override
    public void setGameId(String gameId) { this.gameId = gameId;}

    //the player is given his starterCard, he will then have to place it
    @Override
    public void setStarterCard(int starterCard){
        //placeable is set to false because for starterCard it doesn't matter
        this.starterCard = new CardApi(starterCard, true, false);
    }

    //the player is given the two ObjectiveCard cards from which he can choose his secretObjective
    @Override
    public void chooseSecretObjective(int obj1, int obj2){
        chooseSecretObjectives[0] = obj1;
        chooseSecretObjectives[1] = obj2;
    }

    //the player chooses his secretObjective
    @Override
    public void setSecretObjective(int secretObjective, String player){
        if(this.playerId == player){
            this.secretObjective = secretObjective;
        }else{
        //TODO: BROADCAST
        }
    }

    @Override
    public void setPlaceableCard(int card, boolean placeable) {
        for(CardApi c : hand){
            if(c.getId() == card) c.setPlaceable(placeable);
        }
    }

    @Override
    public void addCardToDisposition(int card, Coordinates coordinates, boolean faceSide) {
        for(CardApi c : hand){
            if(c.getId() == card){
                placementArea.put(coordinates, c);
                hand.remove(c);
                return;
            }
        }
    }

    //the player's points are updated
    @Override
    public void setPoints(int points) {
        this.playerPoints = points;
    }

    @Override
    public void updateArtifacts(HashMap<Artifact, Integer> artifacts) {
        for(Artifact a : artifacts.keySet()){
            availableArtifacts.put(a, artifacts.get(a));
        }
    }

    @Override
    public void updateElements(HashMap<Element, Integer> elements) {
        for(Element e : elements.keySet()){
            availableElements.put(e, elements.get(e));
        }
    }

    @Override
    public void updateHand(String player, int lastDrawnCard) {
        if(this.playerId == player){
            hand.add(new CardApi(lastDrawnCard, false, false));
        }else{

        //TODO: BROADCAST
        }
    }

    @Override
    public void updateCardSource(int cardSource) {
        //TODO: fare extract sulla cardSource specificata 
    }

    @Override
    public void endGame(HashMap<String, Integer> finalPoints) {

    }

}
