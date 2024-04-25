package model.GameState;

import Client.ClientAPI_COME;
import Exceptions.EmptyCardSourceException;
import Server.ModelListener;
import Server.ServerAPI_GO;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Pawn;
import model.placementArea.Coordinates;
import model.player.Player;
import model.deckFactory.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GameState {
    private ArrayList<Player> players;
    private String id;
    private Player turnPlayer;
    private boolean isLastTurn = false;
    private PlayableCard selectedHandCard;
    private Coordinates selectedCoordinates;
    private CommonTable commonTable;
    private TurnState turnState;
    private final int MAX_POINTS = 10;
    private ModelListener modelListener;


    /**
     * class constructor: creates a new Player for each name contained in nickNames,
     *                    creates a new CommonTable and calls the method to initialize it
     * @param nickNames ArrayList of Strings
     * @param id the unique id for gameState
     */
    public GameState(ArrayList<String> nickNames, String id, ServerAPI_GO serverAPIGo) {
        //creates a new players list with the nicknames taken from input
        serverAPIGo = serverAPIGo;
        players = new ArrayList<Player>();
        for(String name : nickNames) {
            Player p;
            players.add(p = new Player());
            p.setNickname(name);
        }
        this.turnPlayer = players.get(0);
        this.id = id;
        this.commonTable = new CommonTable();
        //function that calls every initializing method contained in commonTable
        commonTable.initialize(players);
    }




    //////////////////// GENERAL TURN CONTROL METHODS ///////////////////////////////////////
    /**
     * this method is called at the end of the game and it checks for each Player if they completed any Objective(both secret and commoon)
     */
    public void calculateFinalPoints(){
        int i, j;
        for(i=0; i< players.size(); i++){
            players.get(i).calculateSecretObj();
            for(j=0; j<2; j++){
                players.get(i).calculateSingleCommonObj(commonTable.getCommonObjectives().get(j));
            }
        }
    }

    /**
     * checks if any player reached 20 points or if both the gold and the resource decks are empty
     */
    public void setLastTurnTrue(){
        //checks if a player has reached 20 points
        for (Player player : players) {
            if (player.getPoints() >= MAX_POINTS) {
                isLastTurn = true;
                break;
            }
        }
        //checks if both decks are empty and also openGold and openResources are empty
        if(!isLastTurn) {isLastTurn = commonTable.checkEmptyDecks();}
    }

    /**
     * updates currentPlayer
     */
    public void nextPlayer() {
        int currentPlayer = players.indexOf(turnPlayer);
        if(currentPlayer == players.size() - 1) {
            turnPlayer = players.get(0);
        } else{turnPlayer = players.get(currentPlayer+1);}
    }

    /**
     * INTERFACE METHOD
     * based on Player input, the method sets the selected pawn color for the player
     * @param pawnColor Pawn selected by the Player
     */
    public void setPlayerPawnColor(Pawn pawnColor) {
        turnPlayer.setPawnColor(pawnColor);
    }



    ////////////////// CARDS PLACEMENT RELATED METHODS //////////////////////////////////////////////////////
    /**
     * INTERFACE METHOD
     * calls playCard method contained in Player class
     */
    public void playCard(){
        turnPlayer.playCard(selectedHandCard, selectedCoordinates);
        //we check if the player reached 20 points
        setLastTurnTrue();
    }

    /**
     * INTERFACE METHOD
     * calls playCard method contained in Player class
     */
    public void playStarterCard() {
        turnPlayer.playStarterCard();
    }

    /**
     * based on Player input, the method sets the card that will be placed on the player's PlacementArea
     * @param card PlayableCard selected by the Player
     */
    public void setSelectedHandCard(PlayableCard card) {
        this.selectedHandCard = card;
    }

    /**
     * based on Player input, the method sets the coordinates where the this.selectedHandCard will be placed
     * @param coordinates Coordinates selected by the Player
     */
    public void setSelectedCoordinates(Coordinates coordinates) {
        this.selectedCoordinates = coordinates;
    }

    /**
     * INTERFACE METHOD
     * based on Player input, the method sets the faceSide that will be visible for the starting card when placed
     * @param faceSide FaceSide selected by the Player
     */
    public void setStartingCardFace(boolean faceSide) {
        turnPlayer.getStarterCard().setFaceSide(faceSide);
    }


    /**
     * INTERFACE METHOD
     * based on Player input, the method sets the faceSide for the card that the player has selected from his hand
     * @param faceSide FaceSide selected by the Player
     */
    public void setSelectedCardFace(boolean faceSide) {
        selectedHandCard.setFaceSide(faceSide);
    }



    ////////////////////////////////METHODS RELATED TO DRAWING FROM DECKS///////////////////////////////////////
    /**
     * INTERFACE METHOD
     * draws a card from goldDeck
     * @throws EmptyCardSourceException
     */
    public void drawCardGoldDeck() throws EmptyCardSourceException {
        commonTable.drawCardGoldDeck(turnPlayer);
        setLastTurnTrue();
    }

    /**
     * INTERFACE METHOD
     * draws a card from resourcesDeck
     * @throws EmptyCardSourceException
     */
    public void drawCardResourcesDeck() throws EmptyCardSourceException {
        commonTable.drawCardResourcesDeck(turnPlayer);
        setLastTurnTrue();
    }

    /**
     * INTERFACE METHOD
     * draws a card from openGold at index 0 or 1
     * @throws EmptyCardSourceException
     */
    public void drawCardOpenGold(int index) throws EmptyCardSourceException {
        commonTable.drawCardOpenGold(index, turnPlayer);
        setLastTurnTrue();
    }

    /**
     * INTERFACE METHOD
     * draws a card from openResources at index 0 or 1
     * @throws EmptyCardSourceException
     */
    public void drawCardOpenResources(int index) throws EmptyCardSourceException {
        commonTable.drawCardOpenResources(index, turnPlayer);
        setLastTurnTrue();
    }


    public void nextState(){turnState.nextState();}
    public void nextStage(){turnState.nextStage();}

    /////////////// GETTER METHODS FOR COMMONTABLE ATTRIBUTES ////////////////////////
    public PlayableDeck getGoldDeck() { return commonTable.getGoldDeck(); }
    public PlayableDeck getResourceDeck() { return commonTable.getResourceDeck(); }
    public PlayableDeck getStarterDeck(){return commonTable.getStarterDeck();}
    public ObjectiveDeck getObjectiveDeck() {return commonTable.getObjectiveDeck();}
    public List<PlayableCard> getOpenResources() { return commonTable.getOpenResources(); }
    public List<PlayableCard> getOpenGold() { return commonTable.getOpenGold(); }
    public List<ObjectiveCard> getCommonObjectives(){return commonTable.getCommonObjectives();}
    public CommonTable getCommonTable(){return commonTable;}


    //////////////////////// GETTER METHODS FOR GAMESTATE ATTRIBUTES ///////////////////////////
    public Player getPlayer(int index){ return players.get(index); }
    public String getId(){ return id; }
    public Player getTurnPlayer(){ return turnPlayer; }
    public boolean getLastTurn(){ return isLastTurn; }
    public int getPoints() {return turnPlayer.getPoints(); }
    //returns turnPlayer's card at specified index in his hand
    public PlayableCard getPlayerHandCard(int index) { return turnPlayer.getPlayingHand().get(index); }




    /////////////////////// METHODS RELATED TO TESTING ONLY ////////////////////////////////////////////////////////////////
    //calls the PlacementArea method to print available places where the player can put the selected card
    public void printPlayerAvailablePlaces() {turnPlayer.getPlacementArea().printAvailablePlaces();}

    //calls the PlacementArea method to print the player's cards disposition
    public void printPlayerDisposition(){
        turnPlayer.getPlacementArea().printDisposition();
    }

    public void printCommonObjectives() {
        System.out.println("COMMON OBJECTIVE 1:");
        commonTable.getCommonObjectives().get(0).printCard();
        System.out.println("COMMON OBJECTIVE 2:");
        commonTable.getCommonObjectives().get(1).printCard();
    }

    /**
     * this is a variation of the default constructor, ONLY USED FOR THE CONTROLLER TESTS
     * @param nickNames
     * @param id
     * @param i
     */
    public GameState(ArrayList<String> nickNames, String id, int i) {
        players = new ArrayList<Player>();
        for(String name : nickNames) {
            Player p;
            players.add(p = new Player());
            p.setNickname(name);
        }
        this.turnPlayer = players.get(0);
        this.id = id;
        // initialize commonTable
        this.commonTable = new CommonTable();
        //function that calls every initializing method contained in commonTable
        commonTable.definedDeckInitialization(players);
    }
}
