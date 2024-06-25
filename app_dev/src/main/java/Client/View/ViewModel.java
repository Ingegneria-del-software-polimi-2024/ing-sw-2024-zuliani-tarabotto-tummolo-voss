package Client.View;

import Chat.ChatHistory;
import Chat.MessagesFromClient.ChatMessage;
import Client.UI.UI;
import Client.Web.ClientAPI_GO;
import SharedWebInterfaces.Messages.MessagesFromClient.toModelController.*;
import SharedWebInterfaces.Messages.MessagesToLobby.*;
import model.GameState.TurnState;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;
import model.enums.Pawn;
import model.placementArea.Coordinates;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * The type View model.
 * A class representing the model for the client, it is a lighter version of the model present on the server, used only for
 * graphic displaying purposes
 */
public class ViewModel {
    /**
     * The list of available games present
     */
    private ArrayList<String> listOfGames;
    //private for the player
    /**
     * The hand of the player
     */
    private List<PlayableCard> hand;
    /**
     * The starter card of the player
     */
    private PlayableCard starterCard;
    /**
     * The secret onjective of the player
     */
    private ObjectiveCard secretObjective;


    //common on table
    /**
     * The decks on the table.
     * This hashmap contains the decks from which the player can draw cards in the following order:
     *      0- gold deck,
     *      1- open gold cards,
     *      2- resource deck,
     *      3- open resource cards
     */
    private HashMap<Integer,List<PlayableCard>> decks;
    //this hashmap contains the decks from which the player can draw cards in the following order:
    // 0- gold deck
    // 1- open gold cards
    // 2- resource deck
    // 3- open resource cards
    /**
     * The index of the gold deck
     */
    private final int GOLD_DECK_INDX = 0;
    /**
     * The index of the open gold cards
     */
    private final int OPEN_GOLD_INDX = 1;
    /**
     * The index of the resource deck
     */
    private final int RESOURCE_DECK_INDX = 2;
    /**
     * The index of the open resource cards
     */
    private final int OPEN_RESOURCE_INDX = 3;
    /**
     * The common objectives
     */
    private List<ObjectiveCard> commonObjectives;
    /**
     * The card that will become the secret objective
     */
    private  List<ObjectiveCard> chooseSecretObjectives;


    //logistical support
    /**
     * The turn state
     */
    private TurnState state;
    /**
     * A list containing the players
     */
    private List<String> players;
    /**
     * The name of the game the player is in
     */
    private String gameId = null;
    /**
     * The available artifacts for the player
     */
    private HashMap< String, HashMap<Artifact, Integer> > availableArtifacts;
    /**
     * The available elements for the player
     */
    private HashMap< String, HashMap<Element, Integer> > availableElements;
    /**
     * The pawn colors
     */
    private HashMap<String, String> pawnColors;
    /**
     * The player name
     */
    private String playerId;
    /**
     * The name of the turn player
     */
    private String turnPlayer;
    /**
     * The coordinates in which the turn player can place a card
     */
    private List<Coordinates> availablePlaces;
    /**
     * An array of 3 booleans indicating which of the cards in the player's hand can be placed(due to placementConstraint)
     */
    private boolean[] canBePlaced;


    /**
     * The disposition of all players' are stored here
     */
    private HashMap< String, HashMap< Coordinates, PlayableCard> > dispositions;
    /**
     * The points of all players are store here
     */
    private HashMap< String , Integer> points;

    /**
     * The list of the winners of the game
     */
    private ArrayList<String> winners;

    /**
     * The chat history: a variable containing all chat message belonging to the current game
     */
    private List<ChatMessage> chat;
    /**
     * True when the player is actually playing in a game (set true when their starter card is handled to them)
     */
    private boolean gameStarted = false;

    //interfaces to communicate with
    /**
     * The interface to send messages to the server
     */
    private ClientAPI_GO clientAPIGo;
    /**
     * The active "printer" may be GUI or TUI
     */
    private UI ui;

    /**
     * Instantiates a new View model.
     *
     * @param ui the ui
     */
    public ViewModel(UI ui) {
        this.commonObjectives = new ArrayList<>();
        this.chooseSecretObjectives = new ArrayList<>();
        this.winners = new ArrayList<String>();

        this.pawnColors = new HashMap<>();
        this.decks = new HashMap<Integer,List<PlayableCard>>();

        this.chat = Collections.synchronizedList(new ArrayList<>());
        this.ui = ui;
    }

