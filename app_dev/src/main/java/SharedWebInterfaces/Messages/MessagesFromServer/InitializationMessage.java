package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;
import SharedWebInterfaces.ViewAPI_Interface;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;

import java.util.ArrayList;
import java.util.List;

public class InitializationMessage implements MessageFromServer{

    private List<PlayableCard> goldDeck;
    private List<PlayableCard> resourceDeck;
    private String[] players;
    private String gameId;
    private ObjectiveCard commonObjective1;
    private ObjectiveCard commonObjective2;
    private List<PlayableCard> openGold;
    private List<PlayableCard> openResouce;

    public InitializationMessage(List<PlayableCard> goldDeck, List<PlayableCard> resourceDeck, List<PlayableCard> openGold, List<PlayableCard> openResouce,
                                 String[] players, String gameId, ObjectiveCard commonObjective1, ObjectiveCard commonObjective2) {

        this.goldDeck = goldDeck;
        this.resourceDeck = resourceDeck;
        this.players = players;
        this.gameId = gameId;
        this.commonObjective1 = commonObjective1;
        this.commonObjective2 = commonObjective2;
        this.openGold = openGold;
        this.openResouce = openResouce;

    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.setGoldDeck(goldDeck);
        view.setResourceDeck(resourceDeck);
        view.setPlayers(players);
        view.setGameId(gameId);
        view.setCommonObjectives(commonObjective1, commonObjective2);
        view.setOpenGold(openGold);
        view.setOpenResource(openResouce);
    }

}
