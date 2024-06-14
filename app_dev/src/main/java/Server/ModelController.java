package Server;
import Server.Web.Game.ServerAPI_GO;
import Server.Web.Lobby.HeartBeatSettings;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import model.Exceptions.CantPlaceCardException;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;
import model.GameState.GameState;
import model.GameState.TurnState;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;
import Server.Web.Lobby.Room;
import java.util.ArrayList;
import java.util.Objects;


/*
1 - MESCOLARE MAZZI, MOSTRARE CARTE OPEN
2 - IN CONTEMPORANEA: OGNI GIOCATORE RICEVE LA CARTA INIZIALE E SCEGLIE LA FACCIA CON CUI PIAZZARLA
3 - OGNI GIOCATORE PESCA DUE CARTE RISORSA E UNA CARTA ORO
4 - VENGONO RIVELATE LE DUE CARTE OBBIETTIVO COMUNI
5 - IN CONTEMPORANEA: OGNI GIOCATORE SCEGLIE IL SUO OBBIETTIVO TRA DUE CARTE
6 - IL GIOCO INIZIA
 */

public class ModelController implements ServerControllerInterface {
    private GameState gameState;
    private final ArrayList<String> playersNicknames;
    private final String gameId;
    private int cont = 0;
    private String initialPlayer;
    private boolean lastRound = false;
    private final ServerAPI_GO send;
    private int readyPlayers;
    private final Room room;
    private boolean finished = false;
    private boolean gameEnded = false;



    /**
     * class constructor
     * @param playersNicknames nicknames of the players
     * @param gameId id of the game
     */
    public ModelController(ArrayList<String> playersNicknames, String gameId, ServerAPI_GO send, Room room){
        System.out.println("model controller created");
        this.playersNicknames = playersNicknames;
        this.gameId = gameId;
        this.send = send;
        this.readyPlayers = 0;
        this.room = room;
    }

    /**
     * decks and open cards are created, also each player is given his initial hand of cards
     * (the view will display it only later)
     */
    @Override
    public void initializeGameState(){

        gameState = new GameState(playersNicknames, gameId, new ModelListener(send), this);
        initialPlayer = gameState.getTurnPlayer().getNickname();
        gameState.setTurnState(TurnState.GAME_INITIALIZATION);
    }


    /**
     * each player once connected and inside a game sends a message(ReadyToPlayMessage) that calls this method:
     * after all players sent this message the game finally starts with the starterCards distribution
     */
    @Override
    public void setPlayerReady(){
        readyPlayers ++;
        if(readyPlayers == playersNicknames.size()){
            gameState.setTurnState(TurnState.STARTER_CARD_SELECTION);
        }
    }


    /**
     * the controller places the starter card for the player with the specified face.
     * A counter checks if all players placed their cards and then updates the State of the game
     * @param face the face of the starter card
     * @param player the nickname of the player
     */
    @Override
    public void playStarterCard(boolean face, String player){
        if(!playersNicknames.contains(player))
            return;
        gameState.setStartingCardFace(face,player);
        gameState.playStarterCard(player);
        cont++;
        //IF THIS FUNCTION GETS CALLED A NUMBER OF TIMES EQUALS TO THE NUMBER OF PLAYERS THEN THE STATE OF THE GAME IS CHANGED
        if(cont == playersNicknames.size()){
            cont = 0;
            distributeSecretObjectives();
        }
    }


    /**
     * when the state is changed to OBJECTIVE_SELECTION the UI is able to see the commonObjectives and
     * the player can choose between the two secretObjective cards that he is given
     */
    private void distributeSecretObjectives(){
        gameState.distributeSecretOjectives();
        gameState.setTurnState(TurnState.OBJECTIVE_SELECTION);
    }



    /**
     * sets the desired card as secretObjective for the player.
     * A counter checks if all players chose their secretObjective
     * @param cardId the id of the selected card
     * @param player nickname of the player
     */
    @Override
    public void chooseSecretObjective(String cardId, String player) {
        cont++;
        if(!playersNicknames.contains(player))
            return;
        gameState.setPlayerSecretObjective(cardId, player);
        if(cont == playersNicknames.size()){
            //Now the first round will be played
            gameState.playingTurn();
            gameState.setTurnState(TurnState.PLACING_CARD_SELECTION);
            return;
        }
    }


