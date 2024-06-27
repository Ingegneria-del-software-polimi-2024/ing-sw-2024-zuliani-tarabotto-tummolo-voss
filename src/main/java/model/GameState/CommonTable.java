package model.GameState;

import model.Exceptions.EmptyCardSourceException;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.Generators.*;
import model.deckFactory.ObjectiveDeck;
import model.deckFactory.PlayableDeck;
import model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the common table in the game.
 * The common table holds the decks of cards and the open cards that are available to all players.
 */
public class CommonTable {

    /**
     * The deck of gold cards.
     */
    private PlayableDeck goldDeck;

    /**
     * The deck of resource cards.
     */
    private PlayableDeck resourceDeck;

    /**
     * The deck of objective cards.
     */
    private ObjectiveDeck objectiveDeck;

    /**
     * The deck of starter cards.
     */
    private PlayableDeck startingDeck;

    /**
     * The list of common objective cards.
     */
    private List<ObjectiveCard> commonObjectives; //2 elements in the list

    /**
     * The list of open gold cards.
     */
    private List<PlayableCard> openGold; //2 elements in the list

    /**
     * The list of open resource cards.
     */
    private List<PlayableCard> openResources; //2 elements in the list

    /**
     * this method calls all the initialization methods related to CommonTable
     * (it is more convenient not to do this in the class constructor for testing purposes)
     *
     * @param players the players
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
     *
     * @return Boolean boolean
     */
    public boolean checkEmptyDecks() {
        return goldDeck.getSize() == 0 && resourceDeck.getSize() == 0 /*&& openGold.isEmpty() && openResources.isEmpty()*/;
    }

    /**
     * the first card in the GoldDeck is extracted and given to the turnPlayer
     *
     * @param turnPlayer the currentPlayer
     * @throws EmptyCardSourceException the empty card source exception
     */
    public void drawCardGoldDeck(Player turnPlayer) throws EmptyCardSourceException {
        if(goldDeck.getSize() > 0){
            turnPlayer.drawCard( goldDeck.extract());
        } else {throw new EmptyCardSourceException(1);}
    }

    /**
     * the first card in the ResourceDeck is extracted and given to the turnPlayer
     *
     * @param turnPlayer the currentPlayer
     * @throws EmptyCardSourceException the empty card source exception
     */
    public void drawCardResourcesDeck(Player turnPlayer) throws EmptyCardSourceException{
        //takes away the first card of the deck and calls the following method
        if(resourceDeck.getSize() > 0){
            turnPlayer.drawCard( resourceDeck.extract());
        }else {throw new EmptyCardSourceException(4);}
    }

    /**
     * the card at the specified index position in openGold is extracted and given to the turnPlayer
     *
     * @param index      the index
     * @param turnPlayer the currentPlayer
     * @throws EmptyCardSourceException the empty card source exception
     */
    public void drawCardOpenGold(int index, Player turnPlayer) throws EmptyCardSourceException {
        if(openGold.get(index) != null) {
            //takes away the card at specified position from openGold
            turnPlayer.drawCard(openGold.get(index));
            //replaces the card taken with the first of the goldDeck
            openGold.remove(index);
        }else {
            int idx = index==0? 2 : 3;
            throw new EmptyCardSourceException(idx);
        }
        if(goldDeck.getSize() > 0) openGold.add(index, goldDeck.extract());
        else openGold.add(index, null);
    }

    /**
     * the card at the specified index position in openResources is extracted and given to the turnPlayer
     *
     * @param index      the index
     * @param turnPlayer the currentPlayer
     * @throws EmptyCardSourceException the empty card source exception
     */
    public void drawCardOpenResources(int index, Player turnPlayer) throws EmptyCardSourceException {
        if(openResources.get(index) != null) {
            //takes away the card at specified position from openGold
            turnPlayer.drawCard(openResources.get(index));
            //replaces the card taken with the first of the goldDeck
            openResources.remove(index);
        }else {
            int idx = index==0? 5 : 6;
            throw new EmptyCardSourceException(idx);
        }
        if(resourceDeck.getSize() > 0) openResources.add(index,  resourceDeck.extract());
        else openResources.add(index, null);
    }



////////////////////// GETTER METHODS /////////////////////////////////////////////////////////////////////////
    /**
     * @return the list of common objective cards
     */
    public List<ObjectiveCard> getCommonObjectives() {return commonObjectives;}

    /**
     * @return the gold deck
     */
    public PlayableDeck getGoldDeck() { return goldDeck; }

    /**
     * @return the resource deck
     */
    public PlayableDeck getResourceDeck() { return resourceDeck; }

    /**
     * @return the starter deck
     */
    public PlayableDeck getStarterDeck(){return startingDeck;}

    /**
     * @return the objective deck
     */
    public ObjectiveDeck getObjectiveDeck() {return objectiveDeck;}

    /**
     * @return the open resource cards
     */
    public List<PlayableCard> getOpenResources() { return openResources; }

    /**
     * @return the open gold cards
     */
    public List<PlayableCard> getOpenGold() { return openGold; }

///////////////////////FOR TESTING PURPOSES ONLY //////////////////////////////////////////////////////////////

    /**
     * this method is used to initialize the decks with a fixed permutation (used for testign purposes only)
     * @param players
     */
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
        

        //for(PlayableCard c : goldDeck.getCards()){System.out.println(c.getId());}
        //Extract open cards
        initializeOpenCards();
        //give each player a random starting card
        initializePlayersStarterCard(players);
        //we draw two random objectiveCards
        initializeCommonObjectives();
        //every player draws three random cards: 1 goldCard and 2 resourceCard
        initializePlayersHands(players);
    }

    /**
     * this method is used to shuffle a deck with a fixed permutation (used for testing purposes only)
     * @param list the deck to shuffle
     * @param permutation the permutation to use
     */
    public  void deterministicShuffle(PlayableDeck list, int[] permutation) {
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

    /**
     * this method is used to shuffle a deck with a fixed permutation (used for testing purposes only)
     * @param list the deck to shuffle
     * @param permutation the permutation to use
     */
    public  void deterministicShuffle(ObjectiveDeck list, int[] permutation) {
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

//    private ObjectiveCard getOCard(int i){
//        return objectiveDeck.getCard(i);
//    }
}
