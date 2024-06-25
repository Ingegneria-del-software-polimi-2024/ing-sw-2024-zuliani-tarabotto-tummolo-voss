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
 */
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
    private /*final*/ List<ObjectiveCard> commonObjectives;
    private /*final*/ List<ObjectiveCard> chooseSecretObjectives;


    //logistical support
    private TurnState state;
    private List<String> players;
    private String gameId = null;
    private HashMap< String, HashMap<Artifact, Integer> > availableArtifacts;
    private HashMap< String, HashMap<Element, Integer> > availableElements;
    private HashMap<String, String> pawnColors;
    private String playerId;
    private String turnPlayer;
    private List<Coordinates> availablePlaces;

    //an array of 3 booleans indicating which of the cards in the player's hand can be placed(due to placementConstraint)
    private boolean[] canBePlaced;


    //the disposition of all players' are stored here
    private HashMap< String, HashMap< Coordinates, PlayableCard> > dispositions;
    //the points of all players are store here
    private HashMap< String , Integer> points;

    /**
     * the list of the winners of the game
     */
    private ArrayList<String> winners;

    /**
     * The chat history: a variable containing all chat message belonging to the current game
     */
    private List<ChatMessage> chat;

    private boolean gameStarted = false;
    //interfaces to communicate with
    private ClientAPI_GO clientAPIGo;
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
     * Set client api go.
     *
     * @param clientAPI_GO the client api go
     */
    public void setClientAPIGo(ClientAPI_GO clientAPI_GO){
        clientAPIGo = clientAPI_GO;
    }

    /**
     * Reset client api go.
     */
    public void resetClientAPIGo(){
        clientAPIGo = null;
    }


    /**
     * Set available games.
     *
     * @param listOfGames the list of games
     */
/////////////////////////////////////////////////Lobby//////////////////////////////////////////////////////////////
    public void setAvailableGames(ArrayList<String> listOfGames){
        this.listOfGames = listOfGames;
    }

    /**
     * Display available games.
     */
    public void displayAvailableGames(){
        ui.displayAvailableGames(listOfGames);
    }

    /**
     * Heartbeat to server.
     */
//HEARTBEAT
    public void HeartbeatToServer(){
        if(clientAPIGo == null)
            return;
        clientAPIGo.sendToLobby( new HeartbeatMessage(playerId));
    }

    /**
     * Request available games.
     */
    public void requestAvailableGames(){
        clientAPIGo.sendToLobby(new RequestAvailableGames(playerId));
    }

    /**
     * Join game.
     *
     * @param game    the game
     * @param players the players
     */
    public void joinGame(String game, int players){
        clientAPIGo.sendToLobby(new JoinGameMessage(playerId, game, players));
    }

    /**
     * Play starter card.
     */
/////////// from CLIENT to SERVER  ACTIONS ////////////////////////////////////////////////////////////////////////////////////
    //all this methods create a new MessageFromClient object containing an execute() method with the call to a specific method of ModelController
    public void playStarterCard(){
        clientAPIGo.sendToServer( new PlayStarterCardMessage( starterCard.getFaceSide(), playerId));
    }

    private void chooseSecretObjective(String chosenObjective){
        clientAPIGo.sendToServer(new ChooseSecreteObjMessage(chosenObjective, playerId));
    }

    /**
     * Play card.
     *
     * @param c        the c
     * @param faceSide the face side
     * @param x        the x
     * @param y        the y
     */
    public void playCard(PlayableCard c, boolean faceSide, int x, int y) {
        clientAPIGo.sendToServer( new PlayCardMessage(c.getId(), x, y, faceSide));
    }

    /**
     * Draw card.
     *
     * @param cardSource the card source
     */
    public void drawCard(int cardSource){
        clientAPIGo.sendToServer( new DrawCardMessage(cardSource));
    }

    /**
     * Ready to play.
     */
    public void readyToPlay(){clientAPIGo.sendToServer( new ReadyToPlayMessage());}


    /**
     * Sets state.
     *
     * @param state the state
     */
/////////// from SERVER to CLIENT ACTIONS ////////////////////////////////////////////////////////////////////////////////////
    public void setState(TurnState state) {
        this.state = state;
        //System.out.println(state.toString());
        this.state.display(ui);
    }

    /**
     * Set gold deck.
     *
     * @param deck the deck
     */
    public void setGoldDeck(List<PlayableCard> deck){
        decks.put(GOLD_DECK_INDX, deck);
    }


    /**
     * Set resource deck.
     *
     * @param deck the deck
     */
    public void setResourceDeck(List<PlayableCard> deck){
        decks.put(RESOURCE_DECK_INDX, deck);
    }


    /**
     * Set players.
     *
     * @param players the players
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
     * Sets game id.
     *
     * @param gameId the game id
     */
    public void setGameId(String gameId) {
        this.gameId = gameId;
        ui.joinedGame(gameId);
    }

    /**
     * Set starter card.
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
     * Set hand.
     *
     * @param hand the hand
     */
    public void setHand(List<PlayableCard> hand){
        this.hand = hand;
    }

    /**
     * Set secret objectives.
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
     * Set secret objective.
     *
     * @param secretObjective the secret objective
     */