    /**
     * all GameState attributes regarding the placement of a card are set and the card is placed
     * @param cardId the id of the card we want to play
     * @param x the x value of the coordinates
     * @param y the y value of the coordinates
     * @param faceSide the side we want to play the card
     */
    @Override
    public void playCard(int cardId, int x , int y, Boolean faceSide){
        boolean found = false;
        for(PlayableCard c : gameState.getTurnPlayer().getPlayingHand()){
            if(c.getId() == cardId) {
                gameState.setSelectedHandCard(c);
                found = true;
            }
        }
        if(!found) {
            gameState.wrongCardRoutine(new CantPlaceCardException(new Coordinates(x, y)));
            return;
        }
        gameState.setSelectedCardFace(faceSide);
        gameState.setSelectedCoordinates(new Coordinates(x,y));
        try{
            gameState.playCard();
        }catch (CantPlaceCardException e){
            return;
        }
        gameState.setTurnState(TurnState.CARD_DRAWING);
    }

    
    /**
     * we draw a card from the specified card source
     * Also since this is the last performed action in a turn, we check if this is the last turn of the game
     * @param cardSource the deck to draw from: 1 if it is goldDeck, 2 if it is openGold with index 0,
     *                   3 if it is openGold with index 1, 4 if it is resourceDeck, 5 if it is openResource with index 0,
     *                   6 if it is openResource with index 1
     */
    @Override
    public void drawCard(int cardSource) throws RuntimeException{
        switch (cardSource) {
            case 1:
                gameState.drawCardGoldDeck();
                break;
            case 2:
                gameState.drawCardOpenGold(0);
                break;
            case 3:
                gameState.drawCardOpenGold(1);
                break;
            case 4:
                gameState.drawCardResourcesDeck();
                break;
            case 5:
                gameState.drawCardOpenResources(0);
                break;
            case 6:
                gameState.drawCardOpenResources(1);
                break;
            default:
                gameState.drawCardGoldDeck();
        }

        gameState.nextPlayer();

        //if it's not the last turn:
        // - turnPlayer is updated
        // - the state is changed to PLACING_CARD_SELECTION
        // - new turnPlayer gets notified about which cards he can place(and where) by calling playingTurn method
        //TODO: optimize this control
        if(!gameState.getLastTurn() && !lastRound){

            playNewTurn();
        }else if(!Objects.equals(gameState.getTurnPlayer().getNickname(), initialPlayer) && !lastRound){
//            gameState.nextPlayer();
            playNewTurn();
        } else {
            if(!lastRound){
                cont = 0;
                lastRound = true;
            }

            if(cont == playersNicknames.size()){
                endGame();
                return;
            }else{
//                gameState.nextPlayer();
                playNewTurn();
            }
            cont ++;
        }
    }

    /**
     * we communicate GameState that another turn will be played
     */
    private void playNewTurn(){
        gameState.playingTurn();
        gameState.setTurnState(TurnState.PLACING_CARD_SELECTION);
    }

    public boolean checkMessage(MessageFromClient message){
        if(gameState != null)
            return gameState.checkMessage(message);
        return true;
    }

    public void endGame(){
        gameEnded = true;
        gameState.calculateFinalPoints();
        gameState.setTurnState(TurnState.END_GAME);
        room.ended();
    }

    /**
     * set a player in an active state
     * @param playerName the name of the player to be set active
     */
    public void setPlayerActive(String playerName){
        gameState.setPlayerActive(playersNicknames.indexOf(playerName));
    }

    /**
     * set a player in an inactive state
     * @param playerName the name of the player to be set active
     * @return true if the game was closed after the player left the match, false if the match continued
     */
    public void setPlayerInactive(String playerName){
        gameState.setPlayerInactive(playersNicknames.indexOf(playerName));
        //control if all the players except one are disconnected
        int iterations = 0;
        do {
            int disconnectedPlayersNumber = 0;
            for (int i = 0; i < playersNicknames.size(); i++) {
                if (!gameState.getPlayer(i).isActive()) {
                    disconnectedPlayersNumber++;
                }
            }
            //in case not all the players except one are disconnected, we must continue the game
            if(disconnectedPlayersNumber < playersNicknames.size() - 1) {
                return;
            }
            iterations++;
            try {
                Thread.sleep(HeartBeatSettings.timerB4ClosingGame);
            } catch (InterruptedException e) {
                e.printStackTrace();
                //TODO handle the exception
            }
        }while(iterations < HeartBeatSettings.iterationsNumber);

        //after some time of checking we must close the game
        if(!gameEnded)
            endGame();
        finished = true;
    }

    public void handleDisconnection() {
        System.out.println("DISCONNECTION DETECTED");
        room.handleADetectedDisconnection();
    }

    public void quitGame(String playerID){
        gameState.quitGame(playerID);
        room.quitGame(playerID);
    }

    public void reconnect(String playerID) {
        System.out.println("RECONNECTION of player: " + playerID);
        gameState.reconnect(playerID);
        System.out.println("RECONNECTION COMPLETE");
    }
}
