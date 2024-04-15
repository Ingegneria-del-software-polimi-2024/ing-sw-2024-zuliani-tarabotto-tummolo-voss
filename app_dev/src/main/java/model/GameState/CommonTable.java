package model.GameState;

import Exceptions.EmptyCardSourceException;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.Generators.*;
import model.deckFactory.ObjectiveDeck;
import model.deckFactory.PlayableDeck;
import model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonTable {

    private PlayableDeck goldDeck;
    private PlayableDeck resourceDeck;
    private ObjectiveDeck objectiveDeck;
    private PlayableDeck startingDeck;
    private List<ObjectiveCard> commonObjectives; //2 elements in the list
    private List<PlayableCard> openGold; //2 elements in the list
    private List<PlayableCard> openResources; //2 elements in the list


    /**
     * this method calls all the initialization methods related to CommonTable
     * (it is more convenient not to do this in the class constructor for testing purposes)
     * @param players
     */
    public void initialize(List<Player> players){
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
    /**
     * it creates the decks using the factory pattern,
     * each deck is then randomly shuffled
     */
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

    /**
     * the two first cards from both the GoldDeck and the ResourceDeck are extracted and revealed
     */
    private void initializeOpenCards() {
        //extract from goldDeck and resourceDeck the four open cards
        openResources = new ArrayList<PlayableCard>();
        openGold = new ArrayList<PlayableCard>();
        openResources.add(resourceDeck.extract());
        openResources.add(resourceDeck.extract());
        openGold.add( goldDeck.extract());
        openGold.add( goldDeck.extract());
    }

    /**
     * the two first cards from the ObjectiveDeck are extracted and revealed
     */
    private void initializeCommonObjectives() {
        commonObjectives = new ArrayList<>();
        commonObjectives.add(objectiveDeck.extract());
        commonObjectives.add(objectiveDeck.extract());
    }

    /**
     * each Player is given two ResourceCard and one GoldCard
     * @param players
     */
    private void initializePlayersHands(List<Player> players) {
        for(Player p : players) {
            p.drawCard( goldDeck.extract());
            p.drawCard( resourceDeck.extract());
            p.drawCard( resourceDeck.extract());
        }
    }

    /**
     * each Player is given a StarterCard
     * @param players
     */
    private void initializePlayersStarterCard(List<Player> players) {
        for(Player p : players) {
            p.setStarterCard(startingDeck.extract());
        }
    }



    /////////////////// METHODS RELATED TO DRAWING FROM DECKS //////////////////////////////////////////////////////////////////
    /**
     * returns true if all drawable cards on the CommonTable are finished
     * @return Boolean
     */
    public boolean checkEmptyDecks() {
        if(goldDeck.getSize() == 0 && resourceDeck.getSize() == 0 && openGold.isEmpty() && openResources.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * the first card in the GoldDeck is extracted and given to the turnPlayer
     * @param turnPlayer the currentPlayer
     * @throws EmptyCardSourceException
     */
    public void drawCardGoldDeck(Player turnPlayer) throws EmptyCardSourceException {
        if(goldDeck.getSize() > 0){
            turnPlayer.drawCard( goldDeck.extract());
        } else {throw new EmptyCardSourceException("gold deck is empty");}
    }

    /**
     * the first card in the ResourceDeck is extracted and given to the turnPlayer
     * @param turnPlayer the currentPlayer
     * @throws EmptyCardSourceException
     */
    public void drawCardResourcesDeck(Player turnPlayer) throws EmptyCardSourceException{
        //takes away the first card of the deck and calls the following method
        if(resourceDeck.getSize() > 0){
            turnPlayer.drawCard( resourceDeck.extract());
        }else {throw new EmptyCardSourceException("resource deck is empty");}
    }

    /**
     * the card at the specified index position in openGold is extracted and given to the turnPlayer
     * @param turnPlayer the currentPlayer
     * @throws EmptyCardSourceException
     */
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

    /**
     * the card at the specified index position in openResources is extracted and given to the turnPlayer
     * @param turnPlayer the currentPlayer
     * @throws EmptyCardSourceException
     */
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





    ///////////////////////FOR TESTING PURPOSES ONLY //////////////////////////////////////////////////////////////
    public void definedDeckInitialization(List<Player> players) {
        //creates and shuffles decks
        long seed = 12345;
        PlayableDeckGenerator resourcesDeckGenerator = new ResourceCardsDeckGenerator();
        PlayableDeckGenerator goldenDeckGenerator = new GoldCardsDeckGenerator();
        PlayableDeckGenerator starterDeckGenerator = new StarterCardsDeckGenerator();
        ObjectiveCardsDeckGenerator objectiveCardsDeckGenerator = new ObjectiveCardsDeckGenerator();
        objectiveDeck = objectiveCardsDeckGenerator.generateDeck();
        resourceDeck =  resourcesDeckGenerator.generateDeck();
        goldDeck =  goldenDeckGenerator.generateDeck();
        startingDeck =  starterDeckGenerator.generateDeck();

        int[] permutation1 = {23, 10, 5, 2, 36, 39, 4, 11, 32, 19, 29, 33, 6, 7, 21, 9, 18, 12, 24, 0, 22, 30, 25, 38, 14, 17, 37, 1, 35, 16, 31, 13, 20, 34, 27, 28, 15, 26, 8, 3};
        int[] permutation2 = {5, 2, 3, 0, 1, 4};
        int[] permutation3 = {11, 3, 13, 2, 7, 0, 6, 4, 14, 1, 8, 10, 5, 12, 15, 9};

        deterministicShuffle(resourceDeck, permutation1);
        deterministicShuffle(goldDeck, permutation1);
        deterministicShuffle(startingDeck, permutation2);
        deterministicShuffle(objectiveDeck, permutation3);

        //Extract open cards
        initializeOpenCards();
        //give each player a random starting card
        initializePlayersStarterCard(players);
        //we draw two random objectiveCards
        initializeCommonObjectives();
        //every player draws three random cards: 1 goldCard and 2 resourceCard
        initializePlayersHands(players);
    }

    public static  void deterministicShuffle(PlayableDeck list, int[] permutation) {
        // Fisher-Yates shuffle algorithm with a fixed permutation
        ArrayList<PlayableCard> copy = new ArrayList<>(list.getCards());
        for (int i = 0; i < list.getCards().size(); i++) {
            int j = permutation[i];
            PlayableCard temp = copy.get(i);
            copy.set(i, copy.get(j));
            copy.set(j, temp);
        }
        list.getCards().clear();
        list.getCards().addAll(copy);
    }
    public static  void deterministicShuffle(ObjectiveDeck list, int[] permutation) {
        // Fisher-Yates shuffle algorithm with a fixed permutation
        ArrayList<ObjectiveCard> copy = new ArrayList<>(list.getCards());
        for (int i = 0; i < list.getCards().size(); i++) {
            int j = permutation[i];
            ObjectiveCard temp = copy.get(i);
            copy.set(i, copy.get(j));
            copy.set(j, temp);
        }
        list.getCards().clear();
        list.getCards().addAll(copy);
    }

}
