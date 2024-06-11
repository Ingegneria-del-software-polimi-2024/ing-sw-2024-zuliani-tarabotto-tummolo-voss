package Server;


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

public class ModelListener {//TODO Handle correctly the exceptions

    private ServerAPI_GO serverAPI;

    public ModelListener(ServerAPI_GO serverAPI){
        this.serverAPI = serverAPI;
    }


    //////////////////////// GAME SETUP NOTIFICATIONS ///////////////////////////////////////////////////

    /**
     * notification about current state of GameState
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
     * @param state the state to be set
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
     * @param turnPlayer
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
     * @param turnPlayer the player playing at the moment
     * @param playerID the player to send the message to
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


        try{
            serverAPI.broadcastNotifyChanges( new InitializationMessage( goldDeck.getCards(), resourceDeck.getCards(),
                    openGold, openResource,
                     players, gameId,
                    commonObjective1, commonObjective2));
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }

    }
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
     * the player is notified with the starterCard that he has been given and the PAWNCOLOR
     * @param starterCard
     * @param player
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
     * @param hand
     * @param player
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
     * @param secretObjective1
     * @param secretObjective2
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
     * @param secretObjective
     * @param player
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
     * @param availablePlaces
     * @param player
     * @param canBePlaced
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
     * @param player
     * @param disposition
     * @param availablePlaces
     * @param points
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
     * after placing the card each player is notified with his update disposition, points, elements and artifacts
     * @param player
     * @param disposition
     * @param availablePlaces
     * @param points
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
     * @param player
     * @param availableArtifacts
     * @param availableElements
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
     * we update the player resources(elements, artifacts) NOT IN BROADCAST
     * @param player
     * @param availableArtifacts
     * @param availableElements
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
     * after the player decided where to draw the next card from, the involved card source gets updated based on cardSource
     * @param deck
     * @param cardSource
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
     * @param deck
     * @param cardSource
     * @param index
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
     * @param finalPoints an hashmap containing the players' names and their points
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
     * @param player the player's nickname
     * @param e the raised exception
     */
    public void notifyChanges(String player, CantPlaceCardException e) {
        try {
            serverAPI.notifyChanges(new CantPlaceCardMessage(player, e.getCard(), e.getCoord()), player);
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }

    /**
     * notify a player when he is kicked out of the game or exits it
     * @param player the player's nickname
     * @param e the raised exception
     */
    public void notifyChanges(String player, KickOutOfGameException e){
        try {
            serverAPI.notifyChanges(new KickOutOfGameMessage(player), player);
        }catch(MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }

    /**
     * notify a player when can't draw from a deck
     * @param player the player's nickname
     * @param e the raised exception
     */
    public void notifyChanges(String player, EmptyCardSourceException e){
        try {
            serverAPI.notifyChanges(new EmptyDeckMessage(e.getIndx()), player);
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(e);
        }
    }


    public void notifyReconnection(String playerID){
        try {
            serverAPI.notifyChanges(new ReconnectionHappened(playerID), playerID);
        } catch (MsgNotDeliveredException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayStarterCardNotification(String playerID, PlayableCard starterCard, Pawn color){
        try {
            serverAPI.notifyChanges(new StarterCardMessage(playerID, starterCard, color.toString()), playerID);
            serverAPI.notifyChanges(new DisplayStarterCardSelection(playerID), playerID);
        } catch (MsgNotDeliveredException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayObjectiveNotification(String playerID){
        try {
            serverAPI.notifyChanges(new DisplayObjectiveSelection(playerID), playerID);
        } catch (MsgNotDeliveredException e) {
            throw new RuntimeException(e);
        }
    }
}
