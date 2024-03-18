package model.GameState;

import model.deckFactory.ResourcesDeck;

public class GameState {

    //Attributes

    Player[] players = new Player[];

    private String id;

    private ResourcesDeck resourceDeck;

    private Player turnPlayer;

    private ResourcesDeck resourcesDeck;

    private GoldDeck goldDeck;

    private ObjectiveDeck deckObjectives;

    private StartingDeck startingDeck;

    ObjectiveCards[] commonObjectives = new ObjectiveCards[2];

    GoldCards[] openGold = new GoldCards[2];

    ResourceCards[] openResources = new ResourceCards[2];

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
