package main.java.model.GameState;
import main.java.model.cards.*;
import main.java.model.deckFactory.*;

public class GameState {

    //Attributes

    Player[] players = new Player();

    private String id;

    private ResourcesDeck resourceDeck;

    private Player turnPlayer;

    private ResourcesDeck resourcesDeck;

    private GoldenDeck goldDeck;

    private ObjectiveDeck deckObjectives;

    private StarterDeck startingDeck;

    ObjectiveCards[] commonObjectives = new ObjectiveCards[2];

    GoldCard[] openGold = new GoldCard[2];

    ResourceCard[] openResources = new ResourceCard[2];

    private boolean isLastTurn;



    //Methods

    public Player getPlayer(){

    }

    public String getId(){

    }

    public Player getTurnPlayer(){

    }

    public void calculateCommonObj(){

    }

    public boolean isLastTurn(){

    }

    public Couple<GoldCard> getOpenGold(){

    }

    public Couple<ResourceCard> getOpenResource(){

    }

    public void countEndgamePoints(){

    }
}
