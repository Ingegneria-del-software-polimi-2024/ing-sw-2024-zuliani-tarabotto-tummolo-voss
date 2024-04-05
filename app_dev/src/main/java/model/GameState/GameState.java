package model.GameState;

import Exceptions.EmptyCardSourceException;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.Generators.PlayableDeckGenerator;
import model.deckFactory.Generators.GoldCardsDeckGenerator;
import model.deckFactory.Generators.ResourceCardsDeckGenerator;
import model.deckFactory.Generators.*;
import model.placementArea.Coordinates;
import model.player.Player;
import model.deckFactory.*;

import java.util.ArrayList;
import java.util.List;

public class GameState {


    private List<Player> players;
    private String id;
    private Player turnPlayer;
    private PlayableDeck goldDeck;
    private PlayableDeck resourceDeck;
    private ObjectiveDeck objectiveDeck;
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


        //creates and shuffles decks
        initializeDecks();
        //Extract open cards
        initializeOpenCards();
        //give each player a random starting card
        initializePlayersStarterCard();
        //we draw two random objectiveCards
        initializeCommonObjectives();
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


    private void initializeDecks(){
        //creates and shuffles decks
        PlayableDeckGenerator resourcesDeckGenerator = new ResourceCardsDeckGenerator();
        PlayableDeckGenerator goldenDeckGenerator = new GoldCardsDeckGenerator();
        PlayableDeckGenerator starterDeckGenerator = new StarterCardsDeckGenerator();
        ObjectiveCardsDeckGenerator objectiveCardsDeckGenerator = new ObjectiveCardsDeckGenerator();
        objectiveDeck = objectiveCardsDeckGenerator.generateDeck();
        resourceDeck =  resourcesDeckGenerator.generateDeck();
        goldDeck =  goldenDeckGenerator.generateDeck();
        startingDeck =  starterDeckGenerator.generateDeck();
        objectiveDeck.shuffle();
        resourceDeck.shuffle();
        goldDeck.shuffle();
        startingDeck.shuffle();
    }
    private void initializeOpenCards() {
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
            p.drawCard( resourceDeck.extract());
            p.drawCard( resourceDeck.extract());
        }
    }

    private void initializePlayersStarterCard() {
        for(Player p : players) {
            p.setStarterCard(startingDeck.extract());
        }
    }

    private void initializeCommonObjectives() {
        commonObjectives = new ArrayList<>();
        commonObjectives.add(objectiveDeck.extract());
        commonObjectives.add(objectiveDeck.extract());
    }


    //checks if any player reached 20 points or if both the gold and the resource decks are empty
    public void setLastTurnTrue(){
        //checks if a player has reached 20 points
        for(int i=0; i<players.size(); i++){
            if (players.get(i).getPoints() >= 20) {
                isLastTurn = true;
                break;
            }
        }
        //return isLastTurn;
        //checks if both decks are empty
        if(goldDeck.getSize() == 0 && resourceDeck.getSize() == 0 && openGold.isEmpty() && openResources.isEmpty()){
            isLastTurn = true;
        }
    }

    public boolean getLastTurn(){
        return isLastTurn;
    }


    public void nextPlayer() {
        int currentPlayer = players.indexOf(turnPlayer);
        if(currentPlayer == players.size() - 1) {
            turnPlayer = players.get(0);
        } else{turnPlayer = players.get(currentPlayer+1);}
    }

    public void drawCardGoldDeck() throws EmptyCardSourceException {
        //takes away the first card of the deck and calls the following method
        if(goldDeck.getSize() > 0){
            turnPlayer.drawCard( goldDeck.extract());
        } else {throw new EmptyCardSourceException("gold deck is empty");}
        //method to check if both decks are empty
        setLastTurnTrue();
    }

    public void drawCardResourcesDeck() throws EmptyCardSourceException{
        //takes away the first card of the deck and calls the following method
        if(resourceDeck.getSize() > 0){
            turnPlayer.drawCard( resourceDeck.extract());
        }else {throw new EmptyCardSourceException("resource deck is empty");}
        setLastTurnTrue();
    }

    public void drawCardOpenGold(int index) throws EmptyCardSourceException {
        if(openGold.get(index) != null) {
            //takes away the card at specified position from openGold
            turnPlayer.drawCard(openGold.get(index));
            //replaces the card taken with the first of the goldDeck
            openGold.remove(index);
        }else {
            throw new EmptyCardSourceException("OpenGold_"+ index + "is empty");
        }
        if(goldDeck.getSize() > 0) openGold.add(index, goldDeck.extract());
        setLastTurnTrue();
    }

    public void drawCardOpenResources(int index) throws EmptyCardSourceException {
        if(openResources.get(index) != null) {
            //takes away the card at specified position from openGold
            turnPlayer.drawCard(openResources.get(index));
            //replaces the card taken with the first of the goldDeck
            openResources.remove(index);
        }else {
            throw new EmptyCardSourceException("OpenResources_"+ index + "is empty");
        }
        if(resourceDeck.getSize() > 0) openResources.add(index,  resourceDeck.extract());
        setLastTurnTrue();
    }

    public void playCard(){
        //calls the playCard method contained in the Player class
        turnPlayer.playCard(selectedHandCard, selectedCoordinates);
        //method to check if the player has reached 20 points
        setLastTurnTrue();
    }

    //calls the PlacementArea method to print available places where the player can put the selected card
    public void printPlayerAvailablePlaces() {turnPlayer.getPlacementArea().printAvailablePlaces();}

    //calls the PlacementArea method to print the player's cards disposition
    public void printPlayerDisposition(){
        getTurnPlayer().getPlacementArea().printDisposition();
    }

    public void printCommonObjectives() {
        System.out.println("COMMON OBJECTIVE 1:");
        commonObjectives.get(0).printCard();
        System.out.println("COMMON OBJECTIVE 2:");
        commonObjectives.get(1).printCard();
    }

    public void playStarterCard() {
        turnPlayer.playStarterCard();
    }

    public void setSelectedHandCard(PlayableCard card) {
        this.selectedHandCard = card;
    }

    public void setSelectedCoordinates(Coordinates coordinates) {
        this.selectedCoordinates = coordinates;
    }

    //sets the faceSide for the player's starting card
    public void setStartingCardFace(boolean faceSide) {
        turnPlayer.getStarterCard().setFaceSide(faceSide);
    }

    //sets the faceSide for the card that the player has selected from his hand
    public void setSelectedCardFace(boolean faceSide) {
        this.selectedHandCard.setFaceSide(faceSide);
    }

    //returns the total points of turnPlayer
    public int getPoints() {return turnPlayer.getPoints(); }

    public PlayableCard getPlayerHandCard(int index) {
        return turnPlayer.getPlayingHand().get(index);
    }

    //public void setLastTurnTrue() {isLastTurn = true;}

    //are all of these methods necessary? idk but rn i'll keep them
    public Deck getGoldDeck() {
        return goldDeck;
    }

    public PlayableDeck getResourceDeck() {
        return resourceDeck;
    }

    public PlayableDeck getStarterDeck(){return startingDeck;}

    public ObjectiveDeck getObjectiveDeck() {return objectiveDeck;}

    public List<PlayableCard> getOpenResources() {
        return openResources;
    }

    public List<PlayableCard> getOpenGold() {
        return openGold;
    }



}
