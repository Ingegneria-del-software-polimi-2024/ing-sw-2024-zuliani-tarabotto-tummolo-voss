package model.GameState;

import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.ResourcesDeck;
import model.player.Player;
import model.deckFactory.*;
import model.cards.*;

import java.util.List;

public class GameState {


    private List<Player> players;
    private String id;

    private ResourcesDeck resourceDeck;

    private Player turnPlayer;

    private ResourcesDeck resourcesDeck;

    private GoldenDeck goldDeck;

    //private ObjectiveDeck deckObjectives;

    private StarterDeck startingDeck;

    private List<ObjectiveCard> commonObjectives; //2 elements in the list

    //do we actually want Lists of PlayableCard or we prefer List<GoldCard> and List<ResourceCard>???
    private List<PlayableCard> openGold; //2 elements in the list

    private List<PlayableCard> openResources; //2 elements in the list

    private boolean isLastTurn = false;



    //Methods

    public Player getPlayer(int indx){

        return players.get(indx);
    }

    public String getId(){

    }

    public Player getTurnPlayer(){

        return turnPlayer;
    }

    //update each player's final points by calling the methods in Player class
    public void calculateFinalPoints(){

        int i, j;

        for(i=0; i< players.size(); i++){

            players.get(i).calculateSecretObj();

            for(j=0; j<2; j++){

                players.get(i).calculateSingleCommonObj(commonObjectives.get(j));
            }
        }
    }

    public boolean getLastTurn(){

        return isLastTurn;
    }


    public void countEndgamePoints(){

    }

    public void nextPlayer() {
        int currentPlayer = players.indexOf(turnPlayer);
        turnPlayer = players.get(currentPlayer+1);
        return;
    }

    public void drawCard(Player player, Deck deck){
        //takes away the first card of the deck and calls the following method
        player.drawCard(card);
    }
    public void drawCard(Player player, List<PlayableCard> openCards, int index){
        //takes away the card from the ones face up
        player.drawCard(card);
        //replaces the card taken with the first of the deck
    }

    public void setLastTurnTrue() {isLastTurn = true;}

    //are all of these methods necessary? idk but rn i'll keep them
    public GoldenDeck getGoldDeck() {
        return goldDeck;
    }

    public ResourcesDeck getResourceDeck() {
        return resourceDeck;
    }

    public List<PlayableCard> getOpenResources() {
        return openResources;
    }

    public List<PlayableCard> getOpenGold() {
        return openGold;
    }


}
