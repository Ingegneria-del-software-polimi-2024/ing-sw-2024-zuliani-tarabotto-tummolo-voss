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
        serverAPI.broadcastNotifyChanges( new StateMessage( state ));
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

        ArrayList<PlayableCard> goldDeckCopy = new ArrayList<>();
        List<PlayableCard> resourceDeckCopy = new ArrayList<>();
        goldDeckCopy.addAll(goldDeck.getCards());
        resourceDeckCopy.addAll(resourceDeck.getCards());

        serverAPI.broadcastNotifyChanges( new InitializationMessage( goldDeckCopy, resourceDeckCopy, openGold, openResource,
                (String[]) players.toArray(), gameId,
                commonObjective1, commonObjective2));

    }



    /**
     * the player is notified with the starterCard that he has been given and the PAWNCOLOR
     * @param starterCard
     * @param player
     */
    public void notifyChanges(PlayableCard starterCard, String player, Pawn pawnColor){
        serverAPI.notifyChanges( new StarterCardMessage(starterCard, pawnColor.toString()), player );
    }

    /**
     * the player is notified with his hand of cards
     * @param hand
     * @param player
     */
    public void notifyChanges(List<PlayableCard> hand , String player){
        serverAPI.notifyChanges( new UpdateHandMessage(hand), player);
    }

    /**
     * notification about the two secretObjectives for each player
     * @param secretObjective1
     * @param secretObjective2
     */
    public void notifyChanges(ObjectiveCard secretObjective1, ObjectiveCard secretObjective2, String player){
        serverAPI.notifyChanges( new SecretObjectivesMessage(secretObjective1, secretObjective2), player);
    }


    /**
     * after each player chooses his secretObjective, a notification is sent
     * @param secretObjective
     * @param player
     */
    public void notifyChanges(ObjectiveCard secretObjective, String player){

        serverAPI.notifyChanges( new ConfirmSecretObjectiveMessage(secretObjective), player);
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
        serverAPI.notifyChanges( new PlaceableCardsMessage(availablePlaces, canBePlaced), player);
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
        serverAPI.broadcastNotifyChanges(new UpdateDispositionMessage(player, disposition,
                points, availableArtifacts, availableElements));

    }

    /**
     * after the player decided where to draw the next card from, the involved card source gets updated based on cardSource
     * @param deck
     * @param cardSource
     */
    public void notifyChanges(List<PlayableCard> deck, int cardSource) {
        serverAPI.broadcastNotifyChanges( new DrawCardMessage(deck, cardSource));
    }

    /**
     * at the end of the game each player is notified with the final points of every player,
     * the view is responsible of displaying the winner
     * @param finalPoints
     */
    public void notifyChanges(HashMap<String, Integer> finalPoints){
        serverAPI.broadcastNotifyChanges(new EndGameMessage(finalPoints));
    }
}
