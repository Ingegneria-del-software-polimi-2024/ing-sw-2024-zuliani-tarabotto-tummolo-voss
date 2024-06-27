package Server;


import Chat.MessagesFromServer.ChatHistoryMessage;
import Chat.MessagesFromServer.ChatUpdateMessage;
import Server.Web.Game.ServerAPI_GO;
import SharedWebInterfaces.Messages.MessagesFromServer.*;
import SharedWebInterfaces.Messages.MessagesFromServer.Errors.CantPlaceCardMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.Errors.EmptyDeckMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.Errors.KickOutOfGameMessage;
import SharedWebInterfaces.Messages.MessagesFromServer.ReconnectionsMSG.DisplayObjectiveSelection;
import SharedWebInterfaces.Messages.MessagesFromServer.ReconnectionsMSG.DisplayStarterCardSelection;
import SharedWebInterfaces.Messages.MessagesFromServer.ReconnectionsMSG.ReconnectionHappened;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;
import model.Exceptions.CantPlaceCardException;
import model.Exceptions.EmptyCardSourceException;
import model.Exceptions.KickOutOfGameException;
import model.GameState.TurnState;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.PlayableDeck;
import model.enums.Artifact;
import model.enums.Element;
import model.enums.Pawn;
import model.placementArea.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The type Model listener.
 * This class is responsible for sending notifications to the players about the changes in the game state
 */
public class ModelListener {//TODO .

    /**
     * The API used to send messages to the clients
     */
    private ServerAPI_GO serverAPI;

    /**
     * Instantiates a new Model listener.
     *
     * @param serverAPI the server api
     */
    public ModelListener(ServerAPI_GO serverAPI){
        this.serverAPI = serverAPI;
    }


    //////////////////////// GAME SETUP NOTIFICATIONS ///////////////////////////////////////////////////

