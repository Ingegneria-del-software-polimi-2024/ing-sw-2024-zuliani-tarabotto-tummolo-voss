package model.GameState;

import Client.UI.UI;
import Client.View.ViewAPI;
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
        @Override
        public void display(ViewAPI view, UI ui){
            ui.initializationDisplay(view);
        }

    },
    STARTER_CARD_SELECTION{
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof SelectStarterCardMesage);
        }
        @Override
        public void display(ViewAPI view, UI ui){
            ui.starterSelectionDisplay(view);
        }
    },
    OBJECTIVE_SELECTION{
        //TODO change instanceof type
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof SelectStarterCardMesage);
        }

        @Override
        public void display(ViewAPI view, UI ui){
            ui.objectiveSelectionDisplay(view);
        }
    },
    PLACING_CARD_SELECTION{
        //TODO change instanceof type
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof SelectStarterCardMesage);
        }

        @Override
        public void display(ViewAPI view, UI ui){
            ui.placingCardDisplay(view);
        }
    },
    CARD_DRAWING{
        //TODO change instanceof type
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof SelectStarterCardMesage);
        }

        @Override
        public void display(ViewAPI view, UI ui){
            ui.cardDrawingDisplay(view);
        }
    },
    CALCULATE_OBJECTIVES{
        //TODO change instanceof type
        public boolean controlMessage(MessageFromClient msg){
            return (msg instanceof SelectStarterCardMesage);
        }

        @Override
        public void display(ViewAPI view, UI ui){
            ui.calculateObjectivesDisplay(view);
        }
    };

    /**
     * @throws UnsupportedOperationException when the next state is in a different stage
     */
    public void nextState() throws UnsupportedOperationException{
        switch(this){
            case STARTER_CARD_SELECTION:
                return;
            case OBJECTIVE_SELECTION:
                return;
            case PLACING_CARD_SELECTION:
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
            case OBJECTIVE_SELECTION:
                return;
            case PLACING_CARD_SELECTION:
                return;
            case CARD_DRAWING:
                return;
            case CALCULATE_OBJECTIVES:
                return;
        }
        throw new UnsupportedOperationException();
    }

    public boolean controlMessage(MessageFromClient message){return false;}

    public void display(ViewAPI view, UI ui){}
}
