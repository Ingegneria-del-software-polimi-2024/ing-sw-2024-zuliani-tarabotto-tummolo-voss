package model.GameState;

import Chat.MessagesFromServer.ChatHistoryMessage;
import Chat.MessagesFromServer.ChatUpdateMessage;
import Server.ModelListener;
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
 * A mock implementation of the ModelListener class for testing purposes.
 */
public class MockModelListener extends ModelListener {

    /**
     * Constructs a new MockModelListener with a null server.
     */
    public MockModelListener() {
        super(null);
    }

    /**
     * notification about current state of GameState
     * @param state the state to be set
     */
    public void notifyChanges(TurnState state){
        System.out.println("calledNotifyChanges");

    }

    /**
     * notification about current state of GameState, can be directed to a single player
     * @param state the state to be set
     * @param playerID the receiver of the message
     */
    public void notifyChanges(TurnState state, String playerID){
        System.out.println("calledNotifyChanges");

    }

    /**
     * notification about the new turnPlayer
     * @param turnPlayer
     */
    public void notifyChanges(String turnPlayer){
        System.out.println("calledNotifyChanges");

    }

    /**
     * notification about the new turnPlayer
     * @param turnPlayer the player playing at the moment
     * @param playerID the player to send the message to
     */
    public void notifyChanges(String turnPlayer, String playerID){
        System.out.println("calledNotifyChanges");

    }


    /**
     * notification with data about the order of the cards in decks
     * @param goldDeck
     * @param resourceDeck
     * @param openGold
     * @param openResource
     * @param players
     * @param gameId
     * @param commonObjective1
     * @param commonObjective2
     */
    public void notifyChanges(PlayableDeck goldDeck, PlayableDeck  resourceDeck, List<PlayableCard> openGold,
                              List<PlayableCard> openResource,
                              ArrayList<String> players, String gameId,
                              ObjectiveCard commonObjective1, ObjectiveCard commonObjective2){
        System.out.println("calledNotifyChanges");


    }

    /**
     * notification with data about the order of the cards in decks
     * @param goldDeck
     * @param resourceDeck
     * @param openGold
     * @param openResource
     * @param players
     * @param gameId
     * @param commonObjective1
     * @param commonObjective2
     * @param playerID
     */
    public void notifyChanges(PlayableDeck goldDeck, PlayableDeck  resourceDeck, List<PlayableCard> openGold,
                              List<PlayableCard> openResource,
                              ArrayList<String> players, String gameId,
                              ObjectiveCard commonObjective1, ObjectiveCard commonObjective2, String playerID){
        System.out.println("calledNotifyChanges");

    }

    /**
     * we broadcast the player pawn color
     * @param player
     * @param pawnColor
     */
    public void notifyChanges( String player, Pawn pawnColor){
        System.out.println("calledNotifyChanges");

    }


    /**
     * the player is notified with the starterCard that he has been given and the PAWNCOLOR
     * @param starterCard
     * @param player
     */
    public void notifyChanges(PlayableCard starterCard, String player, Pawn pawnColor){
        System.out.println("calledNotifyChanges");

    }

    /**
     * the player is notified with his hand of cards
     * @param hand
     * @param player
     */
    public void notifyChanges(List<PlayableCard> hand , String player){

        System.out.println("calledNotifyChanges");

    }

    /**
     * notification about the two secretObjectives for each player
     * @param secretObjective1
     * @param secretObjective2
     */
    public void notifyChanges(ObjectiveCard secretObjective1, ObjectiveCard secretObjective2, String player){

        System.out.println("calledNotifyChanges");

    }