    /**
     * Sets client api go.
     *
     * @param clientAPI_GO the client api go
     */
    public void setClientAPIGo(ClientAPI_GO clientAPI_GO){
        clientAPIGo = clientAPI_GO;
    }

    /**
     * Resets client api go, setting it to null.
     */
    public void resetClientAPIGo(){
        clientAPIGo = null;
    }



/////////////////////////////////////////////////Lobby//////////////////////////////////////////////////////////////
    /**
     * Sets the available games.
     *
     * @param listOfGames the list of games
     */
    public void setAvailableGames(ArrayList<String> listOfGames){
        this.listOfGames = listOfGames;
    }

    /**
     * Displays the available games.
     */
    public void displayAvailableGames(){
        ui.displayAvailableGames(listOfGames);
    }

    /**
     * Sends the heartbeat to server.
     */
//HEARTBEAT
    public void HeartbeatToServer(){
        if(clientAPIGo == null)
            return;
        clientAPIGo.sendToLobby( new HeartbeatMessage(playerId));
    }

    /**
     * Requests the available games.
     */
    public void requestAvailableGames(){
        clientAPIGo.sendToLobby(new RequestAvailableGames(playerId));
    }

    /**
     * Asks the server to join the game specified.
     *
     * @param game    the game name
     * @param players the players' number, if 0 it means that the player wants to join an already existing game, else they wants to create a game
     */
    public void joinGame(String game, int players){
        clientAPIGo.sendToLobby(new JoinGameMessage(playerId, game, players));
    }


/////////// from CLIENT to SERVER  ACTIONS ////////////////////////////////////////////////////////////////////////////////////
    //all this methods create a new MessageFromClient object containing an execute() method with the call to a specific method of ModelController
    /**
     * Plays the starter card notifying the server.
     */
    public void playStarterCard(){
        clientAPIGo.sendToServer( new PlayStarterCardMessage( starterCard.getFaceSide(), playerId));
    }

    private void chooseSecretObjective(String chosenObjective){
        clientAPIGo.sendToServer(new ChooseSecreteObjMessage(chosenObjective, playerId));
    }

    /**
     * Play a card notifying the server.
     *
     * @param c        the card
     * @param faceSide the face side
     * @param x        the x of the coordinates
     * @param y        the y of the coordinates
     */
    public void playCard(PlayableCard c, boolean faceSide, int x, int y) {
        clientAPIGo.sendToServer( new PlayCardMessage(c.getId(), x, y, faceSide));
    }

    /**
     * Draws a card notifying the server.
     *
     * @param cardSource the card source
     */
    public void drawCard(int cardSource){
        clientAPIGo.sendToServer( new DrawCardMessage(cardSource));
    }

    /**
     * Notifies the server if being ready to play.
     */
    public void readyToPlay(){clientAPIGo.sendToServer( new ReadyToPlayMessage());}



/////////// from SERVER to CLIENT ACTIONS ////////////////////////////////////////////////////////////////////////////////////
    /**
     * Sets the game state.
     *
     * @param state the state to be set
     */
    public void setState(TurnState state) {
        this.state = state;
        //System.out.println(state.toString());
        this.state.display(ui);
    }

    /**
     * Sets the gold deck.
     *
     * @param deck the new gold deck
     */
    public void setGoldDeck(List<PlayableCard> deck){
        decks.put(GOLD_DECK_INDX, deck);
    }


    /**
     * Set the resource deck.
     *
     * @param deck the new resource deck
     */
    public void setResourceDeck(List<PlayableCard> deck){
        decks.put(RESOURCE_DECK_INDX, deck);
    }


    /**
     * Sets players when initializing the game.
     *
     * @param players the players' names
     */
    public void setPlayers(List<String> players){
        this.players = players;

        //after we receive the array containing the unique nicknames of the players, we initialize
        //the two hashmaps containing information about all players
        this.dispositions = new HashMap<>();
        this.points = new HashMap<>();
        availableElements = new HashMap<>();
        availableArtifacts = new HashMap<>();
        for(String p : players){
            dispositions.put(p, new HashMap<>());
            points.put(p, 0);

            HashMap<Element, Integer> elements = new HashMap<>();
            HashMap<Artifact, Integer> artifacts = new HashMap<>();

            for(Artifact ar : Artifact.values()){
                artifacts.put(ar, 0);
            }
            for(Element el : Element.values()){
                elements.put(el, 0);
            }

            availableElements.put(p, elements);
            availableArtifacts.put(p, artifacts);


        }
    }

