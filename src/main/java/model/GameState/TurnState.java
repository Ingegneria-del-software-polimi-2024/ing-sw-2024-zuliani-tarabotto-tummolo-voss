package model.GameState;

import Chat.MessagesFromClient.ChatMessage;
import Client.UI.UI;
import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.Messages.MessagesFromClient.toModelController.*;
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
     * Represents the game initialization state.
     */
    GAME_INITIALIZATION{

        /**
         * Controls the message from the client.
         * @param message The message from the client.
         * @return true if the message is a ChooseSecreteObjMessage, ReadyToPlayMessage, QuitGameMessage, DisconnectionMessage, I_WantToReconnectMessage, or ChatMessage.
         */
        @Override
        public boolean controlMessage(MessageFromClient message) {
            return (message instanceof ChooseSecreteObjMessage ||
                    message instanceof ReadyToPlayMessage      ||
                    message instanceof QuitGameMessage         ||
                    message instanceof DisconnectionMessage    ||
                    message instanceof I_WantToReconnectMessage||
                    message instanceof ChatMessage);
        }

        /**
         * Displays the UI.
         * @param ui The user interface to display.
         */
        @Override
        public void display(UI ui){
            ui.displayInitialization();
        }

        /**
         * Recovers the disconnection.
         * @param gameState The current game state.
         * @param disconnectedPlayer The player who got disconnected.
         */
        @Override
        public void recoverDisconnection(GameState gameState, Player disconnectedPlayer) {
            gameState.recoveryStarterCard(disconnectedPlayer);
        }

        /**
         * Handles the reconnection of a player.
         * @param gameState The current game state.
         * @param reconnectingPlayer The player who is reconnecting.
         */
        @Override
        public void reconnect(GameState gameState, Player reconnectingPlayer) {
            gameState.inizializationReconnection(reconnectingPlayer);
        }
    },


    /**
     * Represents the starter card selection state.
     */
    STARTER_CARD_SELECTION{

        /**
         * Controls the message from the client.
         * @param msg The message from the client.
         * @return true if the message is a SelectStarterCardMessage, PlayStarterCardMessage, QuitGameMessage, DisconnectionMessage, I_WantToReconnectMessage, or ChatMessage.
         */
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof PlayStarterCardMessage   ||
                    msg instanceof QuitGameMessage          ||
                    msg instanceof DisconnectionMessage     ||
                    msg instanceof I_WantToReconnectMessage ||
                    msg instanceof ChatMessage);
        }

        /**
         * Displays the UI.
         * @param ui The user interface to display.
         */
        @Override
        public void display(UI ui){
            ui.displayStarterCardSelection();
        }

        /**
         * Recovers the disconnection.
         * @param gameState The current game state.
         * @param disconnectedPlayer The player who got disconnected.
         */
        @Override
        public void recoverDisconnection(GameState gameState, Player disconnectedPlayer) {
            gameState.recoveryStarterCard(disconnectedPlayer);
        }

        /**
         * Handles the reconnection of a player.
         * @param gameState The current game state.
         * @param reconnectingPlayer The player who is reconnecting.
         */
        @Override
        public void reconnect(GameState gameState, Player reconnectingPlayer) {
            gameState.starterCardReconnection(reconnectingPlayer);
        }
    },


    /**
     * Represents the objective selection state.
     */
    OBJECTIVE_SELECTION{

        /**
         * Controls the message from the client.
         * @param msg The message from the client.
         * @return true if the message is a ChooseSecreteObjMessage, QuitGameMessage, DisconnectionMessage, I_WantToReconnectMessage, or ChatMessage.
         */
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof ChooseSecreteObjMessage ||
                    msg instanceof QuitGameMessage         ||
                    msg instanceof DisconnectionMessage    ||
                    msg instanceof I_WantToReconnectMessage||
                    msg instanceof ChatMessage);
        }

        /**
         * Displays the UI.
         * @param ui The user interface to display.
         */
        @Override
        public void display(UI ui){
            ui.displayObjectiveSelection();
        }

        /**
         * Recovers the disconnection.
         * @param gameState The current game state.
         * @param disconnectedPlayer The player who got disconnected.
         */
        @Override
        public void recoverDisconnection(GameState gameState, Player disconnectedPlayer) {
            gameState.recoveryObjectiveChoice(disconnectedPlayer);
        }

        /**
         * Handles the reconnection of a player.
         * @param gameState The current game state.
         * @param reconnectingPlayer The player who is reconnecting.
         */
        public void reconnect(GameState gameState, Player reconnectingPlayer){
            gameState.objectiveCardReconnection(reconnectingPlayer);
        }

    },


    /**
     * Represents the placing card selection state.
     */
    PLACING_CARD_SELECTION{

        /**
         * Controls the message from the client.
         * @param msg The message from the client.
         * @return true if the message is a PlayCardMessage, QuitGameMessage, DisconnectionMessage, I_WantToReconnectMessage, or ChatMessage.
         */
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof PlayCardMessage         ||
                    msg instanceof QuitGameMessage         ||
                    msg instanceof DisconnectionMessage    ||
                    msg instanceof I_WantToReconnectMessage||
                    msg instanceof ChatMessage);
        }

        /**
         * Displays the UI.
         * @param ui The user interface to display.
         */
        @Override
        public void display(UI ui){
            ui.displayPlacingCard();
        }

        /**
         * Recovers the disconnection.
         * @param gameState The current game state.
         * @param disconnectedPlayer The player who got disconnected.
         */
        @Override
        public void recoverDisconnection(GameState gameState, Player disconnectedPlayer) {
            gameState.recoverPlacement(disconnectedPlayer);
        }
    },


    /**
     * Represents the card drawing state.
     */
    CARD_DRAWING{

        /**
         * Controls the message from the client.
         * @param msg The message from the client.
         * @return true if the message is a DrawCardMessage, QuitGameMessage, DisconnectionMessage, I_WantToReconnectMessage, or ChatMessage.
         */
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof DrawCardMessage         ||
                    msg instanceof QuitGameMessage         ||
                    msg instanceof DisconnectionMessage    ||
                    msg instanceof I_WantToReconnectMessage||
                    msg instanceof ChatMessage);
        }

        /**
         * Displays the UI.
         * @param ui The user interface to display.
         */
        @Override
        public void display(UI ui){
            ui.displayCardDrawing();
        }

        /**
         * Recovers the disconnection.
         * @param gameState The current game state.
         * @param disconnectedPlayer The player who got disconnected.
         */
        @Override
        public void recoverDisconnection(GameState gameState, Player disconnectedPlayer) {
            gameState.recoveryDrawing(disconnectedPlayer);
        }
    },


    /**
     * Represents the end game state.
     */
    END_GAME{

        /**
         * Controls the message from the client.
         * @param msg The message from the client.
         * @return true if the message is a QuitGameMessage, DisconnectionMessage, I_WantToReconnectMessage, or ChatMessage.
         */
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof QuitGameMessage          ||
                    msg instanceof DisconnectionMessage     ||
                    msg instanceof I_WantToReconnectMessage ||
                    msg instanceof ChatMessage);
        }

        /**
         * Displays the UI.
         * @param ui The user interface to display.
         */
        @Override
        public void display( UI ui){
            ui.displayEndGame();
        }
    };

    /**
     * Controls the message from the client.
     * @param message The message from the client.
     * @return false by default, should be overridden in each state.
     */
    public boolean controlMessage(MessageFromClient message){return false;}

    /**
     * Displays the UI.
     * @param ui The user interface to display.
     */
    public void display(UI ui){}

    /**
     * Recovers the disconnection.
     * @param gameState The current game state.
     * @param disconnectedPlayer The player who got disconnected.
     */
    public void recoverDisconnection(GameState gameState, Player disconnectedPlayer){}

    /**
     * Handles the reconnection of a player.
     * @param gameState The current game state.
     * @param reconnectingPlayer The player who is reconnecting.
     */
    public void reconnect(GameState gameState, Player reconnectingPlayer){}

    /**
     * Checks if the current state is a starting state.
     * @return true if the current state is either GAME_INITIALIZATION, OBJECTIVE_SELECTION, or STARTER_CARD_SELECTION.
     */
    public boolean isStartingState(){
        return this.equals(GAME_INITIALIZATION) || this.equals(OBJECTIVE_SELECTION) || this.equals(STARTER_CARD_SELECTION);
    }

}
