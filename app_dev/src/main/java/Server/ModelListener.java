package Server;


import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Pawn;
import model.placementArea.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelListener {

    //////////////////////// GAME SETUP NOTIFICATIONS ///////////////////////////////////////////////////
    /**
     * this notification confirms the nickname and tells the player which pawn color he has been given
     */
    public void notifyChanges(String turnPlayer,String nickname, Pawn pawnColor) {

    }

    /**
     * notification with the data about the hand of player
     */
    public void notifyChanges(ArrayList<PlayableCard> hand, String player) {

    }

    /**
     * notification with data about common table when initialized
     * @param goldDeck
     * @param resourceDeck
     * @param commonObjectives
     * @param openGold
     * @param openResource
     */
    public void notifyChanges(ArrayList<PlayableCard> goldDeck, ArrayList<PlayableCard> resourceDeck,
                              ArrayList<ObjectiveCard> commonObjectives, ArrayList<PlayableCard> openGold,
                              ArrayList<PlayableCard> openResource){

    }


    /**
     * after each player chooses how the place the starter card, a notification is sent to all clients
     * @param starter
     * @param player
     */
    public void notifyChanges(PlayableCard starter, String player){}

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