    /**
     * Sets the game id.
     *
     * @param gameId the game id
     */
    public void setGameId(String gameId) {
        this.gameId = gameId;
        ui.joinedGame(gameId);
    }

    /**
     * Sets the starter card.
     *
     * @param starterCard the starter card
     */
//the player is given his starterCard, he will then have to place it
    public void setStarterCard(PlayableCard starterCard){
        //placeable is set to false because for starterCard it doesn't matter
        //System.out.println(starterCard.getId());
        this.starterCard = starterCard;
    }

    /**
     * Sets the hand.
     *
     * @param hand the hand
     */
    public void setHand(List<PlayableCard> hand){
        this.hand = hand;
    }

    /**
     * Sets the secret objectives.
     *
     * @param obj1 the obj 1
     * @param obj2 the obj 2
     */
//the player is given the two ObjectiveCard cards from which he can choose his secretObjective
    public void setSecretObjectives(ObjectiveCard obj1, ObjectiveCard obj2){
        System.out.println(obj1.getId());
        System.out.println(obj2.getId());
        chooseSecretObjectives.add(0, obj1);
        chooseSecretObjectives.add(1, obj2);
    }

    /**
     * Sets the secret objective.
     *
     * @param secretObjective the secret objective
     */
//the player chooses his secretObjective
    public void setSecretObjective(ObjectiveCard secretObjective){
        this.secretObjective = secretObjective;
        chooseSecretObjective(String.valueOf(secretObjective.getId()));
    }

    /**
     * Confirms the secret objective.
     *
     * @param secretObjective the secret objective
     */
    public void confirmSecretObjective(ObjectiveCard secretObjective){
        this.secretObjective = secretObjective;
    }


    /**
     * Sets points.
     *
     * @param player the player
     * @param points the points
     */
//the player's points are updated
    public void setPoints(String player, int points) {
        this.points.put(player, points);
    }

    /**
     * Updates the artifacts.
     *
     * @param player    the player
     * @param artifacts the artifacts
     */
    public void updateArtifacts(String player, HashMap<Artifact, Integer> artifacts) {
        availableArtifacts.put(player, artifacts);
    }

    /**
     * Updates the elements.
     *
     * @param player   the player
     * @param elements the elements
     */
    public void updateElements(String player, HashMap<Element, Integer> elements) {
        availableElements.put(player, elements);
    }

    /**
     * Updates a card source after a player draws from it.
     *
     * @param deck       the deck
     * @param cardSource the card source
     */
    public void updateCardSource(List<PlayableCard> deck, int cardSource) {
        decks.put(cardSource, deck);
    }

    /**
     * Update the open cards.
     *
     * @param decK       the dec k
     * @param cardSource the card source
     */
    public void updateOpenCards(List<PlayableCard> decK, int cardSource){
        //we update open cards, this means that the first card of a deck has been turned and put in the open ones
        //that is why we call deck.remove(0). In particular the open cards are a position ahead in comparison to the
        //same type decks
        decks.put(cardSource, decK);
        decks.get(cardSource-1).remove(0);
    }

    /**
     * Sets final points.
     *
     * @param finalPoints the final points in a hashmap with name of the players as entries
     * @param winnersList the winners list
     */
    public void setFinalPoints(HashMap<String, Integer> finalPoints, ArrayList<String> winnersList) {
        points.replaceAll((p, v) -> finalPoints.get(p));
        winners = winnersList;
    }

    /**
     * Quit game.
     * We use this function to end the game whenever we want
     */
    public void quitGame(){
        gameStarted = false;
        chat = Collections.synchronizedList(new ArrayList<>());
        clientAPIGo.sendToServer(new QuitGameMessage(playerId));
    }

    /**
     * Quit game when being in the waiting room.
     *
     * @param roomName the room name
     */
    public void quitGame(String roomName){
        clientAPIGo.sendToLobby(new QuitGameBeforeStartMessage(roomName, playerId));
        gameId = null;
    }

