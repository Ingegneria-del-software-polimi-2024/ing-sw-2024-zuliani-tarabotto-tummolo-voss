package model.GameState;

import SharedWebInterfaces.Messages.MessagesFromClient.MessageFromClient;
import SharedWebInterfaces.Messages.MessagesFromClient.SelectStarterCardMesage;

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
    GAME_INITIALIZATION{

    },
    STARTER_CARD_SELECTION{
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof SelectStarterCardMesage);
        }
    },
    OBJECTIVE_SELECTION{
        //TODO change instanceof type
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof SelectStarterCardMesage);
        }
    },
    COLOUR_SELECTION,//TODO: ELIMINATE
    CARD_DEALING{
        //TODO change instanceof type
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof SelectStarterCardMesage);
        }
    },
    PLACING_CARD_SELECTION{
        //TODO change instanceof type
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof SelectStarterCardMesage);
        }
    },
    PLACING_POSITION_SELECTION, //TODO: ELIMINATE
    CARD_DRAWING{
        //TODO change instanceof type
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof SelectStarterCardMesage);
        }
    },
    CALCULATE_OBJECTIVES{
        //TODO change instanceof type
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof SelectStarterCardMesage);
        }
    };

    /**
     * @throws UnsupportedOperationException when the next state is in a different stage
     */
    public void nextState() throws UnsupportedOperationException{
        switch(this){
            case STARTER_CARD_SELECTION:
                return;
            case COLOUR_SELECTION:
                return;
            case CARD_DEALING:
                return;
            case OBJECTIVE_SELECTION:
                return;
            case PLACING_CARD_SELECTION:
                return;
            case PLACING_POSITION_SELECTION:
                return;
            case CARD_DRAWING:
                return;
            case CALCULATE_OBJECTIVES:
                return;
        }
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException when trying to change stage not being in the final state of the present stage
     */
    public void nextStage() throws UnsupportedOperationException{
        switch(this){
            case STARTER_CARD_SELECTION:
                return;
            case COLOUR_SELECTION:
                return;
            case CARD_DEALING:
                return;
            case OBJECTIVE_SELECTION:
                return;
            case PLACING_CARD_SELECTION:
            case PLACING_POSITION_SELECTION:
                throw new UnsupportedOperationException();
            case CARD_DRAWING:
                return;
            case CALCULATE_OBJECTIVES:
                return;
        }
        throw new UnsupportedOperationException();
    }

    public boolean controlMessage(MessageFromClient message){return false;}
}
