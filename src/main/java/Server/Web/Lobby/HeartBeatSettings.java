package Server.Web.Lobby;

/**
 * The type Heart beat settings.
 * This class contains the settings for the heartbeat mechanism.
 */
public final class HeartBeatSettings {
    /**
     * The constant timeoutCheck.
     */
    public static final long timeoutCheck = 4000;
    /**
     * The constant indicating the frequency at which the clients are checked for disconnections.
     */
    public static final long checkFreq = 1000;
    /**
     * The constant timeout after which a client not sending the heartbeat is considered disconnected.
     */
    public static final long timeout = 5000;
    /**
     * The constant indicating the iterations of waiting for the reconnection of players done before closing the game.
     */
    public static final int iterationsNumber = 10;
    /**
     * The constant indicating the time waited for each iteration before closing the game.
     * This value must be multiplied by the iterationsNumber to get the total time waited before closing the game.
     */
    public static final long timerB4ClosingGame = 6000; //TODO set 6000

}
