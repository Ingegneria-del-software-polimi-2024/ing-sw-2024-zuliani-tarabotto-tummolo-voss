package Client.UI;

import Chat.MessagesFromClient.ChatMessage;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The interface Ui.
 * This interface allows to communicate commands to the operative visualizer, it is implemented graphically by GUI and
 * in a text format by TUI
 */
public interface UI extends Runnable{
    /**
     * Displays the initialization of the game: open cards and other players' pawn and nicknames.
     */
    void displayInitialization();

    /**
     * Displays the starter card selection.
     */
    void displayStarterCardSelection();

    /**
     * Displays objective selection.
     * The initial hand is shown and the commonObjectives, also the two secretObjectives are shown and the player has to make a choice
     */
    void displayObjectiveSelection();

    /**
     * Displays the placement of a card.
     * The disposition of the player and his hand is shown, it's possible to visualize the other players' dispositions
     */
    void displayPlacingCard();

    /**
     * Display the card drawing.
     * All the cards that can be drawn are visualized
     */

    void displayCardDrawing();

    /**
     * Displays the end of the game and the final points.
     */
    void displayEndGame();

    /**
     * Prints the disposition.
     *
     * @param disposition the disposition to be printed
     */
    void printDisposition(HashMap<Coordinates, PlayableCard> disposition);

    /**
     * Chooses the connection between RMI or Socket.
     */
    void chooseConnection();

    /**
     * Asks for the nickname of the player.
     */
    void askNickname();

    /**
     * Displays the available games.
     *
     * @param listOfGames the list of games
     */
    void displayAvailableGames(ArrayList<String> listOfGames);

    /**
     * Confirms that the player has joined game.
     *
     * @param gameID the game id
     */
    void joinedGame(String gameID);

    /**
     * Welcome to the game.
     */
    void firstWelcome();

    /**
     * Notifies the player that the nickname is already in use and asks for a new one.
     */
    void nickNameAlreadyInUse();

    /**
     * Notifies the player that the card can't be placed and asks for a new selection.
     *
     * @param card  the card to be  placed
     * @param coord the coord the coordinates to place the card in
     */
    void cantPlaceACard(PlayableCard card, Coordinates coord);

    /**
     * Notifies the player that the card can't be drawn.
     *
     * @param source the source deck
     */
    void cantDrawCard(int source);

    /**
     * Notifies the player that the room can't be created.
     */
    void cantCreateRoom();

    /**
     * Notifies the player that the room can't be joined.
     */
    void cantJoinRoom();

    /**
     * Notifies the player of their return to the lobby.
     */
    void returnToLobby();

    /**
     * Prints the starter card.
     */
    void printStarterCard();

    /**
     * Prints the secret objective.
     */
    void printSecretObjective();

    /**
     * Displays the correct happening of the reconnection.
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
     * Updates the resources displaued in ui.
     */
    void updateResourcesInUI();

    /**
     * Returns  to the selection of the server.
     */
    void returnToStart();
}
