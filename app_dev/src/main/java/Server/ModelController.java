package Server;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import model.Exceptions.EmptyCardSourceException;
import SharedWebInterfaces.SharedInterfaces.ServerControllerInterface;
import model.GameState.GameState;
import model.GameState.TurnState;
import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;

import java.util.ArrayList;


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
    private int roundCounter = 0;
    private String initialPlayer;
    private boolean lastRound = false;

    /**
     * class constructor
     * @param playersNicknames nicknames of the players
     * @param gameId id of the game
     */
    public ModelController(ArrayList<String> playersNicknames, String gameId){
        this.playersNicknames = playersNicknames;
        this.gameId = gameId;
    }

    /**
     * decks and open cards are created, also each player is given his initial hand of cards
     * (the view will display it only later)
     */
    @Override
    public void initializeGameState(){
        gameState = new GameState(playersNicknames, gameId);
        gameState.setTurnState(TurnState.GAME_INITIALIZATION);
        initialPlayer = gameState.getTurnPlayer().getNickname();
        gameState.setTurnState(TurnState.STARTER_CARD_SELECTION);
    }


    /**
     * the controller places the starter card for the player with the specified face.
     * A counter checks if all players placed their cards and then updates the State of the game
     * @param face the face of the starter card
     * @param player the nickname of the player
     */
    @Override
    public void playStarterCard(boolean face, String player){
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
        gameState.setTurnState(TurnState.OBJECTIVE_SELECTION);
        gameState.distributeSecretOjectives();
    }



    /**
     * sets the desired card as secretObjective for the player.
     * A counter checks if all players chose their secretObjective
     * @param cardId the id of the selected card
     * @param player nickname of the player
     */
    @Override
    public void chooseSecretObjective(String cardId, String player) {
        gameState.setPlayerSecretObjective(cardId, player);
        if(cont == playersNicknames.size() - 1){
            gameState.setTurnState(TurnState.PLACING_CARD_SELECTION);
            //Now the first round will be played
            gameState.playingTurn();
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
        for(PlayableCard c : gameState.getTurnPlayer().getPlayingHand()){
            if(c.getId() == cardId) { gameState.setSelectedHandCard(c); }
        }

        gameState.setSelectedCardFace(faceSide);
        gameState.setSelectedCoordinates(new Coordinates(x,y));
        gameState.playCard();
        gameState.setTurnState(TurnState.CARD_DRAWING);
    }

    
    /**
     * we draw a card from the specified card source
     * Also since this is the last performed action in a turn, we check if this is the last turn of the game
     * @param cardSource
     */
    @Override
    public void drawCard(int cardSource){
        try{
            switch (cardSource) {
                case 1:
                    gameState.drawCardGoldDeck();
                    break;
                case 2:
                    gameState.drawCardResourcesDeck();
                    break;
                case 3:
                    gameState.drawCardOpenGold(0);
                    break;
                case 4:
                    gameState.drawCardOpenGold(1);
                    break;
                case 5:
                    gameState.drawCardOpenResources(0);
                    break;
                case 6:
                    gameState.drawCardOpenResources(1);
                    break;
            }
        }
        catch (EmptyCardSourceException ex) {
            System.out.println(ex.getMessage());
            System.out.println("choose another source to draw a card from");
            //TODO: handle the exception
            //callDrawFunction(new Scanner(System.in).nextInt());
        }

        gameState.nextPlayer();

        //if it's not the last turn:
        // - turnPlayer is updated
        // - the state is changed to PLACING_CARD_SELECTION
        // - new turnPlayer gets notified about which cards he can place(and where) by calling playingTurn method
        //TODO: optimize this control
        if(!gameState.getLastTurn() && !lastRound){
            playNewTurn();
        }else if(gameState.getTurnPlayer().getNickname() != initialPlayer && !lastRound){
            roundCounter++;
            playNewTurn();
        }else if(roundCounter == playersNicknames.size() - 1 && !lastRound){
            gameState.calculateFinalPoints();
            gameState.setTurnState(TurnState.CALCULATE_OBJECTIVES);
        }else{
            if(!lastRound){
                cont = 0;
                lastRound = true;
            }
            playNewTurn();
            if(cont == playersNicknames.size() -1 ){
                gameState.calculateFinalPoints();
                gameState.setTurnState(TurnState.CALCULATE_OBJECTIVES);
                return;
            };
            cont ++;
        }
    }

    /**
     * we communicate GameState that another turn will be played
     */
    private void playNewTurn(){
        gameState.setTurnState(TurnState.PLACING_CARD_SELECTION);
        gameState.playingTurn();
    }

    public boolean checkMessage(MessageFromClient message){return gameState.checkMessage(message);}
}
