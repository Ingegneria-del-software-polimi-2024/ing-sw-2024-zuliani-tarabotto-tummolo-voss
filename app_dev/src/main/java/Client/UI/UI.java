package Client.UI;

import Client.View.ViewAPI;

public interface UI {
    //open cards and other other players' pawn and nicknames
    void displayInitialization();

    //starter card for the player
    void displayStarterCardSelection();

    //the initial hand is shown and the commonObjectives, also the two secretObjectives are shown and
    //the player has to make a choice
    void displayObjectiveSelection();

    //the disposition of the player and his hand is shown, it's possible to visualize the other players' dispositions
    void displayPlacingCard();

    //all the cards that can be drawn
    void displayCardDrawing();

    //whatever -> probably the end
    void displayCalculateObjectives();

}
