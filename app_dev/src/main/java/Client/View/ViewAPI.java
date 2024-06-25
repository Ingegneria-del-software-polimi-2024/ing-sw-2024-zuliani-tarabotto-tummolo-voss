package Client.View;

import Chat.ChatHistory;
import Chat.MessagesFromClient.ChatMessage;
import Client.UI.TUI.TUI;
import Client.UI.UI;
import Client.Web.*;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;
import SharedWebInterfaces.WebSettings;
import model.GameState.TurnState;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.Coordinates;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ViewAPI Class contains:
 * - methods called after user input to modify the view or the model
 * - methods used by the server interface to modify the view
 */
public class ViewAPI implements ViewAPI_Interface {

    //private UI ui;
    private ViewModel viewModel;
    private UI ui;
    private Thread inputThread;
    private Thread heartbeatThread;
    private Thread readMessagesLoop;
    private Thread readChatMessagesLoop;

    /**
     * Set ui.
     *
     * @param ui the ui
     */
    public void setUI(UI ui){
        this.ui = ui;
        this.viewModel = new ViewModel(ui);
    }

    /**
     * Start ui.
     */
    public void startUI(){
        inputThread = new Thread(ui);
        inputThread.start();
    }

    /**
     * Stop ui.
     */
    public void stopUI(){
        if(inputThread == null)
            return;
        try {
            inputThread.interrupt();
            inputThread.join();
        }catch (SecurityException | InterruptedException e){
            return;
        }
    }

    private void setClientAPIGo(ClientAPI_GO clientAPI_GO){
        viewModel.setClientAPIGo(clientAPI_GO);
    }


    /**
     * Heartbeat to server.
     */
    public void HeartbeatToServer(){
        viewModel.HeartbeatToServer();
    }
    //all this methods create a new MessageFromClient object containing an execute() method with t

    /**
     * Play starter card.
     */
/////////// from CLIENT to SERVER  ACTIONS ////////////////////////////////////////////////////////////////////////////////////
    //all this methods create a new MessageFromClient object containing an execute() method with the call to a specific method of ModelController
    public void playStarterCard(){
        viewModel.playStarterCard();
    }

//    public void chooseSecretObjective(String chosenObjective){
//        viewModel.chooseSecretObjective(chosenObjective);
//    }

    /**
     * Play card.
     *
     * @param c        the c
     * @param faceSide the face side
     * @param x        the x
     * @param y        the y
     */
    public void playCard(PlayableCard c, boolean faceSide, int x, int y) {viewModel.playCard(c,faceSide, x,y);}

    /**
     * Draw card.
     *
     * @param cardSource the card source
     */
    public void drawCard(int cardSource){viewModel.drawCard(cardSource);}

    @Override
    public void readyToPlay(){viewModel.readyToPlay();}



    ////////////////////////////////heartbeat////////////////

