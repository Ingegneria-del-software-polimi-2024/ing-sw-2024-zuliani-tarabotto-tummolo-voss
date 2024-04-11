package model.GameState;

public enum TurnState {
    STARTER_CARD_SELECTION,
    COLOUR_SELECTION,
    CARD_DEALING,
    OBJECTIVE_SELECTION,
    PLACING_CARD_SELECTION,
    PLACING_POSITION_SELECTION,
    CARD_DRAWING,
    CALCULATE_OBJECTIVES;

    public TurnState nextState() throws UnsupportedOperationException{
        switch(this){
            case STARTER_CARD_SELECTION:
                return STARTER_CARD_SELECTION;
            case COLOUR_SELECTION:
                return COLOUR_SELECTION;
            case CARD_DEALING:
                return CARD_DEALING;
            case OBJECTIVE_SELECTION:
                return OBJECTIVE_SELECTION;
            case PLACING_CARD_SELECTION:
                return PLACING_POSITION_SELECTION;
            case PLACING_POSITION_SELECTION:
                return CARD_DRAWING;
            case CARD_DRAWING:
                return PLACING_CARD_SELECTION;
            case CALCULATE_OBJECTIVES:
                return CALCULATE_OBJECTIVES;
        }
        throw new UnsupportedOperationException();
    }

    public TurnState nextStage() throws UnsupportedOperationException{
        switch(this){
            case STARTER_CARD_SELECTION:
                return COLOUR_SELECTION;
            case COLOUR_SELECTION:
                return CARD_DEALING;
            case CARD_DEALING:
                return OBJECTIVE_SELECTION;
            case OBJECTIVE_SELECTION:
                return PLACING_CARD_SELECTION;
            case PLACING_CARD_SELECTION:
            case PLACING_POSITION_SELECTION:
                throw new UnsupportedOperationException();
            case CARD_DRAWING:
                return CALCULATE_OBJECTIVES;
            case CALCULATE_OBJECTIVES:
                return STARTER_CARD_SELECTION;
        }
        throw new UnsupportedOperationException();
    }
}