    /**
     * Sets the pawn color for a player.
     *
     * @param player    the player
     * @param pawnColor the pawn color
     */
    public void setPawnColor(String player, String pawnColor) {
        pawnColors.put(player, pawnColor);
    }

    /**
     * Get pawn color string.
     *
     * @param player the player
     * @return the colour
     */
    public String getPawnColor(String player){return pawnColors.get(player);}

    /**
     * Set common objectives.
     *
     * @param commonObjective1 the common objective 1
     * @param commonObjective2 the common objective 2
     */
    public void setCommonObjectives(ObjectiveCard commonObjective1, ObjectiveCard commonObjective2){
        commonObjectives.add(0, commonObjective1);
        commonObjectives.add(1, commonObjective2);
    }

    /**
     * Sets open gold card.
     *
     * @param openGold the open gold
     */
    public void setOpenGold(List<PlayableCard> openGold){
        this.decks.put(OPEN_GOLD_INDX, openGold);
    }

    /**
     * Sets open resource card.
     *
     * @param openResource the open resource
     */
    public void setOpenResource(List<PlayableCard> openResource){
        this.decks.put(OPEN_RESOURCE_INDX, openResource);
    }

    /**
     * Sets available places.
     *
     * @param availablePlaces the available places
     */
    public void setAvailablePlaces(List<Coordinates> availablePlaces){
        this.availablePlaces = availablePlaces;
    }

    /**
     * Sets can be placed.
     *
     * @param canBePlaced the arraying containing the available places (The coordinates in which the turn player can place a card)
     */
    public void setCanBePlaced(boolean[] canBePlaced){
        this.canBePlaced = canBePlaced;
    }

    /**
     * Set disposition.
     *
     * @param player      the player
     * @param disposition the disposition
     */
    public void setDisposition(String player, HashMap<Coordinates, PlayableCard> disposition){
        this.dispositions.put(player, disposition);
    }


    /**
     * Sends to the lobby a NewConnectionMessage, containing the player's name.
     *
     * @param playerId the player id
     */
    public void setPlayerId(String playerId){
//        this.playerId = playerId;
        clientAPIGo.sendToLobby(new NewConnectionMessage(playerId));
    }

    /**
     * Sets the player id, used after the reception of the ack of the id from the server.
     *
     * @param playerId the player id
     */
    public void ackPlayerId(String playerId){
        this.playerId = playerId;
    }

    /**
     *
     * @return true if it is the turn of the player
     */
    public boolean getMyTurn() {
        if(turnPlayer.equals(playerId)) return true;
        return false;
    }


    ////////////////////////////////// GETTER METHODS //////////////////////////////////////////////////////////////////////////////

    /**
     * Returns the disposition of THIS player
     *
     * @return hash map
     */
    public HashMap<Coordinates, PlayableCard> getDisposition(){
        return dispositions.get(this.playerId);
    }

    /**
     * Gets the starter card.
     *
     * @return the playable card
     */
    public PlayableCard getStarterCard(){
        return this.starterCard;
    }

    /**
     * Gets the hand.
     *
     * @return the hand of the player
     */
    public List<PlayableCard> getHand(){return hand;}

    /**
     * Gets common objectives.
     *
     * @return the common objectives
     */
    public List<ObjectiveCard> getCommonObjectives() {
        return commonObjectives;
    }

    /**
     * Gets choose secret objectives.
     *
     * @return the secret objectives
     */
    public List<ObjectiveCard> getChooseSecretObjectives() {
        return chooseSecretObjectives;
    }

    /**
     * Get available places list.
     *
     * @return the available places
     */
    public List<Coordinates> getAvailablePlaces(){
        return availablePlaces;
    }

    /**
     *
     * @return the boolean [ ] representing the cards in the hand that can be placed
     */
    public boolean[] getCanBePlaced() {
        return canBePlaced;
    }

    /**
     *
     * @return the points of the player
     */
    public HashMap<String, Integer> getPoints() {
        return points;
    }

    /**
     *
     * @return the player id
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Gets available artifacts.
     *
     * @param player the player
     * @return the available artifacts
     */
    public HashMap<Artifact, Integer> getAvailableArtifacts(String player) {
        return availableArtifacts.get(player);
    }

    /**
     * Gets available elements.
     *
     * @param player the player
     * @return the available elements
     */
    public HashMap<Element, Integer> getAvailableElements(String player) {
        return availableElements.get(player);
    }

