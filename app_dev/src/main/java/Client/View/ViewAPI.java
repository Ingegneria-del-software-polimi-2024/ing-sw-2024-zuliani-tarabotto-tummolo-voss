package Client.View;

import SharedWebInterfaces.ServerControllerInterface;
import SharedWebInterfaces.ViewAPI_Interface;
import model.GameState.TurnState;
import model.cards.Card;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;
import model.objective.Objective;
import model.placementArea.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;
import java.util.List;

/**
 * ViewAPI Class contains:
 *   - methods called after user input to modify the view or the model
 *   - methods used by the server interface to modify the view
 */
public class ViewAPI implements ViewAPI_Interface {
    private List<PlayableCard> hand;
    private PlayableCard starterCard;
    private List<PlayableCard> goldDeck;
    private List<PlayableCard> resourceDeck;
    private ObjectiveCard secretObjective;
    private List<ObjectiveCard> commonObjectives;
    private List<ObjectiveCard> chooseSecretObjectives;
    private List<PlayableCard> openGold;
    private List<PlayableCard> openResource;
    private TurnState state;
    private String[] players;
    private String gameId;
    private HashMap<Artifact, Integer> availableArtifacts;
    private HashMap<Element, Integer> availableElements;
    private String playerId;// the id of THIS client
    private String pawnColor;
    private List<Coordinates> availablePlaces;
    private boolean[] canBePlaced;
    private HashMap< String, HashMap< Coordinates, PlayableCard> > dispositions;
    //the points of all players are store here
    private HashMap< String , Integer> points;
    private boolean myTurn;
    private UI ui;
    private ServerControllerInterface controller;

    public ViewAPI(UI ui, ServerControllerInterface controller) {
        for(Element el : Element.values()) {
            availableElements.put(el, 0);
        }
        for(Artifact ar : Artifact.values()){
            availableArtifacts.put(ar, 0);
        }
        this.ui = ui;
        this.controller = controller;
    }

    /////////// from CLIENT to SERVER  ACTIONS ////////////////////////////////////////////////////////////////////////////////////

    //all this methods call the methods contained in ModelController in order to modify the model
    public void playStarterCard(){
        controller.playStarterCard(starterCard.getFaceSide(), playerId);
    }

    public void chooseSecretObjective(String chosenObjective){
        controller.chooseSecretObjective(chosenObjective, playerId);
    }

    public void playCard(PlayableCard c, int x, int y) {
        controller.playCard(c.getId(), x, y, c.getFaceSide());
    }

    public void drawCard(int cardSource){
        controller.drawCard(cardSource);
    }

    /////////// from SERVER to CLIENT ACTIONS ////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void setState(TurnState state) {
        this.state = state;
        this.state.display(this, ui);
    }

    @Override
    public void setGoldDeck(List<PlayableCard> deck){goldDeck = deck;}

    @Override
    public void setResourceDeck(List<PlayableCard> deck){resourceDeck = deck;}


    @Override
    public void setPlayers(String[] players){
        this.players = players;

        //after we receive the array containing the unique nicknames of the players, we initialize
        //the two hashmaps containing information about all players
        this.dispositions = new HashMap<>();
        this.points = new HashMap<>();
        for(String p : players){
            dispositions.put(p, new HashMap<>());
            points.put(p, 0);
        }
    }

    @Override
    public void setGameId(String gameId) { this.gameId = gameId;}

    //the player is given his starterCard, he will then have to place it
    @Override
    public void setStarterCard(PlayableCard starterCard){
        //placeable is set to false because for starterCard it doesn't matter
        this.starterCard = starterCard;
    }

    @Override
    public void setHand(List<PlayableCard> hand){
        this.hand = hand;
    }

    //the player is given the two ObjectiveCard cards from which he can choose his secretObjective
    @Override
    public void chooseSecretObjective(ObjectiveCard obj1, ObjectiveCard obj2){
        chooseSecretObjectives.add(0, obj1);
        chooseSecretObjectives.add(1, obj2);
    }

    //the player chooses his secretObjective
    @Override
    public void setSecretObjective(ObjectiveCard secretObjective){
        this.secretObjective = secretObjective;
    }



    //the player's points are updated
    @Override
    public void setPoints(String player, int points) {
        //
        this.points.put(player, points);
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

    //TODO: togliere sta cagata
    @Override
    public void updateCardSource(List<PlayableCard> deck, int cardSource) {

        switch (cardSource) {
            case 1:
                goldDeck = deck;
                break;
            case 2:
                resourceDeck = deck;
                break;
            case 3:
                openGold = deck;
                goldDeck.remove(0);
                break;
            case 4:
                openGold = deck;
                goldDeck.remove(0);
                break;
            case 5:
                openResource = deck;
                openResource.remove(0);
                break;
            case 6:
                openResource = deck;
                openResource.remove(0);
                break;
        }

    }

    @Override
    public void endGame(HashMap<String, Integer> finalPoints) {

    }

    @Override
    public void setPawnColor(String pawnColor) {
        this.pawnColor = pawnColor;
    }

    @Override
    public void setCommonObjectives(ObjectiveCard commonObjective1, ObjectiveCard commonObjective2){
        commonObjectives.add(commonObjective1);
        commonObjectives.add(commonObjective2);
    }

    @Override
    public void setOpenGold(List<PlayableCard> openGold){
        this.openGold = openGold;
    }

    @Override
    public void setOpenResource(List<PlayableCard> openResource){
        this.openResource = openResource;
    }

    @Override
    public void setAvailablePlaces(List<Coordinates> availablePlaces){
        this.availablePlaces = availablePlaces;
    }

    @Override
    public void setCanBePlaced(boolean[] canBePlaced){
        this.canBePlaced = canBePlaced;
    }
    @Override
    public void setDisposition(String player, HashMap<Coordinates, PlayableCard> disposition){
        this.dispositions.put(player, disposition);
    }

    @Override
    public void setMyTurn(boolean bool){
        myTurn = bool;
    }

    @Override
    public boolean getMyTurn() {
        return myTurn;
    }
}