    /**
     * after each player chooses his secretObjective, a notification is sent
     * @param secretObjective
     * @param player
     */
    public void notifyChanges(ObjectiveCard secretObjective, String player){
        System.out.println("calledNotifyChanges");

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// ROUND NOTIFICATIONS ///////////////////////////////////////////////////////////////

    /**
     * At the beginning of the turn of turnPlayer, the model notifies the player about which cards
     * of his hand can be placed(due to placementConstraint) and also where the cards can be placed
     * @param availablePlaces
     * @param player
     * @param canBePlaced
     */
    public void notifyChanges(String player, List<Coordinates> availablePlaces, boolean[] canBePlaced){
        System.out.println("calledNotifyChanges");

    }


    /**
     * after placing the card each player is notified with his update disposition, points, elements and artifacts
     * @param player
     * @param disposition
     * @param availablePlaces
     * @param points
     */
    public void notifyChanges(String player, HashMap<Coordinates, PlayableCard> disposition, List<Coordinates> availablePlaces,
                              int points) {

        System.out.println("calledNotifyChanges");


    }

    /**
     * after placing the card each player is notified with his update disposition, points, elements and artifacts
     * @param player
     * @param disposition
     * @param availablePlaces
     * @param points
     */
    public void notifyChanges(String player, HashMap<Coordinates, PlayableCard> disposition, List<Coordinates> availablePlaces,
                              int points, String recipient) {
        System.out.println("calledNotifyChanges");
    }

    /**
     * we update the player resources(elements, artifacts)
     * @param player
     * @param availableArtifacts
     * @param availableElements
     */
    public void notifyChanges (String player, HashMap<Artifact, Integer> availableArtifacts,
                               HashMap<Element, Integer> availableElements){
        System.out.println("calledNotifyChanges");
    }

    /**
     * we update the player resources(elements, artifacts) NOT IN BROADCAST
     * @param player
     * @param availableArtifacts
     * @param availableElements
     */
    public void personalNotifyChanges (String player, HashMap<Artifact, Integer> availableArtifacts,
                                       HashMap<Element, Integer> availableElements){
        System.out.println("calledNotifyChanges");
    }

    /**
     * after the player decided where to draw the next card from, the involved card source gets updated based on cardSource
     * @param deck
     * @param cardSource
     */
    public void notifyChanges(List<PlayableCard> deck, int cardSource) {
        System.out.println("calledNotifyChanges");
    }

    /**
     * after the player decided where to draw the next card from, the involved card source gets updated based on cardSource
     * method used when picking a card from an "open card" deck
     * @param deck
     * @param cardSource
     * @param index
     */
    public void notifyChanges(List<PlayableCard> deck, int cardSource, int index) {
        System.out.println("calledNotifyChanges");
    }

    /**
     * at the end of the game each player is notified with the final points of every player,
     * the view is responsible for displaying the winner
     * @param finalPoints an hashmap containing the players' names and their points
     */
    public void notifyChanges(HashMap<String, Integer> finalPoints, ArrayList<String> winners){

        System.out.println("calledNotifyChanges");
    }
    ////////////////////////////// ERROR NOTIFICATIONS ///////////////////////////////////////////////////////////////

    /**
     * notify a player when can't play a card
     * @param player the player's nickname
     * @param e the raised exception
     */
    public void notifyChanges(String player, CantPlaceCardException e) {
        System.out.println("calledNotifyChanges");

    }

    /**
     * notify a player when he is kicked out of the game or exits it
     * @param player the player's nickname
     * @param e the raised exception
     */
    public boolean notifyChanges(String player, KickOutOfGameException e){
        System.out.println("calledNotifyChanges");
        return true;
    }

    /**
     * notify a player when can't draw from a deck
     * @param player the player's nickname
     * @param e the raised exception
     */
    public void notifyChanges(String player, EmptyCardSourceException e){
        System.out.println("calledNotifyChanges");

    }

    /**
     * Notifies about a player's reconnection.
     *
     * @param playerID The ID of the player who reconnected.
     */
    public void notifyReconnection(String playerID){
        System.out.println("calledNotifyChanges");

    }

    /**
     * Sends a notification with the starter card and the pawn color to a player.
     *
     * @param playerID The ID of the player to whom the notification is sent.
     * @param starterCard The starter card of the player.
     * @param color The color of the player's pawn.
     */
    public void displayStarterCardNotification(String playerID, PlayableCard starterCard, Pawn color){
        System.out.println("calledNotifyChanges");

    }

    /**
     * Sends a notification to display the objective to a player.
     *
     * @param playerID The ID of the player to whom the notification is sent.
     */
    public void displayObjectiveNotification(String playerID){
        System.out.println("calledNotifyChanges");

    }

    /**
     * Broadcasts a chat message to all players.
     *
     * @param message The chat message to be broadcasted.
     */
    public void broadcastChatMessage(ChatUpdateMessage message){
        System.out.println("calledNotifyChanges");

    }

    /**
     * Sends the chat history to a player.
     *
     * @param player The ID of the player to whom the chat history is sent.
     * @param msg The message containing the chat history.
     */
    public void sendChatHistory(String player, ChatHistoryMessage msg){
        System.out.println("calledNotifyChanges");
    }

}

