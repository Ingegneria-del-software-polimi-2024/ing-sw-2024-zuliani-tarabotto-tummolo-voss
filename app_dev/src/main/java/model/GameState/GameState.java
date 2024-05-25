package model.GameState;

import Server.ModelListener;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import model.Exceptions.CantPlaceCardException;
import model.Exceptions.EmptyCardSourceException;
//import Server.ModelListener;
import model.Exceptions.KickOutOfGameException;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Pawn;
import model.placementArea.Coordinates;
import model.player.Player;
import model.deckFactory.*;

import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;
import java.util.HashMap;
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
    private List<ObjectiveCard> objectiveBuffer;


    /**
     * class constructor: creates a new Player for each name contained in nickNames,
     *                    creates a new CommonTable and calls the method to initialize it
     * @param nickNames ArrayList of Strings
     * @param id the unique id for gameState
     */
    public GameState(ArrayList<String> nickNames, String id, ModelListener modelListener) {
        System.out.println("gamestate created");
        this.modelListener = modelListener;
        //creates a new players list with the nicknames taken from input
        players = new ArrayList<Player>();
        for(String name : nickNames) {
            Player p;
            players.add(p = new Player());
            p.setNickname(name);
            ///// here we set the player's pawn color to a random one still available
            p.setPawnColor(Pawn.randomPick());
        }
        this.turnPlayer = players.get(0);
        this.id = id;
        this.commonTable = new CommonTable();
        this.objectiveBuffer = new ArrayList<>();
        //function that calls every initializing method contained in commonTable
        commonTable.initialize(players);

        //NOTIFICATION: decks order, open cards, commonObjectives, nicknames, gameId and initialPlayer
        //we don't send the objective and starter deck because it would be useless
        modelListener.notifyChanges(commonTable.getGoldDeck(), commonTable.getResourceDeck(), commonTable.getOpenGold(),
                commonTable.getOpenResources(), nickNames, id,
                commonTable.getCommonObjectives().get(0), commonTable.getCommonObjectives().get(1));

        //NOTIFICATION: first turnPlayer
        modelListener.notifyChanges(turnPlayer.getNickname());

        //NOTIFICATION: about each player's starterCard and his hand
        for(Player p : players){
            modelListener.notifyChanges(p.getStarterCard(), p.getNickname(), p.getPawnColor());
            modelListener.notifyChanges(p.getPlayingHand(),p.getNickname());
        }

    }


    //////////////////// GENERAL TURN CONTROL METHODS ///////////////////////////////////////
    /**
     * this method is called at the end of the game and it checks for each Player if they completed any Objective(both secret and commoon)
     */
    public void calculateFinalPoints(){
        int i, j;
        HashMap<String, Integer> finalPoints = new HashMap<>();
        for(i=0; i< players.size(); i++){
            players.get(i).calculateSecretObj();
            for(j=0; j<2; j++){
                players.get(i).calculateSingleCommonObj(commonTable.getCommonObjectives().get(j));
            }
            finalPoints.put(players.get(i).getNickname(),players.get(i).getPoints() );
        }
        //NOTIFICATION:
        modelListener.notifyChanges(finalPoints);
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

        //broadcast notification about the new turnPlayer
        modelListener.notifyChanges(turnPlayer.getNickname());
    }

    /**
     * INTERFACE METHOD
     * based on Player input, the method sets the selected pawn color for the player
     * @param pawnColor Pawn selected by the Player
     */
    public void setPlayerPawnColor(Pawn pawnColor) {
        turnPlayer.setPawnColor(pawnColor);
    }

    /**
     * when the state of gameState is modified, a notification is sent in broadcast to all clients
     * @param state
     */
    public void setTurnState(TurnState state) {
        this.turnState = state;
        //NOTIFICATION: ABOUT THE CHANGED STATE OF GAMESTATE
        modelListener.notifyChanges(state);
    }

    public void distributeSecretOjectives() {
        for(Player p: players){
            objectiveBuffer.add(getObjectiveDeck().extract());
            objectiveBuffer.add(getObjectiveDeck().extract());
            //NOTIFICATION: about the two objectives the player has to choose between
            modelListener.notifyChanges(objectiveBuffer.get(objectiveBuffer.size()-2), objectiveBuffer.get(objectiveBuffer.size()-1), p.getNickname());
        }
    }

    public void setPlayerSecretObjective(String cardId, String player){
        for(Player p : players){
            if(p.getNickname().equals(player)){
                for(ObjectiveCard c : objectiveBuffer){
                    if(String.valueOf(c.getId()).equals(cardId)){
                        p.setSecretObjective(c);
                        //NOTIFICATION: about which secretObjective the player chose
                        //modelListener.notifyChanges( c, player);
                        return;
                    }
                }
            }
        }
    }

    /**
     * checks for each card in the turnPlayer hand if it can be placed.
     * information about whether a card is placeable and where it can be placed are then sent to the turnPlayer
     */
    public void playingTurn(){
        boolean[] canBePlaced = new boolean[3];
        for(int i = 0; i < 3; i++){
            canBePlaced[i] = turnPlayer.getPlacementArea().tuiCanBePlaced(turnPlayer.getPlayingHand().get(i));
        }
        //NOTIFICATION: about which cards the player can place and where on the placementArea
        modelListener.notifyChanges(turnPlayer.getNickname(), turnPlayer.getPlacementArea().getAvailablePlaces(), canBePlaced);
    }

    public boolean checkMessage(MessageFromClient message){return turnState.controlMessage(message);}

    ////////////////// CARDS PLACEMENT RELATED METHODS //////////////////////////////////////////////////////
    /**
     * INTERFACE METHOD
     * calls playCard method contained in Player class
     */
    public void playCard() throws CantPlaceCardException {
        try {
            turnPlayer.playCard(selectedHandCard, selectedCoordinates);
        }catch (CantPlaceCardException e){
            wrongCardRoutine(e);
            throw e;
        }
        //we check if the player reached 20 points
        setLastTurnTrue();
        //NOTIFICATION: the player disposition, points, available resources are updated
        modelListener.notifyChanges(turnPlayer.getNickname(), turnPlayer.getPlacementArea().getDisposition(),
                                    turnPlayer.getPlacementArea().getAvailablePlaces(),
                                    turnPlayer.getPoints());


        //NOTIFICATION: we notified the player of the new resources he acquired
        modelListener.notifyChanges(turnPlayer.getNickname(), turnPlayer.getPlacementArea().getAllArtifactsNumber(),
                turnPlayer.getPlacementArea().getAllElementsNumber());

        //NOTIFICATION:
        //we update the player hand by removing the card he placed
        //we could remove this
        modelListener.notifyChanges(turnPlayer.getPlayingHand(), turnPlayer.getNickname());

    }

    /**
     * notifies the player of not being able to place the card
     * @param e
     */
    public void wrongCardRoutine(CantPlaceCardException e){
        modelListener.notifyChanges(turnPlayer.getNickname(), e);
    }

    /**
     * INTERFACE METHOD
     * calls playCard method contained in Player class
     */
    public void playStarterCard(String player) {
        for(Player p : players){
            if(p.getNickname().equals(player)){
                try {
                    p.playStarterCard();
                }catch (KickOutOfGameException e){
                    modelListener.notifyChanges(player, e);
                }
                //NOTIFICATION ABOUT THE STARTER CARD
                modelListener.notifyChanges(p.getNickname(), p.getPlacementArea().getDisposition(), p.getPlacementArea().getAvailablePlaces(),
                        p.getPoints());

                //NOTIFICATION: we notified the player of the new resources he acquired
                modelListener.notifyChanges(p.getNickname(), p.getPlacementArea().getAllArtifactsNumber(),
                        p.getPlacementArea().getAllElementsNumber());
            }
        }
        //turnPlayer.playStarterCard();
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
     * @param player
     */
    public void setStartingCardFace(boolean faceSide, String player) {
        for(Player p : players){
            if(p.getNickname().equals(player)){
                p.getStarterCard().setFaceSide(faceSide);
                return;
            }
        }
        //turnPlayer.getStarterCard().setFaceSide(faceSide);
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
     */
    public void drawCardGoldDeck(){
        try {
            commonTable.drawCardGoldDeck(turnPlayer);
        } catch (EmptyCardSourceException e) {
            modelListener.notifyChanges(turnPlayer.getNickname(), e);
        }
        setLastTurnTrue();
        modelListener.notifyChanges(getGoldDeck().getCards(), 0);
        modelListener.notifyChanges(turnPlayer.getPlayingHand(), turnPlayer.getNickname());
    }

    /**
     * INTERFACE METHOD
     * draws a card from resourcesDeck
     */
    public void drawCardResourcesDeck(){
        try {
            commonTable.drawCardResourcesDeck(turnPlayer);
        } catch (EmptyCardSourceException e) {
            modelListener.notifyChanges(turnPlayer.getNickname(), e);
        }
        setLastTurnTrue();
        modelListener.notifyChanges(getResourceDeck().getCards(), 2);
        modelListener.notifyChanges(turnPlayer.getPlayingHand(), turnPlayer.getNickname());
    }

    /**
     * INTERFACE METHOD
     * draws a card from openGold at index 0 or 1
     */
    public void drawCardOpenGold(int index){
        try {
            commonTable.drawCardOpenGold(index, turnPlayer);
        } catch (EmptyCardSourceException e) {
            modelListener.notifyChanges(turnPlayer.getNickname(), e);
        }
        setLastTurnTrue();
        modelListener.notifyChanges(getOpenGold(), 1, index);
        modelListener.notifyChanges(turnPlayer.getPlayingHand(), turnPlayer.getNickname());
    }

    /**
     * INTERFACE METHOD
     * draws a card from openResources at index 0 or 1
     */
    public void drawCardOpenResources(int index){
        try {
            commonTable.drawCardOpenResources(index, turnPlayer);
        } catch (EmptyCardSourceException e) {
            modelListener.notifyChanges(turnPlayer.getNickname(), e);
        }
        setLastTurnTrue();
        modelListener.notifyChanges(getOpenResources(), 3, index);
        modelListener.notifyChanges(turnPlayer.getPlayingHand(), turnPlayer.getNickname());
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
    public ArrayList<Player> getPlayers() { return players; }




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
