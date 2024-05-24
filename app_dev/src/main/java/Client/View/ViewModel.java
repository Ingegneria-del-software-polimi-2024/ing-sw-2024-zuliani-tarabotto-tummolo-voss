package Client.View;

import Client.UI.UI;
import Client.Web.ClientAPI_GO;
import SharedWebInterfaces.Messages.MessagesFromClient.toModelController.*;
import SharedWebInterfaces.Messages.MessagesToLobby.JoinGameMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.NewConnectionMessage;
import SharedWebInterfaces.Messages.MessagesToLobby.RequestAvailableGames;
import model.GameState.TurnState;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewModel {
    private ArrayList<String> listOfGames;
    //private for the player
    private List<PlayableCard> hand;
    private PlayableCard starterCard;
    private ObjectiveCard secretObjective;


    //common on table
    private HashMap<Integer,List<PlayableCard>> decks;
    //this hashmap contains the decks from which the player can draw cards in the following order:
    // 0- gold deck
    // 1- open gold cards
    // 2- resource deck
    // 3- open resource cards
    private final int GOLD_DECK_INDX = 0;
    private final int OPEN_GOLD_INDX = 1;
    private final int RESOURCE_DECK_INDX = 2;
    private final int OPEN_RESOURCE_INDX = 3;
    private List<ObjectiveCard> commonObjectives;
    private List<ObjectiveCard> chooseSecretObjectives;

    //logistical support
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

    //interfaces to communicate with
    private ClientAPI_GO clientAPIGo;
    private UI ui;

    public ViewModel(UI ui) {
        availableElements = new HashMap<>();
        availableArtifacts = new HashMap<>();
        //we initialize both hashmap to zero
        for(Element el : Element.values()) {
            availableElements.put(el, 0);
        }
        for(Artifact ar : Artifact.values()){
            availableArtifacts.put(ar, 0);
        }

        this.commonObjectives = new ArrayList<>();
        this.chooseSecretObjectives = new ArrayList<>();

        this.decks = new HashMap<Integer,List<PlayableCard>>();
        this.ui = ui;
    }
    public void setClientAPIGo(ClientAPI_GO clientAPI_GO){
        clientAPIGo = clientAPI_GO;
        //For debug purpose only
//        System.out.println("helo");
    }

    /////////////////////////////////////////////////Lobby//////////////////////////////////////////////////////////////
    public void setAvailableGames(ArrayList<String> listOfGames){
        this.listOfGames = listOfGames;
    }
    public void displayAvailableGames(){
        ui.displayAvailableGames(listOfGames);
    }
    public void requestAvailableGames(){
        clientAPIGo.sendToLobby(new RequestAvailableGames(playerId));
    }
    public void joinGame(String game, int players){
        clientAPIGo.sendToLobby(new JoinGameMessage(playerId, game, players));
    }
    /////////// from CLIENT to SERVER  ACTIONS ////////////////////////////////////////////////////////////////////////////////////
    //all this methods create a new MessageFromClient object containing an execute() method with the call to a specific method of ModelController
    public void playStarterCard(){
        clientAPIGo.sendToServer( new PlayStarterCardMessage( starterCard.getFaceSide(), playerId));
    }

    private void chooseSecretObjective(String chosenObjective){
        clientAPIGo.sendToServer(new ChooseSecreteObjMessage(chosenObjective, playerId));
    }

    public void playCard(PlayableCard c, boolean faceSide, int x, int y) {
        clientAPIGo.sendToServer( new PlayCardMessage(c.getId(), x, y, faceSide));
    }

    public void drawCard(int cardSource){
        clientAPIGo.sendToServer( new DrawCardMessage(cardSource));
    }

    public void readyToPlay(){clientAPIGo.sendToServer( new ReadyToPlayMessage());}



    /////////// from SERVER to CLIENT ACTIONS ////////////////////////////////////////////////////////////////////////////////////
    public void setState(TurnState state) {
        this.state = state;
        //System.out.println(state.toString());
        this.state.display(ui);
    }

    public void setGoldDeck(List<PlayableCard> deck){
        decks.put(GOLD_DECK_INDX, deck);
    }


    public void setResourceDeck(List<PlayableCard> deck){
        decks.put(RESOURCE_DECK_INDX, deck);
    }


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

    public void setGameId(String gameId) {
        this.gameId = gameId;
        ui.joinedGame(gameId);
    }

    //the player is given his starterCard, he will then have to place it
    public void setStarterCard(PlayableCard starterCard){
        //placeable is set to false because for starterCard it doesn't matter
        //System.out.println(starterCard.getId());
        this.starterCard = starterCard;
    }

    public void setHand(List<PlayableCard> hand){
        this.hand = hand;
    }

    //the player is given the two ObjectiveCard cards from which he can choose his secretObjective
    public void setSecretObjectives(ObjectiveCard obj1, ObjectiveCard obj2){
        chooseSecretObjectives.add(0, obj1);
        chooseSecretObjectives.add(1, obj2);
    }

    //the player chooses his secretObjective
    public void setSecretObjective(ObjectiveCard secretObjective){
        this.secretObjective = secretObjective;
        chooseSecretObjective(String.valueOf(secretObjective.getId()));
    }

    public void confirmSecretObjective(ObjectiveCard secretObjective){
        this.secretObjective = secretObjective;
    }


    //the player's points are updated
    public void setPoints(String player, int points) {
        this.points.put(player, points);
    }

    public void updateArtifacts(HashMap<Artifact, Integer> artifacts) {
        for(Artifact a : artifacts.keySet()){
            availableArtifacts.put(a, artifacts.get(a));
        }
    }

    public void updateElements(HashMap<Element, Integer> elements) {
        for(Element e : elements.keySet()){
            availableElements.put(e, elements.get(e));
        }
    }

    public void updateCardSource(List<PlayableCard> deck, int cardSource) {
        decks.put(cardSource, deck);
    }

    public void updateOpenCards(List<PlayableCard> decK, int cardSource){
        //we update open cards, this means that the first card of a deck has been turned and put in the open ones
        //that is why we call deck.remove(0). In particular the open cards are a position ahead in comparison to the
        //same type decks
        decks.put(cardSource, decK);
        decks.get(cardSource-1).remove(0);
    }
    public void setFinalPoints(HashMap<String, Integer> finalPoints) {
        for(String player : points.keySet()){
            points.put(player, finalPoints.get(player));
        }
    }

    //we use this function to end the game whenever we want
    public void endGame(){
        clientAPIGo.sendToServer(new EndGameMessage());
    }

    public void setPawnColor(String pawnColor) {
        this.pawnColor = pawnColor;
    }

    public void setCommonObjectives(ObjectiveCard commonObjective1, ObjectiveCard commonObjective2){
        commonObjectives.add(commonObjective1);
        commonObjectives.add(commonObjective2);
    }

    public void setOpenGold(List<PlayableCard> openGold){
        this.decks.put(OPEN_GOLD_INDX, openGold);
    }

    public void setOpenResource(List<PlayableCard> openResource){
        this.decks.put(OPEN_RESOURCE_INDX, openResource);
    }

    public void setAvailablePlaces(List<Coordinates> availablePlaces){
        this.availablePlaces = availablePlaces;
    }

    public void setCanBePlaced(boolean[] canBePlaced){
        this.canBePlaced = canBePlaced;
    }
    public void setDisposition(String player, HashMap<Coordinates, PlayableCard> disposition){
        this.dispositions.put(player, disposition);
    }


    public void setPlayerId(String playerId){
        this.playerId = playerId;
        clientAPIGo.sendToLobby(new NewConnectionMessage(playerId));
    }

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
        return decks.get(GOLD_DECK_INDX);
    }

    public List<PlayableCard> getResourceDeck() {
        return decks.get(RESOURCE_DECK_INDX);
    }

    public List<PlayableCard> getOpenGold() {
        return decks.get(OPEN_GOLD_INDX);
    }

    public List<PlayableCard> getOpenResource() {
        return decks.get(OPEN_RESOURCE_INDX);
    }

    public String getTurnPlayer() {
        return turnPlayer;
    }

    public void setTurnPlayer(String turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    public ObjectiveCard getSecretObjective(){return secretObjective;}

    public HashMap<String, HashMap<Coordinates, PlayableCard>> getDispositions() {
        return dispositions;
    }
    public List<String> getPlayers(){
        return players;
    }

    public HashMap<Integer, List<PlayableCard>> getDecks() {
        return decks;
    }
}
