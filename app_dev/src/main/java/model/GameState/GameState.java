package main.java.model.GameState;
import main.java.model.*;

public class GameState {

    //Attributes

    Player[] players = new Player();

    private String id;

    private ResourcesDeck resourceDeck;

    private Player turnPlayer;

    private ResourcesDeck resourcesDeck;

    private GoldDeck goldDeck;

    private ObjectiveDeck deckObjectives;

    private StartingDeck startingDeck;

    ObjectiveCards[2] commonObjectives = new ObjectiveCards();

    GoldCards[2] openGold = new GoldCards();

    ResourceCards[2] openResources = new ResourceCards();

    private bool isLastTurn;



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
