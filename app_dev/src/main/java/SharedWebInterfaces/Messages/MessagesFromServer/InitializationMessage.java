package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.Messages.GeneralAPI_Interface;
import SharedWebInterfaces.ViewAPI_Interface;

public class InitializationMessage implements MessageFromServer{

    private int[] goldDeck;
    private int[] resourceDeck;
    private int[] starterDeck;
    private int[] objectiveDeck;
    private String[] players;
    private String gameId;

    public InitializationMessage(int[] goldDeck, int[] resourceDeck, int[] starterDeck, int[] objectiveDeck,
                                 String[] players, String gameId) {

        this.goldDeck = goldDeck;
        this.resourceDeck = resourceDeck;
        this.starterDeck = starterDeck;
        this.objectiveDeck = objectiveDeck;
        this.players = players;
        this.gameId = gameId;
    }

    @Override
    public void execute(ViewAPI_Interface view) {
        view.setGoldDeck(goldDeck);
        view.setResourceDeck(resourceDeck);
        view.setStarterDeck(starterDeck);
        view.setObjectiveDeck(objectiveDeck);
        view.setPlayers(players);
        view.setGameId(gameId);
    }
}
