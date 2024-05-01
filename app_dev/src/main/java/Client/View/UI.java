package Client.View;

public interface UI {

    //open cards and other other players' pawn and nicknames
    void initializationDisplay(ViewAPI view);

    //starter card for the player
    void starterSelectionDisplay(ViewAPI view);

    //the initial hand is shown and the commonObjectives, also the two secretObjectives are shown and
    //the player has to make a choice
    void objectiveSelectionDisplay(ViewAPI view);

    //the disposition of the player and his hand is shown, it's possible to visualize the other players' dispositions
    void placingCardDisplay(ViewAPI view);

    //all the cards that can be drawn
    void cardDrawingDisplay(ViewAPI view);

    //whatever
    void calculateObjectivesDisplay(ViewAPI view);
}
