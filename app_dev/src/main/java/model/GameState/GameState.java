package model.GameState;

import model.cards.ObjectiveCard;
import model.cards.PlayableCards.GoldCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.ResourcesDeck;
import model.placementArea.Coordinates;
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
    private PlayableCard selectedHandCard;
    private Coordinates selectedCoordinates;


    //Methods

    public Player getPlayer(int indx){
        return players.get(indx);
    }

    public String getId(){
        return id;
    }
    public Player getTurnPlayer(){
        return turnPlayer;
    }
    public void calculateCommonObj(){

    }
    public void setLastTurnTrue(){
        //checks if a player has reached 20 points
        for(int i=0; i<4; i++){
            if (players.get(i).getPoints() >= 20) {
                isLastTurn = true;
                break;
            }
        }
        //checks if both decks are empty
        if(goldDeck.getSize() == 0 && resourceDeck.getSize() == 0){
            isLastTurn = true;
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

    public void drawCardGoldDeck(){
        //takes away the first card of the deck and calls the following method
        if(goldDeck.getSize() > 0) turnPlayer.drawCard((PlayableCard) goldDeck.extract());
        //method to check if both decks are empty
        setLastTurnTrue();
    }

    public void drawCardResourcesDeck(){
        //takes away the first card of the deck and calls the following method
        if(resourceDeck.getSize() > 0) turnPlayer.drawCard((PlayableCard) resourceDeck.extract());
        setLastTurnTrue();
    }

    public void drawCardOpenGold(int index){
        //takes away the card at specified position from openGold
        turnPlayer.drawCard(openGold.get(index));
        //replaces the card taken with the first of the goldDeck
        openGold.remove(index);
        if(goldDeck.getSize() > 0) openGold.set(index, (PlayableCard)goldDeck.extract());
        setLastTurnTrue();
    }

    public void drawCardOpenResources(int index) {
        //same as drawCardOpenGold
        turnPlayer.drawCard(openResources.get(index));
        openResources.remove(index);
        if(resourceDeck.getSize() > 0) openResources.set(index, (PlayableCard) resourceDeck.extract());
        setLastTurnTrue();
    }

    public void playCard(){
        //calls the playCard method contained in the Player class
        turnPlayer.playCard(selectedHandCard, selectedCoordinates);
        //method to check if the player has reached 20 points
        setLastTurnTrue();
    }

    public void setSelectedHandCard(PlayableCard card) {
        this.selectedHandCard = card;
    }

    public void setSelectedCoordinates(Coordinates coordinates) {
        this.selectedCoordinates = coordinates;
    }

    //public void setLastTurnTrue() {isLastTurn = true;}

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
