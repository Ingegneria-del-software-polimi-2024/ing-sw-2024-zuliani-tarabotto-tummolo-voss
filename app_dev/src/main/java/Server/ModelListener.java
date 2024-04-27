package Server;


import SharedWebInterfaces.Messages.MessagesFromServer.*;
import model.GameState.TurnState;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.ObjectiveDeck;
import model.deckFactory.PlayableDeck;
import model.enums.Artifact;
import model.enums.Element;
import model.enums.Pawn;
import model.placementArea.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public void notifyChanges(String state){
        serverAPI.broadcastNotifyChanges( new StateMessage( state ));
    }


    /**
     * notification with data about the order of the cards in decks
     * @param goldDeck
     * @param resourceDeck
     * @param starterDeck
     * @param objectiveDeck
     * @param players
     * @param gameId
     */
    //TODO: add notification about each player's pawn color
    public void notifyChanges(PlayableDeck goldDeck, PlayableDeck  resourceDeck,
                              PlayableDeck  starterDeck, ObjectiveDeck objectiveDeck,
                              ArrayList<String> players, String gameId){

        int[] goldDeckInt = goldDeck.getCards().stream()
                .mapToInt(PlayableCard::getId)
                .toArray();

        int[] resourceDeckInt = resourceDeck.getCards().stream()
                .mapToInt(PlayableCard::getId)
                .toArray();

        int[] starterDeckInt = starterDeck.getCards().stream()
                .mapToInt(PlayableCard::getId)
                .toArray();

        int[] objectiveDeckInt = objectiveDeck.getCards().stream()
                .mapToInt(ObjectiveCard::getId)
                .toArray();

        serverAPI.broadcastNotifyChanges( new initializationMessage( goldDeckInt, resourceDeckInt, starterDeckInt,
                                                                     objectiveDeckInt, (String[]) players.toArray(), gameId));

    }



    /**
     * the player is notified with the starterCard that he has been given
     * @param starterCard
     * @param player
     */
    public void notifyChanges(PlayableCard starterCard, String player){
        serverAPI.notifyChanges( new StarterCardMessage(starterCard.getId()), player );
    }


    /**
     * notification about the two secretObjectives for each player
     * @param secretObjective1
     * @param secretObjective2
     */
    public void notifyChanges(ObjectiveCard secretObjective1, ObjectiveCard secretObjective2, String player){
        serverAPI.notifyChanges( new secretObjectivesMessage(secretObjective1.getId(), secretObjective2.getId()), player);
    }


    /**
     * after each player chooses his secretObjective, a notification is sent to all clients
     * @param secretObjective
     * @param player
     */
    public void notifyChanges(ObjectiveCard secretObjective, String player){
        serverAPI.broadcastNotifyChanges( new ConfirmSecretObjectiveMessage(secretObjective.getId(), player));
    }

    ////////////////////////////// ROUND NOTIFICATIONS ///////////////////////////////////////////////////////////////

    /**
     * At the beginning of the turn of turnPlayer, the model notifies the player about which cards
     * of his hand can be placed(due to placementConstraint) and also where the cards can be placed
     * @param availablePlaces
     * @param player
     */
    public void notifyChanges(String player, List<Coordinates> availablePlaces, Boolean[] canBePlaced){

    }

    /**
     * after position, coordinates and faceSide for placing the card are chosen, the player is notified with his updated disposition
     * , points and available resources
     * @param disposition
     * @param player
     */
    public void notifyChanges(String player, HashMap<Coordinates, PlayableCard> disposition, int points,
                              HashMap<Artifact, Integer> availableArtifacts, HashMap<Element, Integer> availableElements) {

    }

    /**
     * after the player decided where to draw the next card from, the involved card source gets updated based on cardDrawnFrom
     * @param hand
     * @param player
     */
    public void notifyChanges(String player, List<PlayableCard> hand) {

    }
}
