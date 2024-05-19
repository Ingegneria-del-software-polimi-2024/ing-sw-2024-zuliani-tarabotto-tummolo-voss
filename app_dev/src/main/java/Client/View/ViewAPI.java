package Client.View;

import Client.UI.TUI.TUI;
import Client.UI.UI;
import Client.Web.ClientAPI_COME;
import Client.Web.ClientAPI_GO;
import Client.Web.RMI_ServerHandler;
import Client.Web.SOCKET_ServerHandler;
import SharedWebInterfaces.Messages.MessagesToLobby.JoinGameMessage;
import SharedWebInterfaces.SharedInterfaces.ServerHandlerInterface;
import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import SharedWebInterfaces.WebExceptions.StartConnectionFailedException;
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

    private UI ui;
    private ViewModel viewModel;

    public ViewAPI() {
        this.ui = new TUI(this);
        this.viewModel = new ViewModel(ui);
    }
    //TODO: clientAPI_GO deve essere passato come parametro

    public void setClientAPIGo(ClientAPI_GO clientAPI_GO){
        viewModel.setClientAPIGo(clientAPI_GO);
    }

    /////////// from CLIENT to SERVER  ACTIONS ////////////////////////////////////////////////////////////////////////////////////
    //all this methods create a new MessageFromClient object containing an execute() method with the call to a specific method of ModelController
    public void playStarterCard(){
        viewModel.playStarterCard();
    }

//    public void chooseSecretObjective(String chosenObjective){
//        viewModel.chooseSecretObjective(chosenObjective);
//    }

    public void playCard(PlayableCard c, int x, int y) {viewModel.playCard(c,x,y);}

    public void drawCard(int cardSource){viewModel.drawCard(cardSource);}

    @Override
    public void readyToPlay(){viewModel.readyToPlay();}

//////////////////////////////////////////Lobby/////////////////////////////////////////////////////////////////////////
    public void startConnection(String in, String host, int port, int localPort){
        ClientAPI_COME clientAPICome = new ClientAPI_COME(this);
        Thread readMessagesLoop = new Thread(clientAPICome);
        readMessagesLoop.start();
        try {
            ServerHandlerInterface serverHandler;
            if (in.equalsIgnoreCase("RMI")) {
                serverHandler = new RMI_ServerHandler(host, port, clientAPICome, localPort);
            } else {
                //debug
                System.out.println("instantiating handler");
                serverHandler = new SOCKET_ServerHandler(host, port, clientAPICome);
                //debug
                System.out.println("starting thread");
                Thread listeningThread = new Thread((SOCKET_ServerHandler)serverHandler);
                listeningThread.start();
                //debug
                System.out.println("started listening for messages");
            }
            ClientAPI_GO clientAPIGo = new ClientAPI_GO(serverHandler);
            this.setClientAPIGo(clientAPIGo);
        }catch (StartConnectionFailedException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            throw new RuntimeException("Couldn't instaurate the connection due to a net error");
        }


    }

    public void chooseConnection(){
        ui.chooseConnection();
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
    public void requestAvailableGames(){
        viewModel.requestAvailableGames();
    }
    public void joinGame(String game, int players){
        viewModel.joinGame(game, players);
    }

//////////////////////////////////////////Lobby/////////////////////////////////////////////////////////////////////////

    //    /////////// from SERVER to CLIENT ACTIONS ////////////////////////////////////////////////////////////////////////////////////
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


    //TODO: rendere più chiara la scelta di secretObjective, confirmSecretObjective potrebbe essere eliminato

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
    public void updateArtifacts(HashMap<Artifact, Integer> artifacts) {
        viewModel.updateArtifacts(artifacts);
    }

    @Override
    public void updateElements(HashMap<Element, Integer> elements) {
        viewModel.updateElements(elements);
    }

    @Override
    public void updateCardSource(List<PlayableCard> deck, int cardSource) {
        viewModel.updateCardSource(deck, cardSource);
    }
    public void updateOpenCards(List<PlayableCard> decK, int cardSource){
        viewModel.updateOpenCards(decK, cardSource);
    }

    @Override
    public void endGame(HashMap<String, Integer> finalPoints) {
        viewModel.endGame(finalPoints);
    }

    @Override
    public void setPawnColor(String pawnColor) {viewModel.setPawnColor(pawnColor);}

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


    public void setPlayerId(String playerId){
        viewModel.setPlayerId(playerId);
    }

    @Override
    public boolean getMyTurn() {
        return viewModel.getMyTurn();
    }


//    ////////////////////////////////// GETTER METHODS //////////////////////////////////////////////////////////////////////////////

    /**
     * returns the disposition of THIS player
     * @return
     */
    public HashMap<Coordinates, PlayableCard> getDisposition(){
        return viewModel.getDisposition();
    }

    public PlayableCard getStarterCard(){
        return viewModel.getStarterCard();
    }

    public List<PlayableCard> getHand(){return viewModel.getHand();}

    public List<ObjectiveCard> getCommonObjectives() {
        return viewModel.getCommonObjectives();
    }

    public List<ObjectiveCard> getChooseSecretObjectives() {
        return viewModel.getChooseSecretObjectives();
    }
    public List<Coordinates> getAvailablePlaces(){
        return viewModel.getAvailablePlaces();
    }

    public boolean[] getCanBePlaced() {
        return viewModel.getCanBePlaced();
    }

    public HashMap<String, Integer> getPoints() {
        return viewModel.getPoints();
    }

    public String getPlayerId() {
        return viewModel.getPlayerId();
    }

    public HashMap<Artifact, Integer> getAvailableArtifacts() {
        return viewModel.getAvailableArtifacts();
    }

    public HashMap<Element, Integer> getAvailableElements() {
        return viewModel.getAvailableElements();
    }

    public List<PlayableCard> getGoldDeck() {
        return viewModel.getGoldDeck();
    }

    public List<PlayableCard> getResourceDeck() {
        return viewModel.getResourceDeck();
    }

    public List<PlayableCard> getOpenGold() {
        return viewModel.getOpenGold();
    }

    public List<PlayableCard> getOpenResource() {
        return viewModel.getOpenResource();
    }

    public String getTurnPlayer() {
        return viewModel.getTurnPlayer();
    }

    @Override
    public void setTurnPlayer(String turnPlayer) {
        viewModel.setTurnPlayer(turnPlayer);
    }

    public ObjectiveCard getSecretObjective(){return viewModel.getSecretObjective();}
}
