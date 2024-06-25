package SharedWebInterfaces.SharedInterfaces;


import Chat.MessagesFromClient.ChatMessage;
import model.GameState.TurnState;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.ObjectiveDeck;
import model.enums.Artifact;
import model.enums.Element;
import model.objective.Objective;
import model.placementArea.Coordinates;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The interface View api interface.
 */
public interface ViewAPI_Interface extends GeneralAPI_Interface {

    /**
     * Starts heartbeat thread.
     */
    void startHeartbeatThread();

    /**
     * Sets the player as ready to play.
     */
    void readyToPlay();

    /**
     * Sets a state of the game.
     *
     * @param state the state
     */
    void setState(TurnState state);

    /**
     * Sets gold deck.
     *
     * @param deck the gold deck
     */
    void setGoldDeck(List<PlayableCard> deck);

    /**
     * Sets resource deck.
     *
     * @param deck the resource deck
     */
    void setResourceDeck(List<PlayableCard>deck);

    /**
     * Sets players when starting a game.
     *
     * @param players the players' list
     */
    void setPlayers(List<String> players);

    /**
     * Sets game id when joining a room.
     *
     * @param gameId the game id
     */
    void setGameId(String gameId);

    /**
     * Sets open gold cards.
     *
     * @param openGold the open gold cards
     */
    void setOpenGold(List<PlayableCard> openGold);

    /**
     * Sets open resource cards.
     *
     * @param openResource the open resource cards
     */
    void setOpenResource(List<PlayableCard> openResource);

    /**
     * Sets starter card.
     *
     * @param starterCard the starter card
     */
    void setStarterCard(PlayableCard starterCard);

    /**
     * Sets hand.
     *
     * @param hand the hand
     */
    void setHand(List<PlayableCard> hand);

    /**
     * Sets secret objectives.
     *
     * @param obj1 the obj 1
     * @param obj2 the obj 2
     */
    void setSecretObjectives(ObjectiveCard obj1, ObjectiveCard obj2);

    /**
     * Sets secret objective chosen by the player.
     *
     * @param secretObjective the secret objective
     */
// se player == client allora il secret objective del client è settato, sennò viene aggiornato quello del client corrispondente a player
    void setSecretObjective(ObjectiveCard secretObjective);

    /**
     * Sets common objectives.
     *
     * @param commonObjective1 the common objective 1
     * @param commonObjective2 the common objective 2
     */
    void setCommonObjectives(ObjectiveCard commonObjective1, ObjectiveCard commonObjective2);

    /**
     * Sets disposition belonging to a player.
     *
     * @param player      the player
     * @param disposition the disposition
     */
//card(1,2,3)
    void setDisposition(String player, HashMap<Coordinates, PlayableCard> disposition);

    /**
     * Sets points of a player.
     *
     * @param player the player
     * @param points the points
     */
    void setPoints(String player, int points);

    /**
     * Update artifacts of a player.
     *
     * @param player    the player
     * @param artifacts the artifacts
     */
    void updateArtifacts(String player, HashMap<Artifact, Integer> artifacts);

    /**
     * Update elements of a player.
     *
     * @param player   the player
     * @param elements the elements
     */
    void updateElements(String player, HashMap<Element, Integer> elements);

    /**
     * Update card source after a players draws from it.
     *
     * @param deck       the deck
     * @param cardSource the card source identifier
     */
    void updateCardSource(List<PlayableCard> deck, int cardSource);

    /**
     * Update open card sources.
     *
     * @param deck the deck
     * @param card the card
     */
    void updateOpenCards(List<PlayableCard> deck, int card);

    /**
     * Sets final points for the players and the winners.
     *
     * @param finalPoints the final points represented in a hashmap where the entries are the names of the players and their relative points
     * @param winners     the winners in a list
     */
    void setFinalPoints( HashMap<String, Integer> finalPoints, ArrayList<String> winners);

    /**
     * Sets pawn color.
     *
     * @param player    the player
     * @param pawnColor the pawn color
     */
    void setPawnColor(String player, String pawnColor);

    /**
     * Sets the available places to put a new card in your placing area.
     *
     * @param availablePlaces the available places
     */
    void setAvailablePlaces(List<Coordinates> availablePlaces);

    /**
     * Sets can be placed, a boolean value for each card in you hand.
     * It is true when that card can be placed "front", else it is false
     *
     * @param canBePlaced the can be placed value
     */
    void setCanBePlaced(boolean[] canBePlaced);

    /**
     * Gets my turn parameter.
     *
     * @return true when it's player's turn else false
     */
    boolean getMyTurn();

    /**
     * Confirm secret objective.
     *
     * @param card the secret objective to be confirmed
     */
    public void confirmSecretObjective(ObjectiveCard card);

    /**
     * Sets turn player.
     *
     * @param turnPlayer the turn player nickname
     */
    public void setTurnPlayer(String turnPlayer);

    /**
     * Asks player to choose a nickname.
     */
    void askNickname();

    /**
     * Sets available games.
     *
     * @param listOfGames the list of games
     */
    void setAvailableGames(ArrayList<String> listOfGames);

    /**
     * Display available games, basing on the data stored in the view model.
     */
    void displayAvailableGames();

    /**
     * Display available games passed as parameters.
     *
     * @param availableGames the available games
     */
    void displayAvailableGames(ArrayList<String> availableGames);

    /**
     * Notifies the player that the nickname is already in use.
     */
    void nickNameAlreadyInUse();

    /**
     * Notifies the player that they can't place a card.
     *
     * @param card  the card
     * @param coord the coord
     */
    void cantPlaceACard(PlayableCard card, Coordinates coord);

    /**
     * Notifies the player that they can't draw card.
     *
     * @param source the source
     */
    void cantDrawCard(int source);

    /**
     * Notifies the player that they can't join room.
     */
    void cantJoinRoom();

    /**
     * Notifies the player that can't create room.
     */
    void cantCreateRoom();

    /**
     * Brings the player back to the lobby
     */
    void returnToLobby();

    /**
     * Notifies the player that the nickname is correctly received by the server, saves it in the viewModel.
     *
     * @param name the nickname
     */
    void ackNickName(String name);

    /**
     * Displays the starter card.
     */
    void displayStarterCard();

    /**
     * Displays the secret objective.
     */
    void displaySecretObjective();

    /**
     * Displays the interface for communicating a completed reconnection.
     */
    void displayReconnection();

    /**
     * Sets player id.
     *
     * @param playerID the player id
     */
    void setPlayerID(String playerID); //used for recovery from disconnections

    /**
     * Returns to lobby when choosing a game.
     */
    void returnToChooseGame();

    /**
     * Delivers a message from the chat
     *
     * @param message the message to be delivered
     */
    void deliverTextMessage(ChatMessage message);

    /**
     * Substitutes the player's actual chat history
     *
     * @param history the new chat history containing ChatMessage objects
     */
    void resetChatHistory(ArrayList<ChatMessage> history);

    /**
     * Notifies the view that the game has started, this way commands as --disp can be called
     */
    void setGameAsStarted();

    /**
     * When elements and resources are updated, we call this function to update the corresponding values on the ui
     */
    void updateResourcesInUI();

    /**
     * Returns to the choosing of the connection technology.
     */
    void returnToStart();
}
