package model.GameState;

import Server.ModelController;
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
import java.util.*;

public class GameState {

    private ArrayList<Player> players;
    private String id;
    private Player turnPlayer;
    private boolean isLastTurn = false;
    private PlayableCard selectedHandCard;
    private Coordinates selectedCoordinates;
    private CommonTable commonTable;
    private TurnState turnState;
    private final int MAX_POINTS = 1;//TODO insert 20 for real game, 10 for testing, 1 for rapid checking
    private ModelListener modelListener;
    private List<ObjectiveCard> objectiveBuffer;

    private ModelController modelController;

    /**
     * class constructor: creates a new Player for each name contained in nickNames,
     *                    creates a new CommonTable and calls the method to initialize it
     * @param nickNames ArrayList of Strings
     * @param id the unique id for gameState
     */
    public GameState(ArrayList<String> nickNames, String id, ModelListener modelListener, ModelController modelController, Set<String> disconnected) {
        this.modelListener = modelListener;
        this.modelController = modelController;
        //creates a new players list with the nicknames taken from input
        players = new ArrayList<Player>();
        int i = 0;
        List<Pawn> randomPawns = Pawn.getRandomPawns();

        for(String name : nickNames) {
            Player p;
            players.add(p = new Player());
            p.setNickname(name);
            ///// here we set the player's pawn color to a random one still available
            p.setPawnColor(randomPawns.get(i));
            i++;
        }
        this.turnPlayer = players.get(0);
        this.id = id;
        this.commonTable = new CommonTable();
        this.objectiveBuffer = new ArrayList<>();
        //function that calls every initializing method contained in commonTable
        commonTable.initialize(players);
        //we must control if all the players are indeed connected
        for(Player p : players){
            if(disconnected.contains(p.getNickname()))
                p.setInactive();
        }
        //commonTable.definedDeckInitialization(players);
        System.out.println("gamestate created");
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
            modelListener.notifyChanges(p.getNickname(), p.getPawnColor());
        }

    }


    //////////////////// GENERAL TURN CONTROL METHODS ///////////////////////////////////////
    /**
     * this method is called at the end of the game, it checks for each Player if they completed any Objective(both secret and commoon)
     */
    public void calculateFinalPoints(){
        int i, j;
        HashMap<String, Integer> finalPoints = new HashMap<>();
        ArrayList<Integer> playersObjectives = new ArrayList<Integer>();

        for(i=0; i< players.size(); i++){
            int obj = players.get(i).calculateSecretObj();
            for(j=0; j<2; j++){
                obj += players.get(i).calculateSingleCommonObj(commonTable.getCommonObjectives().get(j));
            }
            finalPoints.put(players.get(i).getNickname(),players.get(i).getPoints() );
            playersObjectives.add(i, obj);
        }
        //only players currently connected must be considered
        HashMap<String, Integer> validFinalPoints = new HashMap<>();
        for(String p : finalPoints.keySet())
            if(getPlayerByName(p).isActive())
               validFinalPoints.put(p, finalPoints.get(p));
        //if there are at least two players with the same number of points
        int max = validFinalPoints.values().stream().max(Integer::compareTo).orElse(0);
        ArrayList<String> winners = new ArrayList<String>();
        for (i = 0; i<players.size(); i++){
            Integer points = validFinalPoints.get(players.get(i).getNickname());
            if (points != null && points == max)
                winners.add(players.get(i).getNickname());
        }


        //NOTIFICATION:
        modelListener.notifyChanges(finalPoints, winners);
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
    public void nextPlayer(){
        do {
            int currentPlayer = players.indexOf(turnPlayer);
            if (currentPlayer == players.size() - 1) {
                turnPlayer = players.get(0);
            } else {
                turnPlayer = players.get(currentPlayer + 1);
            }
        }while(!turnPlayer.isActive());
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
     * @param state the state to be set
     */
    public void setTurnState(TurnState state) {
        if(state.equals(TurnState.PLACING_CARD_SELECTION)){
            System.out.println(turnPlayer.getNickname());
            if(!turnPlayer.isActive()){
                nextPlayer();
                System.out.println(turnPlayer.getNickname());
                playingTurn();
            }
        }
        this.turnState = state;
        //NOTIFICATION: ABOUT THE CHANGED STATE OF GAMESTATE
        modelListener.notifyChanges(state);
        //TODO this if else can be deleted by using overriding of a method
        if(state.equals(TurnState.OBJECTIVE_SELECTION)) {
            for (Player p : players) {
                if (!p.isActive())
                    recoveryObjectiveChoice(p);
            }
        }else if(state.equals(TurnState.END_GAME)){
            //we notify all the active players of the end of the game
            for(Player p : players){
                if(p.isActive())
                    if(!modelListener.notifyChanges(p.getNickname(), new KickOutOfGameException()))
                        modelController.handleDisconnection();
            }
        }
    }

    public void distributeSecretOjectives() {
        for (Player p : players) {
            objectiveBuffer.add(getObjectiveDeck().extract());
            objectiveBuffer.add(getObjectiveDeck().extract());
            System.out.println(objectiveBuffer.get(objectiveBuffer.size() - 2).getId());
            System.out.println(objectiveBuffer.get(objectiveBuffer.size() - 1).getId());
            //NOTIFICATION: about the two objectives the player has to choose between
            modelListener.notifyChanges(objectiveBuffer.get(objectiveBuffer.size() - 2), objectiveBuffer.get(objectiveBuffer.size() - 1), p.getNickname());
        }
        modelListener.notifyChanges(turnPlayer.getNickname());
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

    public void quitGame(String playerName){
        modelListener.notifyChanges(playerName, new KickOutOfGameException());
    }

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
                    setPlayerInactive(players.indexOf(getPlayerByName(player)));
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
     * @param player the player selecting the starting card
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


//    public void nextState(){turnState.nextState();}
//    public void nextStage(){turnState.nextStage();}

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
    public Player getPlayer(int index){ return players.get(index);}
    public String getId(){ return id; }
    public Player getTurnPlayer(){ return turnPlayer; }
    public boolean getLastTurn(){ return isLastTurn; }
    public int getPoints() {return turnPlayer.getPoints(); }
    //returns turnPlayer's card at specified index in his hand
    public PlayableCard getPlayerHandCard(int index) { return turnPlayer.getPlayingHand().get(index); }
    public ArrayList<Player> getPlayers() { return players; }
    Player getPlayerByName(String playerName){
        for(Player p : players)
            if(p.getNickname().equals(playerName))
                return p;
        return null;
    }



    /////////////////////// METHODS RELATED TO TESTING ONLY ////////////////////////////////////////////////////////////////
    //calls the PlacementArea method to print available places where the player can put the selected card
//    public void printPlayerAvailablePlaces() {turnPlayer.getPlacementArea().printAvailablePlaces();}
//
//    //calls the PlacementArea method to print the player's cards disposition
//    public void printPlayerDisposition(){
//        turnPlayer.getPlacementArea(). printDisposition();
//    }
//
//    public void printCommonObjectives() {
//        System.out.println("COMMON OBJECTIVE 1:");
//        commonTable.getCommonObjectives().get(0).printCard();
//        System.out.println("COMMON OBJECTIVE 2:");
//        commonTable.getCommonObjectives().get(1).printCard();
//    }

    /**
     * this is a variation of the default constructor, ONLY USED FOR THE CONTROLLER TESTS
     * @param nickNames
     * @param id
     * @param i
     */
    public GameState(ArrayList<String> nickNames, String id, int i, ModelListener listener) {
        this.modelListener = listener;
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

    ////////////////////////////////////////////RECONNECTIONS//////////////////////////////////////////////////////////

    /**
     * allows a player back in the game
     * @param playerID the name of the player to be added again
     */
    public void reconnect(String playerID){
        setPlayerActive(players.indexOf(getPlayerByName(playerID)));
        ArrayList<String> nickNames = new ArrayList<>();
        for(Player p : players) {
            nickNames.add(p.getNickname());
        }
        modelListener.notifyChanges(commonTable.getGoldDeck(), commonTable.getResourceDeck(), commonTable.getOpenGold(),
                commonTable.getOpenResources(), nickNames, id,
                commonTable.getCommonObjectives().get(0), commonTable.getCommonObjectives().get(1), playerID);

        Player p = getPlayerByName(playerID);
        modelListener.notifyChanges(p.getPlayingHand(), playerID);
        //we notify the player of the disposition
        for(Player x : players) {
            modelListener.notifyChanges(x.getNickname(), x.getPlacementArea().getDisposition(),
                    x.getPlacementArea().getAvailablePlaces(),
                    x.getPoints(), p.getNickname());

            //notification about the pawn color
            modelListener.notifyChanges(x.getNickname(), x.getPawnColor(), playerID);

            //notification about each player's resources and elements
            //modelListener.personalNotifyChanges(p.getNickname(), p.getPlacementArea().getAllArtifactsNumber(),
                   // p.getPlacementArea().getAllElementsNumber());

            //we broadcast to every player the resources the player has acquired
            modelListener.notifyChanges(x.getNickname(), x.getPlacementArea().getAllArtifactsNumber(),
                    x.getPlacementArea().getAllElementsNumber());
        }


        //NOTIFICATION: we notify the player of the new resources he acquired
       /* modelListener.personalNotifyChanges(p.getNickname(), p.getPlacementArea().getAllArtifactsNumber(),
                p.getPlacementArea().getAllElementsNumber());*/



        if(turnState.isStartingState()) {
            modelListener.notifyReconnection(playerID);
            turnState.reconnect(this, getPlayerByName(playerID));

        }else{
            modelListener.notifyChanges(getPlayerByName(playerID).getSecretObjective(), playerID);
            modelListener.notifyChanges(turnPlayer.getNickname(), playerID);
            if(turnPlayer == p)
                playingTurn();
            modelListener.notifyReconnection(playerID);
            modelListener.notifyChanges(turnState, playerID);
        }

    }
    /**
     * sets the player in an active state
     * @param index the index of the player
     */
    public void setPlayerActive(int index){
        players.get(index).setActive();
    }

    public void inizializationReconnection(Player p){
        //NOTIFICATION ABOUT THE STARTER CARD
        modelListener.notifyChanges(p.getNickname(), p.getPlacementArea().getDisposition(), p.getPlacementArea().getAvailablePlaces(),
                p.getPoints());
    }

    public void starterCardReconnection(Player player){
        modelListener.displayStarterCardNotification(player.getNickname(), player.getStarterCard(), player.getPawnColor() );
    }

    public void objectiveCardReconnection(Player player){
        modelListener.notifyChanges(turnPlayer.getNickname(), player.getNickname());
        modelListener.notifyChanges(player.getSecretObjective(), player.getNickname());
        modelListener.displayObjectiveNotification(player.getNickname());
    }
    ////////////////////////DISCONNECTIONS /////////////////////////////////////////////////////////////////////////////

    /**
     * sets the player in an inactive state
     * @param index the index of the player
     */
    public void setPlayerInactive(int index){
        Player inactivePlayer = players.get(index);
        inactivePlayer.setInactive();
        if (getTurnPlayer().equals(inactivePlayer) || turnState.isStartingState()){
            turnState.recoverDisconnection(this, inactivePlayer);
        }
    }

    ////////////////////////DISCONNECTION RECOVERY//////////////////////////////////////////////////////////////////////

    /**
     * if the turn palyer disconnects before choosing the secret objective, the firs of the two objectives is chosen and
     * the face card is assigned for the starter card
     * @param player the disconnected player
     */
    public void recoveryObjectiveChoice(Player player){
        int indx = players.indexOf(player);
        if(player.getSecretObjective() == null) {
            modelController.chooseSecretObjective(String.valueOf(objectiveBuffer.get(indx * 2).getId()), player.getNickname());
        }
    }

    /**
     * if the turn palyer disconnects before choosing the starter card face, the face side is automatically played
     * @param player the disconnected player
     */
    public void recoveryStarterCard(Player player){
        if(!areCoordinatesPresent(player.getPlacementArea().freePositions(), new Coordinates(0,0))){
            return;
        }
        modelController.playStarterCard(true, player.getNickname());
    }

    /**
     * if the turn palyer disconnects and has palced a card but still has to draw, he will draw from the first available deck,
     * if no deck is available he will then draw a card from the open deck. The order of the decks is:
     * (1)resource deck, (2) gold deck, (3) open resources, (4) open gold
     * @param player the disconnected player
     */
    public void recoveryDrawing(Player player){
        if(!player.equals(turnPlayer))
            return;
        if (getResourceDeck().getSize() > 0){
            modelController.drawCard(4);
        }else if (getGoldDeck().getSize() > 0){
            modelController.drawCard(1);
        }else if (!getOpenResources().isEmpty()){
            int i;
            if(getOpenResources().get(0) != null)
                i = 0;
            else
                i = 1;
            modelController.drawCard(5+i);
        }else{
            int i;
            if(getOpenGold().get(0) != null)
                i = 0;
            else
                i = 1;
            modelController.drawCard(2+i);
        }
    }

    /**
     * if the turn palyer disconnects and has not palced a card yet, the turn passes to the next player
     * @param player the disconnected player
     */
    public void recoverPlacement(Player player){
        if(!player.equals(turnPlayer))
            return;
        nextPlayer();
        playingTurn();
        setTurnState(TurnState.PLACING_CARD_SELECTION);
    }

    private boolean areCoordinatesPresent(List<Coordinates> list, Coordinates coordinates){
        for(Coordinates c : list)
            if(c.equals(coordinates))
                return true;
        return false;
    }
}
