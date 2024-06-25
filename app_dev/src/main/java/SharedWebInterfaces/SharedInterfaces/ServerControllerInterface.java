package SharedWebInterfaces.SharedInterfaces;

/**
 * The interface Server controller interface.
 */
public interface ServerControllerInterface extends ControllerInterface {
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
     * Initialize game state.
     */
    public void initializeGameState();

    /**
     * Play starter card.
     *
     * @param face   the face
     * @param player the player
     */
    public void playStarterCard(boolean face, String player);

    /**
     * Choose secret objective.
     *
     * @param cardId the card id
     * @param player the player
     */
    public void chooseSecretObjective(String cardId, String player);

    /**
     * Draw card.
     *
     * @param cardSource the card source
     */
    public void drawCard(int cardSource);

    /**
     * Sets player ready.
     */
    public void setPlayerReady();

    /**
     * End game.
     */
    public void endGame();
}
