package model.GameState;

import Server.ModelListener;
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

public class MockModelListener extends ModelListener {

    public MockModelListener() {
        super(null);
    }

    /**
     * notification about current state of GameState
     * @param state
     */
    public void notifyChanges(TurnState state){
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
     * we update the player resources(elements, artifacts) NOT IN BROADCAST
     * @param player
     * @param availableArtifacts
     * @param availableElements
     */
    public void notifyChanges (String player, HashMap<Artifact, Integer> availableArtifacts,
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
    public void notifyChanges(List<PlayableCard> deck, int cardSource, int index) {
        System.out.println("calledNotifyChanges");
    }

    /**
     * at the end of the game each player is notified with the final points of every player,
     * the view is responsible for displaying the winner
     * @param finalPoints
     */
    public void notifyChanges(HashMap<String, Integer> finalPoints){
        System.out.println("calledNotifyChanges");
    }
    ////////////////////////////// ERROR NOTIFICATIONS ///////////////////////////////////////////////////////////////
    public void notifyChanges(String player, CantPlaceCardException e) {
        System.out.println("calledNotifyChanges");
    }
    public void notifyChanges(String player, KickOutOfGameException e){
        System.out.println("calledNotifyChanges");
    }
    public void notifyChanges(String player, EmptyCardSourceException e){
        System.out.println("calledNotifyChanges");
    }

}
