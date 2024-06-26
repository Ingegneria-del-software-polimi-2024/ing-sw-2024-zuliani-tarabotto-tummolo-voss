package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Initialization message.
 * This message is sent by the server to the clients when the game is started.
 * It contains the initial state of the game.
 */
public class InitializationMessage implements MessageFromServer{
    /**
     * The Gold deck.
     */
    private List<PlayableCard> goldDeck;
    /**
     * The Resource deck.
     */
    private List<PlayableCard> resourceDeck;
    /**
     * The list of Players.
     */
    private List<String> players;
    /**
     * The Game name.
     */
    private String gameId;
    /**
     * The Common objective 1.
     */
    private ObjectiveCard commonObjective1;
    /**
     * The Common objective 2.
     */
    private ObjectiveCard commonObjective2;
    /**
     * The Open gold cards.
     */
    private List<PlayableCard> openGold;
    /**
     * The Open resource cards.
     */
    private List<PlayableCard> openResource;

    /**
     * Instantiates a new Initialization message.
     *
     * @param goldDeck         the gold deck
     * @param resourceDeck     the resource deck
     * @param openGold         the open gold
     * @param openResource     the open resource
     * @param players          the players
     * @param gameId           the game id
     * @param commonObjective1 the common objective 1
     * @param commonObjective2 the common objective 2
     */
    public InitializationMessage(List<PlayableCard> goldDeck, List<PlayableCard> resourceDeck, List<PlayableCard> openGold, List<PlayableCard> openResource,
                                 List<String> players, String gameId, ObjectiveCard commonObjective1, ObjectiveCard commonObjective2) {

        this.goldDeck = goldDeck;
        this.resourceDeck = resourceDeck;
        this.players = players;
        this.gameId = gameId;
        this.commonObjective1 = commonObjective1;
        this.commonObjective2 = commonObjective2;
        this.openGold = openGold;
        this.openResource = openResource;

    }

    /**
     * Set the initial state of the game for the receiver
     * @param view the view interface of the receiver
     */
    @Override
    public void execute(ViewAPI_Interface view) {
        view.setGameAsStarted();
        view.setGoldDeck(goldDeck);
        view.setResourceDeck(resourceDeck);
        view.setPlayers(players);
        view.setCommonObjectives(commonObjective1, commonObjective2);
        view.setOpenGold(openGold);
        view.setOpenResource(openResource);
    }

}
