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
     * Start heartbeat thread.
     */
    void startHeartbeatThread();

    /**
     * Ready to play.
     */
    void readyToPlay();

    /**
     * Sets state.
     *
     * @param state the state
     */
    void setState(TurnState state);

    /**
     * Sets gold deck.
     *
     * @param deck the deck
     */
    void setGoldDeck(List<PlayableCard> deck);

    /**
     * Sets resource deck.
     *
     * @param deck the deck
     */
    void setResourceDeck(List<PlayableCard>deck);

    /**
     * Sets players.
     *
     * @param players the players
     */
    void setPlayers(List<String> players);

    /**
     * Sets game id.
     *
     * @param gameId the game id
     */
    void setGameId(String gameId);

    /**
     * Sets open gold.
     *
     * @param openGold the open gold
     */
    void setOpenGold(List<PlayableCard> openGold);

    /**
     * Sets open resource.
     *
     * @param openResource the open resource
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
     * Sets secret objective.
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
     * Sets disposition.
     *
     * @param player      the player
     * @param disposition the disposition
     */
//card(1,2,3)
    void setDisposition(String player, HashMap<Coordinates, PlayableCard> disposition);

    /**
     * Sets points.
     *
     * @param player the player
     * @param points the points
     */
    void setPoints(String player, int points);

    /**
     * Update artifacts.
     *
     * @param player    the player
     * @param artifacts the artifacts
     */
    void updateArtifacts(String player, HashMap<Artifact, Integer> artifacts);

    /**
     * Update elements.
     *
     * @param player   the player
     * @param elements the elements
     */
    void updateElements(String player, HashMap<Element, Integer> elements);

    /**
     * Update card source.
     *
     * @param deck       the deck
     * @param cardSource the card source
     */
    void updateCardSource(List<PlayableCard> deck, int cardSource);

    /**
     * Update open cards.
     *
     * @param decK the dec k
     * @param card the card
     */
    void updateOpenCards(List<PlayableCard> decK, int card);

    /**
     * Sets final points.
     *
     * @param finalPoints the final points
     * @param winners     the winners
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
     * Sets available places.
     *
     * @param availablePlaces the available places
     */
    void setAvailablePlaces(List<Coordinates> availablePlaces);

    /**
     * Sets can be placed.
     *
     * @param canBePlaced the can be placed
     */
    void setCanBePlaced(boolean[] canBePlaced);

    /**
     * Gets my turn.
     *
     * @return the my turn
     */
    boolean getMyTurn();

    /**
     * Confirm secret objective.
     *
     * @param card the card
     */
    public void confirmSecretObjective(ObjectiveCard card);

    /**
     * Sets turn player.
     *
     * @param turnPlayer the turn player
     */
    public void setTurnPlayer(String turnPlayer);

    /**
     * Ask nickname.
     */
    void askNickname();

    /**
     * Sets available games.
     *
     * @param listOfGames the list of games
     */
    void setAvailableGames(ArrayList<String> listOfGames);

    /**
     * Display available games.
     */
    void displayAvailableGames();

    /**
     * Display available games.
     *
     * @param availableGames the available games
     */
    void displayAvailableGames(ArrayList<String> availableGames);

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
     * Cant join room.
     */
    void cantJoinRoom();

    /**
     * Cant create room.
     */
    void cantCreateRoom();

    /**
     * brings the player back to the lobby
     */
    void returnToLobby();

    /**
     * Ack nick name.
     *
     * @param name the name
     */
    void ackNickName(String name);

    /**
     * Display starter card.
     */
    void displayStarterCard();

    /**
     * Display secret objective.
     */
    void displaySecretObjective();

    /**
     * Display reconnection.
     */
    void displayReconnection();

    /**
     * Sets player id.
     *
     * @param playerID the player id
     */
    void setPlayerID(String playerID); //used for recovery from disconnections

    /**
     * Return to choose game.
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
     * when elements and resources are updated, we call this function to update the corresponding values on the ui
     */
    void updateResourcesInUI();

    /**
     * Return to start.
     */
    void returnToStart();
}