    /**
     * notification about current state of GameState
     *
     * @param state the state to be set
     */
    public void notifyChanges(TurnState state){
        try{
            System.out.println("state notification send");
            serverAPI.broadcastNotifyChanges( new StateMessage(state));
        } catch(MsgNotDeliveredException msg) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * notification about current state of GameState, can be directed to a single player
     *
     * @param state    the state to be set
     * @param playerID the receiver of the message
     */
    public void notifyChanges(TurnState state, String playerID){
        try{
            System.out.println("state notification send");
            serverAPI.notifyChanges( new StateMessage( state ), playerID);
        } catch(MsgNotDeliveredException msg) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * notification about the new turnPlayer
     *
     * @param turnPlayer the turn player
     */
    public void notifyChanges(String turnPlayer){
        try{
            System.out.println("notification send");
            serverAPI.broadcastNotifyChanges( new TurnPlayerMessage(turnPlayer));
        } catch(MsgNotDeliveredException msg) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * notification about the new turnPlayer
     *
     * @param turnPlayer the player playing at the moment
     * @param playerID   the player to send the message to
     */
    public void notifyChanges(String turnPlayer, String playerID){
        try{
            System.out.println("notification send");
            serverAPI.notifyChanges( new TurnPlayerMessage(turnPlayer), playerID);
        } catch(MsgNotDeliveredException msg) {
            throw new RuntimeException(msg);
        }
    }


    /**
     * notification with data about the order of the cards in decks
     *
     * @param goldDeck         the gold deck
     * @param resourceDeck     the resource deck
     * @param openGold         the open gold
     * @param openResource     the open resource
     * @param players          the players
     * @param gameId           the game id
     * @param commonObjective1 the common objective 1
     * @param commonObjective2 the common objective 2
     */
    public void notifyChanges(PlayableDeck goldDeck, PlayableDeck  resourceDeck, List<PlayableCard> openGold,
                              List<PlayableCard> openResource,
                              ArrayList<String> players, String gameId,
                              ObjectiveCard commonObjective1, ObjectiveCard commonObjective2){


        try{
            serverAPI.broadcastNotifyChanges( new InitializationMessage( goldDeck.getCards(), resourceDeck.getCards(),
                    openGold, openResource,
                     players, gameId,
                    commonObjective1, commonObjective2));
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }

    }

    /**
     * Notification with data about the order of the cards in decks sent to a single player, used when a player reconnects
     *
     * @param goldDeck         the gold deck
     * @param resourceDeck     the resource deck
     * @param openGold         the open gold
     * @param openResource     the open resource
     * @param players          the players
     * @param gameId           the game id
     * @param commonObjective1 the common objective 1
     * @param commonObjective2 the common objective 2
     * @param playerID         the receiver player id
     */
    public void notifyChanges(PlayableDeck goldDeck, PlayableDeck  resourceDeck, List<PlayableCard> openGold,
                              List<PlayableCard> openResource,
                              ArrayList<String> players, String gameId,
                              ObjectiveCard commonObjective1, ObjectiveCard commonObjective2, String playerID){
        try{
            serverAPI.notifyChanges(new InitializationMessage( goldDeck.getCards(), resourceDeck.getCards(),
                    openGold, openResource,
                    players, gameId,
                    commonObjective1, commonObjective2), playerID);
        }catch (MsgNotDeliveredException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * broadcast of the player pawn color
     *
     * @param player    the player
     * @param pawnColor the pawn color
     */
    public void notifyChanges( String player, Pawn pawnColor){
        try{
            serverAPI.broadcastNotifyChanges(new PawnColorMessage(player, pawnColor.toString()));
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }

    /**
     * Sends the player pawn color to a specific player, used when a player reconnects
     *
     * @param player            the player
     * @param pawnColor         the pawn color
     * @param reconnectedPlayer the reconnected player (the one that needs to be notified)
     */
    public void notifyChanges( String player, Pawn pawnColor, String reconnectedPlayer){
        try{
            serverAPI.notifyChanges(new PawnColorMessage(player, pawnColor.toString()), reconnectedPlayer);
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }


    /**
     * The player is notified with the starterCard that he has been given and the PAWNCOLOR
     *
     * @param starterCard the starter card
     * @param player      the player
     * @param pawnColor   the pawn color
     */
    public void notifyChanges(PlayableCard starterCard, String player, Pawn pawnColor){
        try{
            serverAPI.notifyChanges( new StarterCardMessage(player, starterCard, pawnColor.toString()), player );
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }

    /**
     * the player is notified with his hand of cards
     *
     * @param hand   the hand
     * @param player the player
     */
    public void notifyChanges(List<PlayableCard> hand , String player){

        try{
            serverAPI.notifyChanges( new UpdateHandMessage(hand), player);
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }

    /**
     * notification about the two secretObjectives for each player
     *
     * @param secretObjective1 the secret objective 1
     * @param secretObjective2 the secret objective 2
     * @param player           the player
     */
    public void notifyChanges(ObjectiveCard secretObjective1, ObjectiveCard secretObjective2, String player){

        try{
            serverAPI.notifyChanges( new SecretObjectivesMessage(secretObjective1, secretObjective2), player);
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }


    /**
     * after each player chooses his secretObjective, a notification is sent
     *
     * @param secretObjective the secret objective
     * @param player          the player
     */
    public void notifyChanges(ObjectiveCard secretObjective, String player){
        try{
            serverAPI.notifyChanges( new ConfirmSecretObjectiveMessage(secretObjective), player);
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// ROUND NOTIFICATIONS ///////////////////////////////////////////////////////////////

    /**
     * At the beginning of the turn of turnPlayer, the model notifies the player about which cards
     * of his hand can be placed(due to placementConstraint) and also where the cards can be placed
     *
     * @param player          the player
     * @param availablePlaces the available places
     * @param canBePlaced     true if the card can be placed face side, false otherwise
     */
    public void notifyChanges(String player, List<Coordinates> availablePlaces, boolean[] canBePlaced){
        try{
            serverAPI.notifyChanges( new PlaceableCardsMessage(availablePlaces, canBePlaced), player);
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }


    /**
     * after placing the card each player is notified with his update disposition, points, elements and artifacts
     *
     * @param player          the player
     * @param disposition     the disposition
     * @param availablePlaces the available places
     * @param points          the points
     */
    public void notifyChanges(String player, HashMap<Coordinates, PlayableCard> disposition, List<Coordinates> availablePlaces,
                              int points) {


        try{
            serverAPI.broadcastNotifyChanges(new UpdateDispositionMessage(player, disposition, availablePlaces,
                    points));
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }

    }

    /**
     * after placing the card the reconnected player is notified with his update disposition, points, elements and artifacts, used when a player reconnects
     *
     * @param player          the player
     * @param disposition     the disposition
     * @param availablePlaces the available places
     * @param points          the points
     * @param recipient       the recipient
     */
    public void notifyChanges(String player, HashMap<Coordinates, PlayableCard> disposition, List<Coordinates> availablePlaces,
                              int points, String recipient) {
        try{
            serverAPI.notifyChanges(new UpdateDispositionMessage(player, disposition, availablePlaces,
                    points), recipient);
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }

    }

    /**
     * we update the player resources(elements, artifacts)
     *
     * @param player             the player
     * @param availableArtifacts the available artifacts
     * @param availableElements  the available elements
     */
    public void notifyChanges (String player, HashMap<Artifact, Integer> availableArtifacts,
                               HashMap<Element, Integer> availableElements){
        try{
            serverAPI.broadcastNotifyChanges(new UpdateResourcesMessage(player, availableElements, availableArtifacts));
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }

    /**
     * we update the player resources(elements, artifacts) NOT IN BROADCAST, used when a player reconnects
     *
     * @param player             the player
     * @param availableArtifacts the available artifacts
     * @param availableElements  the available elements
     */
    public void personalNotifyChanges (String player, HashMap<Artifact, Integer> availableArtifacts,
                               HashMap<Element, Integer> availableElements){
        try{
            serverAPI.notifyChanges(new UpdateResourcesMessage(player, availableElements, availableArtifacts), player);
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }

    /**
     * After the player decided where to draw the next card from, the involved card source gets updated based on cardSource
     *
     * @param deck       the deck from which the card is drawn
     * @param cardSource the card source
     */
    public void notifyChanges(List<PlayableCard> deck, int cardSource) {
        try{
            serverAPI.broadcastNotifyChanges( new DrawDeckCardMessage(deck, cardSource));
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }

    /**
     * after the player decided where to draw the next card from, the involved card source gets updated based on cardSource
     * method used when picking a card from an "open card" deck
     *
     * @param deck       the deck
     * @param cardSource the card source
     * @param index      the index
     */
    public void notifyChanges(List<PlayableCard> deck, int cardSource, int index) {
        try{
            serverAPI.broadcastNotifyChanges( new DrawOpenCardMessage(deck, cardSource, index));
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }

    /**
     * at the end of the game each player is notified with the final points of every player,
     * the view is responsible for displaying the winner
     *
     * @param finalPoints a hashmap containing the players' names and their points
     * @param winners     the winners
     */
    public void notifyChanges(HashMap<String, Integer> finalPoints, ArrayList<String> winners){

        try{
            serverAPI.broadcastNotifyChanges(new EndGameMessage(finalPoints, winners));
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }
    ////////////////////////////// ERROR NOTIFICATIONS ///////////////////////////////////////////////////////////////

    /**
     * notify a player when can't play a card
     *
     * @param player the player's nickname
     * @param e      the raised exception by the controller
     */
    public void notifyChanges(String player, CantPlaceCardException e) {
        try {
            serverAPI.notifyChanges(new CantPlaceCardMessage(player, e.getCard(), e.getCoord()), player);
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }

    /**
     * notifies a player when he is kicked out of the game or exits it
     *
     * @param player the player's nickname
     * @param e      the raised exception
     * @return true if the message is correctly delivered, else false
     */
    public boolean notifyChanges(String player, KickOutOfGameException e){
        try {
            serverAPI.notifyChanges(new KickOutOfGameMessage(player), player);
            return true;
        }catch(MsgNotDeliveredException msg){
            return false;
        }
    }

    /**
     * notify a player when can't draw from a deck
     *
     * @param player the player's nickname
     * @param e      the raised exception
     */
    public void notifyChanges(String player, EmptyCardSourceException e){
        try {
            serverAPI.notifyChanges(new EmptyDeckMessage(e.getIndx()), player);
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(e);
        }
    }


    /**
     * Notifies when the reconnection of a player correctly happens.
     *
     * @param playerID the player id
     */
    public void notifyReconnection(String playerID){
        try {
            serverAPI.notifyChanges(new ReconnectionHappened(playerID), playerID);
        } catch (MsgNotDeliveredException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Notify of the choosing of the starter card to the player.
     *
     * @param playerID    the player id
     * @param starterCard the starter card
     * @param color       the color of the pawn for the player
     */
    public void displayStarterCardNotification(String playerID, PlayableCard starterCard, Pawn color){
        try {
            serverAPI.notifyChanges(new StarterCardMessage(playerID, starterCard, color.toString()), playerID);
            serverAPI.notifyChanges(new DisplayStarterCardSelection(playerID), playerID);
        } catch (MsgNotDeliveredException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays the choosing objective notification.
     *
     * @param playerID the player id
     */
    public void displayObjectiveNotification(String playerID){
        try {
            serverAPI.notifyChanges(new DisplayObjectiveSelection(playerID), playerID);
        } catch (MsgNotDeliveredException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Broadcast chat message.
     *
     * @param message the message
     */
    public void broadcastChatMessage(ChatUpdateMessage message){
        try {
            serverAPI.broadcastNotifyChanges(message);
        }catch (MsgNotDeliveredException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Send private chat message.
     *
     * @param message  the message
     * @param receiver the receiver
     */
    public void sendPrivateChatMessage(ChatUpdateMessage message, String receiver){
        try{
            serverAPI.notifyChanges(message, receiver);
        }catch (MsgNotDeliveredException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Send chat history.
     *
     * @param player the player
     * @param msg    the msg
     */
    public void sendChatHistory(String player, ChatHistoryMessage msg){
        try {
            serverAPI.notifyChanges(msg, player);
        } catch (MsgNotDeliveredException e) {
            throw new RuntimeException(e);
        }
    }
}
