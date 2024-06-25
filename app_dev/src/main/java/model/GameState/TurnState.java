package model.GameState;

import Chat.MessagesFromClient.ChatMessage;
import Client.UI.UI;
import Server.ModelController;
import Server.ModelListener;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.Messages.MessagesFromClient.toModelController.*;
import SharedWebInterfaces.Messages.MessagesFromServer.EndGameMessage;
import model.player.Player;

/**
 * a finite state machine that keeps track of the states occuring during the turn of a player,
 * the states are divided in:
 * starter card selection stage
 * colour selection stage
 * card dealing stage
 * normal stage (card selection, coordinates selection, drawing a card)
 * objective calculation stage
 */
public enum TurnState {
    /**
     * The Game initialization.
     */
    GAME_INITIALIZATION{
        @Override
        public boolean controlMessage(MessageFromClient message) {
            return (message instanceof ChooseSecreteObjMessage ||
                    message instanceof ReadyToPlayMessage      ||
                    message instanceof QuitGameMessage         ||
                    message instanceof DisconnectionMessage    ||
                    message instanceof I_WantToReconnectMessage||
                    message instanceof ChatMessage);
        }

        @Override
        public void display(UI ui){
            ui.displayInitialization();
        }

        @Override
        public void recoverDisconnection(GameState gameState, Player disconnectedPlayer) {
            gameState.recoveryStarterCard(disconnectedPlayer);
        }

        @Override
        public void reconnect(GameState gameState, Player reconnectingPlayer) {
            gameState.inizializationReconnection(reconnectingPlayer);
        }
    },


    /**
     * The Starter card selection.
     */
    STARTER_CARD_SELECTION{
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof SelectStarterCardMessage ||
                    msg instanceof PlayStarterCardMessage   ||
                    msg instanceof QuitGameMessage          ||
                    msg instanceof DisconnectionMessage     ||
                    msg instanceof I_WantToReconnectMessage ||
                    msg instanceof ChatMessage);
        }
        @Override
        public void display(UI ui){
            ui.displayStarterCardSelection();
        }

        @Override
        public void recoverDisconnection(GameState gameState, Player disconnectedPlayer) {
            gameState.recoveryStarterCard(disconnectedPlayer);
        }

        @Override
        public void reconnect(GameState gameState, Player reconnectingPlayer) {
            gameState.starterCardReconnection(reconnectingPlayer);
        }
    },


    /**
     * The Objective selection.
     */
    OBJECTIVE_SELECTION{
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof ChooseSecreteObjMessage ||
                    msg instanceof QuitGameMessage         ||
                    msg instanceof DisconnectionMessage    ||
                    msg instanceof I_WantToReconnectMessage||
                    msg instanceof ChatMessage);
        }

        @Override
        public void display(UI ui){
            ui.displayObjectiveSelection();
        }

        @Override
        public void recoverDisconnection(GameState gameState, Player disconnectedPlayer) {
            gameState.recoveryObjectiveChoice(disconnectedPlayer);
        }

        public void reconnect(GameState gameState, Player reconnectingPlayer){
            gameState.objectiveCardReconnection(reconnectingPlayer);
        }

    },


    /**
     * The Placing card selection.
     */
    PLACING_CARD_SELECTION{
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof PlayCardMessage         ||
                    msg instanceof QuitGameMessage         ||
                    msg instanceof DisconnectionMessage    ||
                    msg instanceof I_WantToReconnectMessage||
                    msg instanceof ChatMessage);
        }

        @Override
        public void display(UI ui){
            ui.displayPlacingCard();
        }

        @Override
        public void recoverDisconnection(GameState gameState, Player disconnectedPlayer) {
            gameState.recoverPlacement(disconnectedPlayer);
        }
    },


    /**
     * The Card drawing.
     */
    CARD_DRAWING{
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof DrawCardMessage         ||
                    msg instanceof QuitGameMessage         ||
                    msg instanceof DisconnectionMessage    ||
                    msg instanceof I_WantToReconnectMessage||
                    msg instanceof ChatMessage);
        }

        @Override
        public void display(UI ui){
            ui.displayCardDrawing();
        }

        @Override
        public void recoverDisconnection(GameState gameState, Player disconnectedPlayer) {
            gameState.recoveryDrawing(disconnectedPlayer);
        }
    },


    /**
     * The End game.
     */
    END_GAME{
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof QuitGameMessage          ||
                    msg instanceof DisconnectionMessage     ||
                    msg instanceof I_WantToReconnectMessage ||
                    msg instanceof ChatMessage);
        }

        @Override
        public void display( UI ui){
            ui.displayEndGame();
        }
    };



//    /**
//     * @throws UnsupportedOperationException when the next state is in a different stage
//     */
//    public void nextState() throws UnsupportedOperationException{
//        switch(this){
//            case STARTER_CARD_SELECTION:
//                return;
//            case OBJECTIVE_SELECTION:
//                return;
//            case PLACING_CARD_SELECTION:
//                return;
//            case CARD_DRAWING:
//                return;
//            case END_GAME:
//                return;
//        }
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * @throws UnsupportedOperationException when trying to change stage not being in the final state of the present stage
//     */
//    public void nextStage() throws UnsupportedOperationException{
//        switch(this){
//            case STARTER_CARD_SELECTION:
//                return;
//            case OBJECTIVE_SELECTION:
//                return;
//            case PLACING_CARD_SELECTION:
//                return;
//            case CARD_DRAWING:
//                return;
//            case END_GAME:
//                return;
//        }
//        throw new UnsupportedOperationException();
//    }

    /**
     * Control message boolean.
     *
     * @param message the message
     * @return the boolean
     */
    public boolean controlMessage(MessageFromClient message){return false;}

    /**
     * Display.
     *
     * @param ui the ui
     */
    public void display(UI ui){}

    /**
     * Recover disconnection.
     *
     * @param gameState          the game state
     * @param disconnectedPlayer the disconnected player
     */
    public void recoverDisconnection(GameState gameState, Player disconnectedPlayer){}

    /**
     * Reconnect.
     *
     * @param gameState          the game state
     * @param reconnectingPlayer the reconnecting player
     */
    public void reconnect(GameState gameState, Player reconnectingPlayer){}

    /**
     * Is starting state boolean.
     *
     * @return the boolean
     */
    public boolean isStartingState(){
        return this.equals(GAME_INITIALIZATION) || this.equals(OBJECTIVE_SELECTION) || this.equals(STARTER_CARD_SELECTION);
    }
}
