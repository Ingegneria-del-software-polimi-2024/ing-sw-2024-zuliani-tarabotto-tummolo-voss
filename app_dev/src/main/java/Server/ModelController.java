package Server;
import Server.Web.Game.ServerAPI_GO;
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
    private ArrayList<String> playersNicknames;
    private String gameId;
    private int cont = 0;
    private String initialPlayer;
    private boolean lastRound = false;
    private ServerAPI_GO send;
    private int readyPlayers;
    private Room room;



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
    }

    /**
     * decks and open cards are created, also each player is given his initial hand of cards
     * (the view will display it only later)
     */
    @Override
    public void initializeGameState(){

        gameState = new GameState(playersNicknames, gameId, new ModelListener(send));
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
        //IF THIS FUNCTION GETS CALLED A NUMBER OF TIMES EQUALS TO THE NUMBER OF PLAYERS THEN THE STATE OF THE GAME IS CHANGED
        if(cont == playersNicknames.size() - 1){
            distributeSecretObjectives();
            cont = 0;
            return;
        }
        cont++;
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
        if(!playersNicknames.contains(player))
            return;

        gameState.setPlayerSecretObjective(cardId, player);

        if(cont == playersNicknames.size() - 1){

            //Now the first round will be played
            gameState.playingTurn();
            gameState.setTurnState(TurnState.PLACING_CARD_SELECTION);
            return;
        }
        cont++;
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
        if(!found)
            gameState.wrongCardRoutine(new CantPlaceCardException(new Coordinates(x,y)));

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
            playNewTurn();
        } else {
            if(!lastRound){
                cont = 0;
                lastRound = true;
            }

            if(cont == playersNicknames.size()){
                gameState.calculateFinalPoints();
                gameState.setTurnState(TurnState.END_GAME);
                return;
            }else{playNewTurn();}
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

    public boolean checkMessage(MessageFromClient message){return gameState.checkMessage(message);}

    public void endGame(){
        gameState.calculateFinalPoints();
        gameState.setTurnState(TurnState.END_GAME);
    }



    public void HandleDisconnection() {
        System.out.println("DISCONNECTION DETECTED");
        room.handleADetectedDisconnection();

    }

    public void quitGame(String playerID){
        //the player must be added to the "unavailable" list, some kind of control MUST be done
        //TODO control
        gameState.quitGame(playerID);
    }
}
