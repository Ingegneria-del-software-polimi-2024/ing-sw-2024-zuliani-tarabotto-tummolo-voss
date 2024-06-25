package SharedWebInterfaces.SharedInterfaces;

/**
 * The interface Server controller interface.
 * This interface is used to define the methods that the client can perform on the serverController through the messages sent.
 */
public interface ServerControllerInterface extends Traslator {
    /**
     * Play card.
     *
     * @param id       the id
     * @param x        the x
     * @param y        the y
     * @param faceSide the face side
     */
    public void playCard(int id, int x, int y, Boolean faceSide);

    /**
     * Initializes a new game state.
     */
    public void initializeGameState();

    /**
     * Plays the starter card.
     *
     * @param face   the face
     * @param player the player
     */
    public void playStarterCard(boolean face, String player);

    /**
     * Chooses a secret objective.
     *
     * @param cardId the card id
     * @param player the player
     */
    public void chooseSecretObjective(String cardId, String player);

    /**
     * Draws a card.
     *
     * @param cardSource the deck from which the card is drawn
     */
    public void drawCard(int cardSource);

    /**
     * Sets player as ready.
     */
    public void setPlayerReady();

    /**
     * Ends the game.
     */
    public void endGame();
}
