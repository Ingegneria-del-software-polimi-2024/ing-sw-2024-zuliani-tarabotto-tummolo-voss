package Server;


import model.GameState.TurnState;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.ObjectiveDeck;
import model.deckFactory.PlayableDeck;
import model.enums.Pawn;
import model.placementArea.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelListener {

    //////////////////////// GAME SETUP NOTIFICATIONS ///////////////////////////////////////////////////

    /**
     * notification about current state of GameState
     * @param state
     */
    public void notifyChanges(TurnState state){

    }

    /**
     * notification with data about the order of the cards in decks
     * @param goldDeck
     * @param resourceDeck
     * @param starterDeck
     * @param objectiveDeck
     * @param players
     * @param GameId
     * @param initialPlayer
     */
    //TODO: add notification about each player's pawn color
    public void notifyChanges(PlayableDeck goldDeck, PlayableDeck  resourceDeck,
                              PlayableDeck  starterDeck, ObjectiveDeck objectiveDeck,
                              ArrayList<String> players, String GameId, String initialPlayer){

    }


    /**
     * the player is notified with the starterCard that he has been given
     * @param starterCard
     * @param player
     */
    public void notifyChanges(PlayableCard starterCard, String player){

    }

    /**
     * notification with the data about the hand of player
     */
    public void notifyChanges(ArrayList<PlayableCard> hand, String player) {

    }
    /**
     * notification about the two commonObjectives
     * @param commonObjectives
     */
    public void notifyChanges(ArrayList<ObjectiveCard> commonObjectives){

    }


    /**
     * after each player chooses his secretObjective, a notification is sent to all clients
     * @param secretObjective
     * @param player
     */
    public void notifyChanges(ObjectiveCard secretObjective, String player){

    }

    ////////////////////////////// ROUND NOTIFICATIONS ///////////////////////////////////////////////////////////////

    /**
     * after ui selects a card from hand, the model's selectedHandCard attribute changes(also the face attribute in card),
     * the model notifies the view with the available places where the selected card can be placed
     * @param availablePlaces
     * @param player
     */
    public void notifyChanges(List<Coordinates> availablePlaces, String player){

    }

    /**
     * after a position to place the card is also selected, the model updates the view's disposition
     * @param disposition
     * @param player
     */
    public void notifyChanges(HashMap<Coordinates, PlayableCard> disposition, String player) {

    }

    /**
     * after the player decided where to draw the next card from, the involved card source gets updated based on cardDrawnFrom
     * @param hand
     * @param player
     * @param nextTurnPlayer
     * @param playerPoints
     * @param cardDrawnFrom
     */
    public void notifyChanges(ArrayList<PlayableCard> hand, String player, String nextTurnPlayer, int playerPoints, String cardDrawnFrom) {

    }
}
