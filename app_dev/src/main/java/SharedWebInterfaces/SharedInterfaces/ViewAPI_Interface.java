package SharedWebInterfaces.SharedInterfaces;

import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.Coordinates;

import java.util.HashMap;

public interface ViewAPI_Interface extends GeneralAPI_Interface {

    void setState(String state);

    void setGoldDeck(int[] deck);
    void setResourceDeck(int[] deck);
    void setStarterDeck(int[] deck);
    void setObjectiveDeck(int[] deck);
    void setPlayers(String[] players);
    void setGameId(String gameId);
    void setStarterCard(int starterCard);
    void chooseSecretObjective(int obj1, int obj2);
    // se player == client allora il secret objective del client è settato, sennò viene aggiornato quello del client corrispondente a player
    void setSecretObjective(int secretObjective, String player);
    //card(1,2,3)
    void setPlaceableCard(int card, boolean placeable);

    void addCardToDisposition(int card, Coordinates coordinates, boolean faceside);
    void setPoints(int points);
    void updateArtifacts(HashMap<Artifact, Integer> artifacts);
    void updateElements(HashMap<Element, Integer> elements);
    void updateHand(String player,  int lastDrawnCard);
    void updateCardSource( int cardSource);

    void endGame( HashMap<String, Integer> finalPoints);

}
