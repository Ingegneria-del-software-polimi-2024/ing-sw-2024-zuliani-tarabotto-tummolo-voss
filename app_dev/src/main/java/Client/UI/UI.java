package Client.UI;

import Chat.MessagesFromClient.ChatMessage;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The interface Ui.
 */
public interface UI extends Runnable{
    /**
     * Display initialization.
     */
//open cards and other players' pawn and nicknames
    void displayInitialization();

    /**
     * Display starter card selection.
     */
//starter card for the player
    void displayStarterCardSelection();

    /**
     * Display objective selection.
     */
//the initial hand is shown and the commonObjectives, also the two secretObjectives are shown and
    //the player has to make a choice
    void displayObjectiveSelection();

    /**
     * Display placing card.
     */
//the disposition of the player and his hand is shown, it's possible to visualize the other players' dispositions
    void displayPlacingCard();

    /**
     * Display card drawing.
     */
//all the cards that can be drawn
    void displayCardDrawing();

    /**
     * Display end game.
     */
//whatever -> probably the end
    void displayEndGame();

    /**
     * Print disposition.
     *
     * @param disposition the disposition
     */
    void printDisposition(HashMap<Coordinates, PlayableCard> disposition);

    /**
     * Choose connection.
     */
    void chooseConnection();

    /**
     * Ask nickname.
     */
    void askNickname();

    /**
     * Display available games.
     *
     * @param listOfGames the list of games
     */
    void displayAvailableGames(ArrayList<String> listOfGames);

    /**
     * Joined game.
     *
     * @param gameID the game id
     */
    void joinedGame(String gameID);

    /**
     * First welcome.
     */
    void firstWelcome();

    /**
     * Nick name already in use.
     */
    void nickNameAlreadyInUse();

    /**
     * Cant place a card.
     *
     * @param card  the card
     * @param coord the coord
     */
    void cantPlaceACard(PlayableCard card, Coordinates coord);

    /**
     * Cant draw card.
     *
     * @param source the source
     */
    void cantDrawCard(int source);

    /**
     * Cant create room.
     */
    void cantCreateRoom();

    /**
     * Cant join room.
     */
    void cantJoinRoom();

    /**
     * Return to lobby.
     */
    void returnToLobby();

    /**
     * Print starter card.
     */
    void printStarterCard();

    /**
     * Print secret objective.
     */
    void printSecretObjective();

    /**
     * Display reconnection.
     */
    void displayReconnection();

    /**
     * Return to choose game.
     */
    void returnToChooseGame();

    /**
     * displays the text message that has arrived
     *
     * @param message the message
     */
    void displayNewTextMessage(ChatMessage message);

    /**
     * Update resources in ui.
     */
    void updateResourcesInUI();

    /**
     * Return to start.
     */
    void returnToStart();
}
