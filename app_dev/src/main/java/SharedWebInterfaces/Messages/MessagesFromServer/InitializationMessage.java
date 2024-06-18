package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;

import java.util.ArrayList;
import java.util.List;

public class InitializationMessage implements MessageFromServer{

    private List<PlayableCard> goldDeck;
    private List<PlayableCard> resourceDeck;
    private List<String> players;
    private String gameId;
    private ObjectiveCard commonObjective1;
    private ObjectiveCard commonObjective2;
    private List<PlayableCard> openGold;
    private List<PlayableCard> openResource;

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
//        view.setGameId(gameId); //TODO DELETE
        view.setCommonObjectives(commonObjective1, commonObjective2);
        view.setOpenGold(openGold);
        view.setOpenResource(openResource);
    }

}