    public void startHeartbeatThread() {
        heartbeatThread = new Thread(() -> {
            while (true) {
                try {
                    this.HeartbeatToServer();
                    Thread.sleep(3000); // Send heartbeat every 1 second
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        heartbeatThread.setDaemon(true);
        heartbeatThread.start();
    }

    /**
     * Stop heart beat.
     */
    public void stopHeartBeat(){
        heartbeatThread.interrupt();
    }
//////////////////////////////////////////Lobby/////////////////////////////////////////////////////////////////////////

    /**
     * starts the connection with the server
     *
     * @param in   the string defining the connection technology, either "RMI" or "Socket"
     * @param host the ip of the server
     * @throws StartConnectionFailedException the start connection failed exception
     */
    public void startConnection(String in, String host) throws StartConnectionFailedException {
        ClientAPI_COME clientAPICome = new ClientAPI_COME(this);
        readMessagesLoop = new Thread(clientAPICome);
        readChatMessagesLoop = new Thread(clientAPICome::dequeueChatMessages);
        readMessagesLoop.start();
        readChatMessagesLoop.start();
        ClientAPI_GO clientAPIGo = getClientAPIGo(in, host, clientAPICome);
        this.setClientAPIGo(clientAPIGo);
        viewModel.reset();
    }

    /**
     *
     * @param in the string defining the connection technology, either "RMI" or "Socket"
     * @param host the ip of the server
     * @param clientAPICome the interface to which messages will be forwarded
     * @return a new clientAPI_GO object connected with the server
     * @throws StartConnectionFailedException when the connection couldn't be created
     */
    private ClientAPI_GO getClientAPIGo(String in, String host, ClientAPI_COME clientAPICome) throws StartConnectionFailedException {
        ServerHandlerInterface serverHandler;
        if (in.equalsIgnoreCase("RMI")) {
            serverHandler = new RMI_ServerHandler(host, clientAPICome);
            ((RMI_ServerHandler)serverHandler).connect(WebSettings.serverPortRMI);
        } else if(in.equalsIgnoreCase("SOCKET")){
            serverHandler = new SOCKET_ServerHandler(host, WebSettings.serverPortSocket, clientAPICome);
            Thread listeningThread = new Thread((SOCKET_ServerHandler)serverHandler);
            listeningThread.start();
        }else
            throw new StartConnectionFailedException();

        return new ClientAPI_GO(serverHandler);
    }

    /**
     * Choose connection.
     */
    public void chooseConnection(){
        ui.chooseConnection();
    }

    /**
     * Welcome.
     */
    public void welcome(){
        ui.firstWelcome();
    }
    public void askNickname(){
        ui.askNickname();
    }
    public void setAvailableGames(ArrayList<String> listOfGames){
        viewModel.setAvailableGames(listOfGames);
    }
    public void displayAvailableGames(){
        viewModel.displayAvailableGames();
    }
    public void displayAvailableGames(ArrayList<String> availableGames){
        viewModel.setAvailableGames(availableGames);
        viewModel.displayAvailableGames();
    }

    /**
     * Request available games.
     */
    public void requestAvailableGames(){
        viewModel.requestAvailableGames();
    }

    /**
     * Join game.
     *
     * @param game    the game
     * @param players the players
     */
    public void joinGame(String game, int players){
        viewModel.reset();
        viewModel.joinGame(game, players);
    }
    public void nickNameAlreadyInUse(){ui.nickNameAlreadyInUse();}

    public void ackNickName(String name){
        viewModel.ackPlayerId(name);
    }

//////////////////////////////////////////Lobby/////////////////////////////////////////////////////////////////////////

    ///////////// from SERVER to CLIENT ACTIONS ////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void setState(TurnState state) {
        viewModel.setState(state);
    }

    @Override
    public void setGoldDeck(List<PlayableCard> deck){viewModel.setGoldDeck(deck);}

    @Override
    public void setResourceDeck(List<PlayableCard> deck){viewModel.setResourceDeck(deck);}


    @Override
    public void setPlayers(List<String> players) {viewModel.setPlayers(players);}

    @Override
    public void setGameId(String gameId) {viewModel.setGameId(gameId);}

    //the player is given his starterCard, he will then have to place it
    @Override
    public void setStarterCard(PlayableCard starterCard){viewModel.setStarterCard(starterCard);}

    @Override
    public void setHand(List<PlayableCard> hand){viewModel.setHand(hand);}

    //the player is given the two ObjectiveCard cards from which he can choose his secretObjective
    @Override
    public void setSecretObjectives(ObjectiveCard obj1, ObjectiveCard obj2){viewModel.setSecretObjectives(obj1, obj2);}

    //the player chooses his secretObjective
    @Override
    public void setSecretObjective(ObjectiveCard secretObjective){viewModel.setSecretObjective(secretObjective);}

    @Override
    public void confirmSecretObjective(ObjectiveCard secretObjective){
        viewModel.confirmSecretObjective(secretObjective);
    }


    //the player's points are updated
    @Override
    public void setPoints(String player, int points) {
        viewModel.setPoints(player, points);
    }

    @Override
    public void updateArtifacts(String player, HashMap<Artifact, Integer> artifacts) {
        viewModel.updateArtifacts(player, artifacts);
    }

    @Override
    public void updateElements(String player, HashMap<Element, Integer> elements) {
        viewModel.updateElements(player, elements);
    }

    @Override
    public void updateCardSource(List<PlayableCard> deck, int cardSource) {
        viewModel.updateCardSource(deck, cardSource);
    }
    public void updateOpenCards(List<PlayableCard> decK, int cardSource){
        viewModel.updateOpenCards(decK, cardSource);
    }

    @Override
    public void setFinalPoints(HashMap<String, Integer> finalPoints, ArrayList<String> winnersList) {
        viewModel.setFinalPoints(finalPoints, winnersList);
    }

    /**
     * Quit game.
     */
    public void quitGame(){
        viewModel.quitGame();
    }

    /**
     * Quit game.
     *
     * @param roomName the room name
     */
    public void quitGame(String roomName){
        viewModel.quitGame(roomName);
    }

    @Override
    public void setPawnColor(String player, String pawnColor) {viewModel.setPawnColor(player, pawnColor);}

    /**
     * Get pawn color string.
     *
     * @param player the player
     * @return the string
     */
    public String getPawnColor(String player){return viewModel.getPawnColor(player);}

    @Override
    public void setCommonObjectives(ObjectiveCard commonObjective1, ObjectiveCard commonObjective2){
        viewModel.setCommonObjectives(commonObjective1, commonObjective2);
    }

    @Override
    public void setOpenGold(List<PlayableCard> openGold){
        viewModel.setOpenGold(openGold);
    }

    @Override
    public void setOpenResource(List<PlayableCard> openResource){
        viewModel.setOpenResource(openResource);
    }

    @Override
    public void setAvailablePlaces(List<Coordinates> availablePlaces){
        viewModel.setAvailablePlaces(availablePlaces);
    }

    @Override
    public void setCanBePlaced(boolean[] canBePlaced){
        viewModel.setCanBePlaced(canBePlaced);
    }
    @Override
    public void setDisposition(String player, HashMap<Coordinates, PlayableCard> disposition){
        viewModel.setDisposition(player, disposition);
    }


    public void setPlayerID(String playerId){
        viewModel.setPlayerId(playerId);
    }

    @Override
    public boolean getMyTurn() {
        return viewModel.getMyTurn();
    }

    public void cantPlaceACard(PlayableCard card, Coordinates coord){
        ui.cantPlaceACard(card, coord);
    }

    @Override
    public void cantDrawCard(int source) {
        ui.cantDrawCard(source);
    }

    @Override
    public void cantJoinRoom() {
        ui.cantJoinRoom();
    }

    @Override
    public void cantCreateRoom() {
        ui.cantCreateRoom();
    }

    @Override
    public void returnToLobby() {
        viewModel.resetGameID();
        viewModel.setGameAsNotStarted();
        ui.returnToLobby();
    }

    public void displayStarterCard(){ui.printStarterCard();}

    public void displaySecretObjective(){ui.printSecretObjective();};
    public void displayReconnection(){
        if(ui instanceof TUI){
            inputThread = new Thread(ui);
            inputThread.start();
        }
        ui.displayReconnection();
    }

//    ////////////////////////////////// GETTER METHODS //////////////////////////////////////////////////////////////////////////////

    /**
     * returns the disposition of THIS player
     *
     * @return hash map
     */
    public HashMap<Coordinates, PlayableCard> getDisposition(){
        return viewModel.getDisposition();
    }

    /**
     * Get starter card playable card.
     *
     * @return the playable card
     */
    public PlayableCard getStarterCard(){
        return viewModel.getStarterCard();
    }

    /**
     * Get hand list.
     *
     * @return the list
     */
    public List<PlayableCard> getHand(){return viewModel.getHand();}

    /**
     * Gets common objectives.
     *
     * @return the common objectives
     */
    public List<ObjectiveCard> getCommonObjectives() {
        return viewModel.getCommonObjectives();
    }

    /**
     * Gets choose secret objectives.
     *
     * @return the choose secret objectives
     */
    public List<ObjectiveCard> getChooseSecretObjectives() {
        return viewModel.getChooseSecretObjectives();
    }

    /**
     * Get available places list.
     *
     * @return the list
     */
    public List<Coordinates> getAvailablePlaces(){
        return viewModel.getAvailablePlaces();
    }

    /**
     * Get can be placed boolean [ ].
     *
     * @return the boolean [ ]
     */
    public boolean[] getCanBePlaced() {
        return viewModel.getCanBePlaced();
    }

    /**
     * Gets points.
     *
     * @return the points
     */
    public HashMap<String, Integer> getPoints() {
        return viewModel.getPoints();
    }

    /**
     * Gets player id.
     *
     * @return the player id
     */
    public String getPlayerId() {
        return viewModel.getPlayerId();
    }

    /**
     * Gets available artifacts.
     *
     * @param player the player
     * @return the available artifacts
     */
    public HashMap<Artifact, Integer> getAvailableArtifacts(String player) {
        return viewModel.getAvailableArtifacts(player);
    }

    /**
     * Gets available elements.
     *
     * @param player the player
     * @return the available elements
     */
    public HashMap<Element, Integer> getAvailableElements(String player) {
        return viewModel.getAvailableElements(player);
    }

    /**
     * Gets gold deck.
     *
     * @return the gold deck
     */
    public List<PlayableCard> getGoldDeck() {
        return viewModel.getGoldDeck();
    }

    /**
     * Gets resource deck.
     *
     * @return the resource deck
     */
    public List<PlayableCard> getResourceDeck() {
        return viewModel.getResourceDeck();
    }

    /**
     * Gets open gold.
     *
     * @return the open gold
     */
    public List<PlayableCard> getOpenGold() {
        return viewModel.getOpenGold();
    }

    /**
     * Gets open resource.
     *
     * @return the open resource
     */
    public List<PlayableCard> getOpenResource() {
        return viewModel.getOpenResource();
    }

    /**
     * Gets turn player.
     *
     * @return the turn player
     */
    public String getTurnPlayer() {
        return viewModel.getTurnPlayer();
    }

    @Override
    public void setTurnPlayer(String turnPlayer) {
        viewModel.setTurnPlayer(turnPlayer);
    }

    /**
     * Get secret objective objective card.
     *
     * @return the objective card
     */
    public ObjectiveCard getSecretObjective(){return viewModel.getSecretObjective();}

    /**
     * Get dispositions hash map.
     *
     * @return the hash map
     */
    public  HashMap< String, HashMap< Coordinates, PlayableCard> > getDispositions(){
        return viewModel.getDispositions();
    }

    /**
     * Get ui ui.
     *
     * @return the ui
     */
    public UI getUi(){
        return ui;
    }

    /**
     * Get players list.
     *
     * @return the list
     */
    public List<String> getPlayers(){
        return viewModel.getPlayers();
    }

    /**
     * Get winners list.
     *
     * @return the list
     */
    public List<String> getWinners(){return  viewModel.getWinners();}

    /**
     * Check available boolean.
     *
     * @param x the x
     * @param y the y
     * @return the boolean
     */
///////////////////// functions used for input controls ///////////////////////////
    public boolean checkAvailable(int x, int y){
        for(Coordinates c : viewModel.getAvailablePlaces()){
            if(c.equals(new Coordinates(x,y))) return true;
        }
        return false;
    }


    /**
     * Check can draw from boolean.
     *
     * @param cardSource the card source
     * @return the boolean
     */
    public boolean checkCanDrawFrom(int cardSource) {
        switch (cardSource) {
            case 1:
                return !viewModel.getGoldDeck().isEmpty();
            case 2:
                return !viewModel.getOpenGold().isEmpty();
            case 3:
                return viewModel.getOpenGold().size() > 1;
            case 4:
                return !viewModel.getResourceDeck().isEmpty();
            case 5:
                return !viewModel.getOpenResource().isEmpty();
            case 6:
                return viewModel.getOpenResource().size() > 1;
        }
        return false;
    }

    public void returnToChooseGame(){
        ui.returnToChooseGame();
    }

    @Override
    public void deliverTextMessage(ChatMessage message) {
        viewModel.receiveTextMessage(message);
    }


    @Override
    public void resetChatHistory(ArrayList<ChatMessage> history) {
        viewModel.resetChatHistory(history);
    }


    /**
     * Gets chat history.
     *
     * @return the chat history
     */
    public List<ChatMessage> getChatHistory() {
        return viewModel.getChatHistory();
    }

    /**
     * notifies the viewModel that the game has started
     */
    public void setGameAsStarted(){
        viewModel.setGameAsStarted();
    }

    @Override
    public void updateResourcesInUI() {
        ui.updateResourcesInUI();
    }

    @Override
    public void returnToStart() {
        readChatMessagesLoop.interrupt();
        readMessagesLoop.interrupt();
        stopHeartBeat();
        viewModel.resetClientAPIGo();
        viewModel.resetGameID();
        viewModel.setGameAsNotStarted();
        ui.returnToStart();
    }

    /**
     * Is game started boolean.
     *
     * @return true if the game is started, else false. The game is intended as started when the part of playing takes place (not when players are in the waiting room)
     */
    public boolean isGameStarted(){
        return viewModel.isGameStarted();
    }

    /**
     * Send chat message.
     *
     * @param content the content
     */
    public void sendChatMessage(String content){
        viewModel.sendChatMessage(content);
    }

    /**
     * Send private chat message.
     *
     * @param content  the content
     * @param receiver the receiver
     */
    public void sendPrivateChatMessage(String content, String receiver){
        viewModel.sendPrivateChatMessage(content, receiver);
    }
}