//the player chooses his secretObjective
    public void setSecretObjective(ObjectiveCard secretObjective){
        this.secretObjective = secretObjective;
        chooseSecretObjective(String.valueOf(secretObjective.getId()));
    }

    /**
     * Confirm secret objective.
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
     * Update artifacts.
     *
     * @param player    the player
     * @param artifacts the artifacts
     */
    public void updateArtifacts(String player, HashMap<Artifact, Integer> artifacts) {
        /*for(Artifact a : artifacts.keySet()){
            availableArtifacts.put(a, artifacts.get(a));
        }*/
        availableArtifacts.put(player, artifacts);
    }

    /**
     * Update elements.
     *
     * @param player   the player
     * @param elements the elements
     */
    public void updateElements(String player, HashMap<Element, Integer> elements) {
        /*for(Element e : elements.keySet()){
            availableElements.put(e, elements.get(e));
        }*/
        availableElements.put(player, elements);
    }

    /**
     * Update card source.
     *
     * @param deck       the deck
     * @param cardSource the card source
     */
    public void updateCardSource(List<PlayableCard> deck, int cardSource) {
        decks.put(cardSource, deck);
    }

    /**
     * Update open cards.
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
     * @param finalPoints the final points
     * @param winnersList the winners list
     */
    public void setFinalPoints(HashMap<String, Integer> finalPoints, ArrayList<String> winnersList) {
        points.replaceAll((p, v) -> finalPoints.get(p));
        winners = winnersList;
    }

    /**
     * Quit game.
     */
//we use this function to end the game whenever we want
    public void quitGame(){
        gameStarted = false;
        chat = Collections.synchronizedList(new ArrayList<>());
        clientAPIGo.sendToServer(new QuitGameMessage(playerId));
    }

    /**
     * Quit game.
     *
     * @param roomName the room name
     */
    public void quitGame(String roomName){
        clientAPIGo.sendToLobby(new QuitGameBeforeStartMessage(roomName, playerId));
        gameId = null;
    }

    /**
     * Sets pawn color.
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
     * @return the string
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
     * Set open gold.
     *
     * @param openGold the open gold
     */
    public void setOpenGold(List<PlayableCard> openGold){
        this.decks.put(OPEN_GOLD_INDX, openGold);
    }

    /**
     * Set open resource.
     *
     * @param openResource the open resource
     */
    public void setOpenResource(List<PlayableCard> openResource){
        this.decks.put(OPEN_RESOURCE_INDX, openResource);
    }

    /**
     * Set available places.
     *
     * @param availablePlaces the available places
     */
    public void setAvailablePlaces(List<Coordinates> availablePlaces){
        this.availablePlaces = availablePlaces;
    }

    /**
     * Set can be placed.
     *
     * @param canBePlaced the can be placed
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
     * Set player id.
     *
     * @param playerId the player id
     */
    public void setPlayerId(String playerId){
//        this.playerId = playerId;
        clientAPIGo.sendToLobby(new NewConnectionMessage(playerId));
    }

    /**
     * Ack player id.
     *
     * @param playerId the player id
     */
    public void ackPlayerId(String playerId){
        this.playerId = playerId;
    }

    /**
     * Gets my turn.
     *
     * @return the my turn
     */
    public boolean getMyTurn() {
        if(turnPlayer.equals(playerId)) return true;
        return false;
    }


    ////////////////////////////////// GETTER METHODS //////////////////////////////////////////////////////////////////////////////

    /**
     * returns the disposition of THIS player
     *
     * @return hash map
     */
    public HashMap<Coordinates, PlayableCard> getDisposition(){
        return dispositions.get(this.playerId);
    }

    /**
     * Get starter card playable card.
     *
     * @return the playable card
     */
    public PlayableCard getStarterCard(){
        return this.starterCard;
    }

    /**
     * Get hand list.
     *
     * @return the list
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
     * @return the choose secret objectives
     */
    public List<ObjectiveCard> getChooseSecretObjectives() {
        return chooseSecretObjectives;
    }

    /**
     * Get available places list.
     *
     * @return the list
     */
    public List<Coordinates> getAvailablePlaces(){
        return availablePlaces;
    }

    /**
     * Get can be placed boolean [ ].
     *
     * @return the boolean [ ]
     */
    public boolean[] getCanBePlaced() {
        return canBePlaced;
    }

    /**
     * Gets points.
     *
     * @return the points
     */
    public HashMap<String, Integer> getPoints() {
        return points;
    }

    /**
     * Gets player id.
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
     * Get secret objective objective card.
     *
     * @return the objective card
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
     * handles the reception of a text message,
     *
     * @param message the message to be received
     */
    public void receiveTextMessage(ChatMessage message) {
        chat.add(message);
        ui.displayNewTextMessage(message);
    }


    /**
     * substitutes the current chat history
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
     * Reset game id.
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
     * Is game started boolean.
     *
     * @return the boolean
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
