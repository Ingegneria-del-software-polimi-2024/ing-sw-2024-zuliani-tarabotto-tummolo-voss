package model.GameState;

import model.cards.ObjectiveCard;
import model.cards.PlayableCards.GoldCard;
import model.cards.PlayableCards.PlayableCard;
import model.cards.PlayableCards.ResourceCard;
import model.deckFactory.Generators.DeckGenerator;
import model.deckFactory.Generators.PlayableDeckGenerator;
import model.deckFactory.Generators.GoldCardsDeckGenerator;
import model.deckFactory.Generators.ResourceCardsDeckGenerator;
import model.deckFactory.Generators.*;
import model.deckFactory.ResourcesDeck;
import model.placementArea.Coordinates;
import model.player.Player;
import model.deckFactory.*;
import model.cards.*;

import java.util.ArrayList;
import java.util.List;

public class GameState {


    private List<Player> players;
    private String id;
    private Player turnPlayer;
    private PlayableDeck goldDeck;
    private PlayableDeck resourceDeck;
    //private ObjectiveDeck deckObjectives;
    private PlayableDeck startingDeck;
    private List<ObjectiveCard> commonObjectives; //2 elements in the list
    //do we actually want Lists of PlayableCard or we prefer List<GoldCard> and List<ResourceCard>???
    private List<PlayableCard> openGold; //2 elements in the list
    private List<PlayableCard> openResources; //2 elements in the list
    private boolean isLastTurn = false;
    private PlayableCard selectedHandCard;
    private Coordinates selectedCoordinates;


    public GameState(ArrayList<String> nickNames, String id) {
        //creates a new players list with the nicknames taken from input
        players = new ArrayList<Player>();
        for(String name : nickNames) {
            Player p;
            players.add(p = new Player());
            p.setNickname(name);
        }
        this.turnPlayer = players.get(0);
        this.id = id;
    }
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

    public void initializeDecks(){
        //creates and shuffles decks
        PlayableDeckGenerator resourcesDeckGenerator = new ResourceCardsDeckGenerator();
        PlayableDeckGenerator goldenDeckGenerator = new GoldCardsDeckGenerator();
        PlayableDeckGenerator starterDeckGenerator = new StarterCardsDeckGenerator();
        resourceDeck =  resourcesDeckGenerator.generateDeck();
        goldDeck =  goldenDeckGenerator.generateDeck();
        startingDeck =  starterDeckGenerator.generateDeck();
        resourceDeck.shuffle();
        goldDeck.shuffle();
        startingDeck.shuffle();
    }
    public void initializeOpenCards() {
        //extract from goldDeck and resourceDeck the four open cards
        openResources = new ArrayList<PlayableCard>();
        openGold = new ArrayList<PlayableCard>();
        openResources.add(resourceDeck.extract());
        openResources.add(resourceDeck.extract());
        openGold.add( goldDeck.extract());
        openGold.add( goldDeck.extract());
    }

    public void initializePlayersHands() {
        for(Player p : players) {
            p.drawCard( goldDeck.extract());
        }
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
        if(goldDeck.getSize() > 0) turnPlayer.drawCard( goldDeck.extract());
        //method to check if both decks are empty
        setLastTurnTrue();
    }

    public void drawCardResourcesDeck(){
        //takes away the first card of the deck and calls the following method
        if(resourceDeck.getSize() > 0) turnPlayer.drawCard( resourceDeck.extract());
        setLastTurnTrue();
    }

    public void drawCardOpenGold(int index){
        //takes away the card at specified position from openGold
        turnPlayer.drawCard(openGold.get(index));
        //replaces the card taken with the first of the goldDeck
        openGold.remove(index);
        if(goldDeck.getSize() > 0) openGold.set(index, goldDeck.extract());
        setLastTurnTrue();
    }

    public void drawCardOpenResources(int index) {
        //same as drawCardOpenGold
        turnPlayer.drawCard(openResources.get(index));
        openResources.remove(index);
        if(resourceDeck.getSize() > 0) openResources.set(index,  resourceDeck.extract());
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
    public Deck getGoldDeck() {
        return goldDeck;
    }

    public Deck getResourceDeck() {
        return resourceDeck;
    }

    public List<PlayableCard> getOpenResources() {
        return openResources;
    }

    public List<PlayableCard> getOpenGold() {
        return openGold;
    }


}
