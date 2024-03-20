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

    }
    public Player getTurnPlayer(){
        return turnPlayer;
    }
    public void calculateCommonObj(){

    }
    public void isLastTurn(){

        int i;

        for(i=0; i<4; i++){

            if(players.get(i).getPoints() == 20 || players.get(i).getPoints() > 20){

                setLastTurnTrue();
            }
        }

        if(GoldenDeck.isDeckFineshed() || ResourcesDeck.isDeckFinished){

            setLastTurnTrue();
        }

        return isLastTurn;
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

    public void drawCard(Deck deck){
        //takes away the first card of the deck and calls the following method
        turnPlayer.drawCard((PlayableCard) deck.extract());
    }

    public void drawCard(PlayableCard card){
        int index;
        //takes away the card from the ones face up
        turnPlayer.drawCard(card);
        //replaces the card taken with the first of the deck
        if (openGold.contains(card)){
            //if the card is contained in openGold it's replaced by the first card in goldDeck
            index = openGold.indexOf(card);
            openGold.remove(index);
            openGold.set(index, (PlayableCard)goldDeck.extract());
        } else {
            //if the card is contained in openResources it's replaced by the first card in resourcesDeck
            index = openResources.indexOf(card);
            openResources.remove(index);
            openResources.set(index, (PlayableCard)resourceDeck.extract());
        }
    }

    //calls the playCard method contained in the Player class
    public void playCard(){
        turnPlayer.playCard(selectedHandCard, selectedCoordinates);
    }

    public void setSelectedHandCard(PlayableCard card) {
        this.selectedHandCard = card;
    }

    public void setSelectedCoordinates(Coordinates coordinates) {
        this.selectedCoordinates = coordinates;
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
