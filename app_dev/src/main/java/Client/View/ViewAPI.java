package Client.View;

import Client.UI.TUI.TUI;
import Client.UI.UI;
import Client.Web.ClientAPI_GO;
import SharedWebInterfaces.Messages.MessagesFromClient.toModelController.*;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.GameState.TurnState;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
    private List<String> players;
    private String gameId;
    private HashMap<Artifact, Integer> availableArtifacts;
    private HashMap<Element, Integer> availableElements;
    //the id of THIS player
    private String playerId;
    private String turnPlayer;
    private String pawnColor;
    private List<Coordinates> availablePlaces;
    //an array of 3 booleans indicating which of the cards in the player's hand can be placed(due to placementConstraint)
    private boolean[] canBePlaced;
    //the disposition of all players' are stored here
    private HashMap< String, HashMap< Coordinates, PlayableCard> > dispositions;
    //the points of all players are store here
    private HashMap< String , Integer> points;
    private UI ui;
    private ClientAPI_GO clientAPIGo;

    public ViewAPI() {//TODO: clientAPI_GO deve essere passato come parametro

        availableElements = new HashMap<>();
        availableArtifacts = new HashMap<>();
        //we initialize both hashmap to zero
        for(Element el : Element.values()) {
            availableElements.put(el, 0);
        }
        for(Artifact ar : Artifact.values()){
            availableArtifacts.put(ar, 0);
        }

        this.ui = new TUI(this);
        this.commonObjectives = new ArrayList<>();
        this.chooseSecretObjectives = new ArrayList<>();
    }

    public void setClientAPIGo(ClientAPI_GO clientAPI_GO){
        clientAPIGo = clientAPI_GO;
        System.out.println("helo");
    }

    //////////////////////// INTERFACE METHODS //////////////////////////////////////////////////////////////////////////////////////


    /////////// from CLIENT to SERVER  ACTIONS ////////////////////////////////////////////////////////////////////////////////////
    //all this methods create a new MessageFromClient object containing an execute() method with the call to a specific method of ModelController
    public void playStarterCard(){
        clientAPIGo.sendToServer( new PlayStarterCardMessage( starterCard.getFaceSide(), playerId));
    }

    public void chooseSecretObjective(String chosenObjective){
        clientAPIGo.sendToServer(new ChooseSecreteObjMessage(chosenObjective, playerId));
    }

    public void playCard(PlayableCard c, int x, int y) {
        clientAPIGo.sendToServer( new PlayCardMessage(c.getId(), x, y, c.getFaceSide()));
    }

    public void drawCard(int cardSource){
        clientAPIGo.sendToServer( new DrawCardMessage(cardSource));
    }

    @Override
    public void readyToPlay(){
        clientAPIGo.sendToServer( new ReadyToPlayMessage());
    }



    /////////// from SERVER to CLIENT ACTIONS ////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void setState(TurnState state) {
        this.state = state;
        //System.out.println(state.toString());
        this.state.display( ui);
    }

    @Override
    public void setGoldDeck(List<PlayableCard> deck){goldDeck = deck;}

    @Override
    public void setResourceDeck(List<PlayableCard> deck){resourceDeck = deck;}


    @Override
    public void setPlayers(List<String> players){
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
        System.out.println(starterCard.getId());
        this.starterCard = starterCard;
    }

    @Override
    public void setHand(List<PlayableCard> hand){
        this.hand = hand;
    }

    //the player is given the two ObjectiveCard cards from which he can choose his secretObjective
    @Override
    public void setSecretObjectives(ObjectiveCard obj1, ObjectiveCard obj2){
        chooseSecretObjectives.add(0, obj1);
        chooseSecretObjectives.add(1, obj2);
    }

    //the player chooses his secretObjective
    @Override
    public void setSecretObjective(ObjectiveCard secretObjective){
        //this.secretObjective = secretObjective;
        chooseSecretObjective(String.valueOf(secretObjective.getId()));
    }


    //TODO: rendere più chiara la scelta di secretObjective, confirmSecretObjective potrebbe essere eliminato

    @Override
    public void confirmSecretObjective(ObjectiveCard secretObjective){
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


    public void setPlayerId(String playerId){
        this.playerId = playerId;
    }

    @Override
    public boolean getMyTurn() {
        if(turnPlayer.equals(playerId)) return true;
        return false;
    }


    ////////////////////////////////// GETTER METHODS //////////////////////////////////////////////////////////////////////////////

    /**
     * returns the disposition of THIS player
     * @return
     */
    public HashMap<Coordinates, PlayableCard> getDisposition(){
        return dispositions.get(this.playerId);
    }

    public PlayableCard getStarterCard(){
        return this.starterCard;
    }

    public List<PlayableCard> getHand(){return hand;}

    public List<ObjectiveCard> getCommonObjectives() {
        return commonObjectives;
    }

    public List<ObjectiveCard> getChooseSecretObjectives() {
        return chooseSecretObjectives;
    }
    public List<Coordinates> getAvailablePlaces(){
        return availablePlaces;
    }

    public boolean[] getCanBePlaced() {
        return canBePlaced;
    }

    public HashMap<String, Integer> getPoints() {
        return points;
    }

    public String getPlayerId() {
        return playerId;
    }

    public HashMap<Artifact, Integer> getAvailableArtifacts() {
        return availableArtifacts;
    }

    public HashMap<Element, Integer> getAvailableElements() {
        return availableElements;
    }

    public List<PlayableCard> getGoldDeck() {
        return goldDeck;
    }

    public List<PlayableCard> getResourceDeck() {
        return resourceDeck;
    }

    public List<PlayableCard> getOpenGold() {
        return openGold;
    }

    public List<PlayableCard> getOpenResource() {
        return openResource;
    }

    public String getTurnPlayer() {
        return turnPlayer;
    }

    @Override
    public void setTurnPlayer(String turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    public ObjectiveCard getSecretObjective(){return secretObjective;}
}
