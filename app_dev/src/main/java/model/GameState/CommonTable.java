package model.GameState;

import Exceptions.EmptyCardSourceException;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.Generators.*;
import model.deckFactory.ObjectiveDeck;
import model.deckFactory.PlayableDeck;
import model.placementArea.Coordinates;
import model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class CommonTable {

    private PlayableDeck goldDeck;
    private PlayableDeck resourceDeck;
    private ObjectiveDeck objectiveDeck;
    private PlayableDeck startingDeck;
    private List<ObjectiveCard> commonObjectives; //2 elements in the list
    private List<PlayableCard> openGold; //2 elements in the list
    private List<PlayableCard> openResources; //2 elements in the list

    public CommonTable(List<Player> players) {
        //creates and shuffles decks
        initializeDecks();
        //Extract open cards
        initializeOpenCards();
        //give each player a random starting card
        initializePlayersStarterCard(players);
        //we draw two random objectiveCards
        initializeCommonObjectives();
        //every player draws three random cards: 1 goldCard and 2 resourceCard
        initializePlayersHands(players);
    }

    ////////////////////// INITIALIZATION FUNCTIONS ////////////////////////
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

    private void initializeCommonObjectives() {
        commonObjectives = new ArrayList<>();
        commonObjectives.add(objectiveDeck.extract());
        commonObjectives.add(objectiveDeck.extract());
    }

    private void initializePlayersHands(List<Player> players) {
        for(Player p : players) {
            p.drawCard( goldDeck.extract());
            p.drawCard( resourceDeck.extract());
            p.drawCard( resourceDeck.extract());
        }
    }

    private void initializePlayersStarterCard(List<Player> players) {
        for(Player p : players) {
            p.setStarterCard(startingDeck.extract());
        }
    }


    /////////////////// CARD DRAWING RELATED FUNCTIONS //////////////////////////////////////////////////////////////////
    //returns true if all drawable cards are finished
    public boolean checkEmtpyDecks() {
        if(goldDeck.getSize() == 0 && resourceDeck.getSize() == 0 && openGold.isEmpty() && openResources.isEmpty()){
            return true;
        }
        return false;
    }
    public void drawCardGoldDeck(Player turnPlayer) throws EmptyCardSourceException {
        //takes away the first card of the deck and calls the following method
        if(goldDeck.getSize() > 0){
            turnPlayer.drawCard( goldDeck.extract());
        } else {throw new EmptyCardSourceException("gold deck is empty");}
    }

    public void drawCardResourcesDeck(Player turnPlayer) throws EmptyCardSourceException{
        //takes away the first card of the deck and calls the following method
        if(resourceDeck.getSize() > 0){
            turnPlayer.drawCard( resourceDeck.extract());
        }else {throw new EmptyCardSourceException("resource deck is empty");}
    }

    public void drawCardOpenGold(int index, Player turnPlayer) throws EmptyCardSourceException {
        if(openGold.get(index) != null) {
            //takes away the card at specified position from openGold
            turnPlayer.drawCard(openGold.get(index));
            //replaces the card taken with the first of the goldDeck
            openGold.remove(index);
        }else {
            throw new EmptyCardSourceException("OpenGold_"+ index + "is empty");
        }
        if(goldDeck.getSize() > 0) openGold.add(index, goldDeck.extract());
    }

    public void drawCardOpenResources(int index, Player turnPlayer) throws EmptyCardSourceException {
        if(openResources.get(index) != null) {
            //takes away the card at specified position from openGold
            turnPlayer.drawCard(openResources.get(index));
            //replaces the card taken with the first of the goldDeck
            openResources.remove(index);
        }else {
            throw new EmptyCardSourceException("OpenResources_"+ index + "is empty");
        }
        if(resourceDeck.getSize() > 0) openResources.add(index,  resourceDeck.extract());
    }


    ////////////////////// GETTER METHODS /////////////////////////////////////////////////////////////////////////
    public List<ObjectiveCard> getCommonObjectives() {return commonObjectives;}
    public PlayableDeck getGoldDeck() { return goldDeck; }
    public PlayableDeck getResourceDeck() { return resourceDeck; }
    public PlayableDeck getStarterDeck(){return startingDeck;}
    public ObjectiveDeck getObjectiveDeck() {return objectiveDeck;}
    public List<PlayableCard> getOpenResources() { return openResources; }
    public List<PlayableCard> getOpenGold() { return openGold; }

    ///////////////////// SETTER METHODS /////////////////////////////////////////////
}
