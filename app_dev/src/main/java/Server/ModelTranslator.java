package Server;
import Chat.ChatHistory;
import Chat.MessagesFromServer.ChatHistoryMessage;
import Chat.MessagesFromClient.ChatMessage;
import Chat.MessagesFromServer.ChatUpdateMessage;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/*
1 - MESCOLARE MAZZI, MOSTRARE CARTE OPEN
2 - IN CONTEMPORANEA: OGNI GIOCATORE RICEVE LA CARTA INIZIALE E SCEGLIE LA FACCIA CON CUI PIAZZARLA
3 - OGNI GIOCATORE PESCA DUE CARTE RISORSA E UNA CARTA ORO
4 - VENGONO RIVELATE LE DUE CARTE OBBIETTIVO COMUNI
5 - IN CONTEMPORANEA: OGNI GIOCATORE SCEGLIE IL SUO OBBIETTIVO TRA DUE CARTE
6 - IL GIOCO INIZIA
 */

/**
 * The translator between the controller and the web structure
 */
public class ModelTranslator implements ServerControllerInterface {
    /**
     * The Game state controller.
     */
    private GameState gameState;
    /**
     * The Players' nicknames.
     */
    private final ArrayList<String> playersNicknames;
    /**
     * The Game name, corresponds to the room name.
     */
    private final String gameId;
    /**
     * counter used to check if all players have placed their starter card and secret objectives
     */
    private int cont = 0;
    /**
     * The first player to play.
     */
    private String initialPlayer;
    /**
     * boolean to check if the last round is being played
     */
    private boolean lastRound = false;
    /**
     * The interface to send messages to the client.
     */
    private final ServerAPI_GO send;
    /**
     * The number of players that are ready to play
     */
    private int readyPlayers;
    /**
     * The room in which the game is played
     */
    private final Room room;
    /**
     * boolean to check if the game is ended
     */
    private boolean gameEnded = false;
    /**
     * The chat history of the game
     */
    private ChatHistory chatHistory;
    /**
     * The model listener, used to send messages to the client
     */
    private ModelListener modelListener;
    /**
     * The set of disconnected players
     */
    private Set<String> disconnectedPlayers;

    /**
     * class constructor
     *
     * @param playersNicknames nicknames of the players
     * @param gameId           id of the game
     * @param send             the send
     * @param room             the room
     * @param disconnected     the disconnected
     */
    public ModelTranslator(ArrayList<String> playersNicknames, String gameId, ServerAPI_GO send, Room room, Set<String> disconnected){
        System.out.println("model controller created");
        this.playersNicknames = playersNicknames;
        this.gameId = gameId;
        this.send = send;
        this.readyPlayers = 0;
        this.room = room;
        this.disconnectedPlayers = disconnected;
    }

    /**
     * Decks and open cards are created, also each player is given his initial hand of cards
     * (the view will display it only later)
     */
    @Override
    public void initializeGameState(){
        modelListener = new ModelListener(send);
        gameState = new GameState(playersNicknames, gameId, modelListener, this, disconnectedPlayers);
        initialPlayer = gameState.getTurnPlayer().getNickname();
        gameState.setTurnState(TurnState.GAME_INITIALIZATION);
        chatHistory = new ChatHistory();
    }


    /**
     * Each player once connected and inside a game sends a message(ReadyToPlayMessage) that calls this method:
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
     * The controller places the starter card for the player with the specified face.
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
     * We communicate GameState that another turn will be played
     */
    private void playNewTurn(){
        gameState.playingTurn();
        gameState.setTurnState(TurnState.PLACING_CARD_SELECTION);
    }

    /**
     *
     * @param message the message to be checked
     * @return true if the message is pertinent to the current turn state, false otherwise
     */
    public boolean checkMessage(MessageFromClient message){
        if(gameState != null)
            return gameState.checkMessage(message);
        return true;
    }

    /**
     * Ends the game.
     */
    public void endGame(){
        gameEnded = true;
        gameState.calculateFinalPoints();
        gameState.setTurnState(TurnState.END_GAME);
        room.ended();
    }

    /**
     * Sets a player in an active state
     *
     * @param playerName the name of the player to be set active
     */
    public void setPlayerActive(String playerName){
        gameState.setPlayerActive(playersNicknames.indexOf(playerName));
    }

    /**
     * Sets a player in an inactive state
     * If all the players except one are disconnected, after waiting the time specified, the game is closed
     * Otherwise the game continues
     * If the player is the initial player, it must be adjusted
     * If the game is closed, the gameEnded boolean is set to true
     *
     * @param playerName the name of the player to be set active
     * @return true if the game was closed after the player left the match, false if the match continued
     */
    public void setPlayerInactive(String playerName){
        //if it is the initial player it must be adjusted
        if(playerName.equals(initialPlayer)){
            for(int i = 0; i<playersNicknames.size(); i++) {
                if(gameState.getPlayer(i).isActive())
                    initialPlayer = playersNicknames.get(i);
            }
        }

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
        gameEnded = true;
    }

    /**
     * Handles disconnection of a player.
     */
    public void handleDisconnection() {
        System.out.println("DISCONNECTION DETECTED");
        room.handleADetectedDisconnection();
    }

    /**
     * Allows a player to quit the game.
     *
     * @param playerID the player id
     */
    public void quitGame(String playerID){
        System.out.println("quitting method in gamestate");
        gameState.quitGame(playerID);
        System.out.println("quitting method in room");
        room.quitGame(playerID);
    }

    /**
     * Reconnects a player to the game.
     *
     * @param playerID the player id
     */
    public void reconnect(String playerID) {
        System.out.println("RECONNECTION of player: " + playerID);
        //if it was the first player to play, then it must be re-set as the first player (by disconnecting it was shifted
        //to the following)
        if(playerID.equals(playersNicknames.get(0)))
            initialPlayer = playerID;
        gameState.reconnect(playerID);
        //if a player reconnects we also automatically send him the chat history
        //modelListener.sendChatHistory(playerID, new ChatHistoryMessage(chatHistory.getHistory(), playerID));
        sendChatHistory(playerID);
        System.out.println("RECONNECTION COMPLETE");
    }

    ///////////////////////////////////////////////CHAT/////////////////////////////////////////////////////////////////

    /**
     * Enqueues a chat msg.
     *
     * @param message the message
     */
    public void enqChatMsg(ChatMessage message){
        Timestamp deliveryTime = chatHistory.add(message);
        if(message.getReceiver() == null) {
            modelListener.broadcastChatMessage(new ChatUpdateMessage(message));
        } else {
            modelListener.sendPrivateChatMessage(new ChatUpdateMessage(message), message.getReceiver());
            modelListener.sendPrivateChatMessage(new ChatUpdateMessage(message), message.getSender());
        }

    }

    /**
     * Sends the complete chat history to a player.
     *
     * @param player the player
     */
    public void sendChatHistory(String player){
        ArrayList<ChatMessage> history = chatHistory.getHistory();

        history = (ArrayList<ChatMessage>) history.stream()
                .filter(m -> (m.getReceiver() == null || m.getReceiver().equals(player) || m.getSender().equals(player)))
                .collect(Collectors.toList());

        for(ChatMessage msg : history){
            System.out.println(msg.getReceiver() + " receiver");
            System.out.println(msg.getSender() + " sender");
        }
        modelListener.sendChatHistory(player, new ChatHistoryMessage(history, player));
    }
}