    /**
     * Gets gold deck.
     *
     * @return the gold deck
     */
    public List<PlayableCard> getGoldDeck() {
        return decks.get(GOLD_DECK_INDX);
    }

    /**
     * Gets resource deck.
     *
     * @return the resource deck
     */
    public List<PlayableCard> getResourceDeck() {
        return decks.get(RESOURCE_DECK_INDX);
    }

    /**
     * Gets open gold.
     *
     * @return the open gold
     */
    public List<PlayableCard> getOpenGold() {
        return decks.get(OPEN_GOLD_INDX);
    }

    /**
     * Gets open resource.
     *
     * @return the open resource
     */
    public List<PlayableCard> getOpenResource() {
        return decks.get(OPEN_RESOURCE_INDX);
    }

    /**
     * Gets turn player.
     *
     * @return the turn player
     */
    public String getTurnPlayer() {
        return turnPlayer;
    }

    /**
     * Sets turn player.
     *
     * @param turnPlayer the turn player
     */
    public void setTurnPlayer(String turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    /**
     * Get secret the secret objective card.
     *
     * @return the secret objective
     */
    public ObjectiveCard getSecretObjective(){return secretObjective;}

    /**
     * Gets dispositions.
     *
     * @return the dispositions
     */
    public HashMap<String, HashMap<Coordinates, PlayableCard>> getDispositions() {
        return dispositions;
    }

    /**
     * Get players list.
     *
     * @return the list
     */
    public List<String> getPlayers(){
        return players;
    }

    /**
     * Get winners list.
     *
     * @return the list
     */
    public List<String> getWinners(){return Collections.unmodifiableList(winners);}

    /**
     * Gets decks.
     *
     * @return the decks
     */
    public HashMap<Integer, List<PlayableCard>> getDecks() {
        return decks;
    }

    /**
     * Manages the reception of a text message,
     *
     * @param message the message to be received
     */
    public void receiveTextMessage(ChatMessage message) {
        chat.add(message);
        ui.displayNewTextMessage(message);
    }


    /**
     * Substitutes the current chat history
     *
     * @param history the new chat history
     */
    public void resetChatHistory(ArrayList<ChatMessage> history) {
        chat = Collections.synchronizedList(history);
    }


    /**
     * Gets chat history.
     *
     * @return the chat history
     */
    public List<ChatMessage> getChatHistory() {
        return chat;
    }

    /**
     * Reset game id setting it to null.
     */
    public void resetGameID(){
        gameId = null;
    }

    /**
     * Set game as started.
     */
    public void setGameAsStarted(){
        gameStarted = true;
    }

    /**
     * Set game as not started.
     */
    public void setGameAsNotStarted(){
        gameStarted = false;
    }

    /**
     *
     * @return true if the game is started else false
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * All the data structures are reset
     */
    public void reset(){
        this.commonObjectives = new ArrayList<>();
        this.chooseSecretObjectives = new ArrayList<>();
        this.winners = new ArrayList<String>();

        this.pawnColors = new HashMap<>();
        this.decks = new HashMap<Integer,List<PlayableCard>>();

        this.chat = Collections.synchronizedList(new ArrayList<>());

        this.hand = new ArrayList<>();

        this.starterCard = null;

        this.secretObjective = null;

        this.state = null;
        this.players = new ArrayList<>();

        this.gameId = null;
        this.availableArtifacts = new  HashMap< String, HashMap<Artifact, Integer> >();
        this.availableElements = new  HashMap< String, HashMap<Element, Integer> >();
        this.turnPlayer = null;
        this.availablePlaces = new ArrayList<>();

        //the disposition of all players' are stored here
        this.dispositions =  new HashMap< String, HashMap< Coordinates, PlayableCard> >();
        //the points of all players are store here
        this.points = new HashMap< String , Integer>();

        //this.chat = new ArrayList<>();

        gameStarted = false;
    }

    /**
     * Send chat message.
     *
     * @param content the content
     */
    public void sendChatMessage(String content){
        clientAPIGo.sendToServer(new ChatMessage(playerId, content));
    }

    /**
     * Send private chat message.
     *
     * @param content  the content
     * @param receiver the receiver
     */
    public void sendPrivateChatMessage(String content, String receiver){
        clientAPIGo.sendToServer(new ChatMessage(playerId, content, receiver));
    }
}
