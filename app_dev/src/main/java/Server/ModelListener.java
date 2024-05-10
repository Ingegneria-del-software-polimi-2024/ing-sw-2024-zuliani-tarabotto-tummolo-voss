package Server;


import Server.Web.Game.ServerAPI_GO;
import SharedWebInterfaces.Messages.MessagesFromServer.*;
import SharedWebInterfaces.WebExceptions.MsgNotDeliveredException;
import model.GameState.TurnState;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.ObjectiveDeck;
import model.deckFactory.PlayableDeck;
import model.enums.Artifact;
import model.enums.Element;
import model.enums.Pawn;
import model.placementArea.Coordinates;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ModelListener {

    private ServerAPI_GO serverAPI;

    public ModelListener(ServerAPI_GO serverAPI){
        this.serverAPI = serverAPI;
    }

    //////////////////////// GAME SETUP NOTIFICATIONS ///////////////////////////////////////////////////

    /**
     * notification about current state of GameState
     * @param state
     */
    public void notifyChanges(TurnState state){
        try{
            System.out.println("notification send");
            serverAPI.broadcastNotifyChanges( new StateMessage( state ));
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
    public void notifyChanges(PlayableDeck goldDeck, PlayableDeck  resourceDeck, List<PlayableCard> openGold, List<PlayableCard> openResource,
                              ArrayList<String> players, String gameId,
                              ObjectiveCard commonObjective1, ObjectiveCard commonObjective2){


        try{
            serverAPI.broadcastNotifyChanges( new InitializationMessage( goldDeck.getCards(), resourceDeck.getCards(), openGold, openResource,
                    (String[]) players.toArray(), gameId,
                    commonObjective1, commonObjective2));
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }

    }



    /**
     * the player is notified with the starterCard that he has been given and the PAWNCOLOR
     * @param starterCard
     * @param player
     */
    public void notifyChanges(PlayableCard starterCard, String player, Pawn pawnColor){
        try{
            serverAPI.notifyChanges( new StarterCardMessage(starterCard, pawnColor.toString()), player );
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
     * @param points
     * @param availableArtifacts
     * @param availableElements
     */
    public void notifyChanges(String player, HashMap<Coordinates, PlayableCard> disposition, int points,
                              HashMap<Artifact, Integer> availableArtifacts, HashMap<Element, Integer> availableElements) {


        try{
            serverAPI.broadcastNotifyChanges(new UpdateDispositionMessage(player, disposition,
                    points, availableArtifacts, availableElements));
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
            serverAPI.broadcastNotifyChanges( new DrawCardMessage(deck, cardSource));
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }

    /**
     * at the end of the game each player is notified with the final points of every player,
     * the view is responsible of displaying the winner
     * @param finalPoints
     */
    public void notifyChanges(HashMap<String, Integer> finalPoints){

        try{
            serverAPI.broadcastNotifyChanges(new EndGameMessage(finalPoints));
        }catch (MsgNotDeliveredException msg){
            throw new RuntimeException(msg);
        }
    }
}
