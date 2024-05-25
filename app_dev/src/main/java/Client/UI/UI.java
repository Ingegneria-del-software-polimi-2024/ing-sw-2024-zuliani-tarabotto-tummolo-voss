package Client.UI;

import model.cards.PlayableCards.PlayableCard;
import model.placementArea.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;

public interface UI extends Runnable{
    //open cards and other players' pawn and nicknames
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
    void displayEndGame();

    void printDisposition(HashMap<Coordinates, PlayableCard> disposition);

    void displayCalculateObjectives();
    void chooseConnection();
    void askNickname();
    void displayAvailableGames(ArrayList<String> listOfGames);
    void joinedGame(String gameID);
    void firstWelcome();
    void nickNameAlreadyInUse();
    void cantPlaceACard(PlayableCard card, Coordinates coord);
    void cantDrawCard(int source);
    void cantCreateRoom();
    void cantJoinRoom();
}
