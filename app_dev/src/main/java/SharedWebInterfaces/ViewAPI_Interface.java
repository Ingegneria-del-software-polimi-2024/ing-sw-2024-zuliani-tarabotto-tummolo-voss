package SharedWebInterfaces;

import SharedWebInterfaces.Messages.GeneralAPI_Interface;
import model.GameState.TurnState;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.ObjectiveDeck;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ViewAPI_Interface extends GeneralAPI_Interface {

    void setState(TurnState state);
    void setGoldDeck(List<PlayableCard> deck);
    void setResourceDeck(List<PlayableCard>deck);
    void setPlayers(String[] players);
    void setGameId(String gameId);
    void setOpenGold(List<PlayableCard> openGold);
    void setOpenResource(List<PlayableCard> openResource);
    void setStarterCard(PlayableCard starterCard);
    void setHand(List<PlayableCard> hand);

    void chooseSecretObjective(ObjectiveCard obj1, ObjectiveCard obj2);
    // se player == client allora il secret objective del client è settato, sennò viene aggiornato quello del client corrispondente a player
    void setSecretObjective(ObjectiveCard secretObjective);
    void setCommonObjectives(ObjectiveCard commonObjective1, ObjectiveCard commonObjective2);
    //card(1,2,3)

    void setDisposition(String player, HashMap<Coordinates, PlayableCard> disposition);
    void setPoints(String player, int points);
    void updateArtifacts(HashMap<Artifact, Integer> artifacts);
    void updateElements(HashMap<Element, Integer> elements);
    void updateCardSource(List<PlayableCard> deck, int cardSource);

    void endGame( HashMap<String, Integer> finalPoints);
    void setPawnColor(String pawnColor);
    void setAvailablePlaces(List<Coordinates> availablePlaces);
    void setCanBePlaced(boolean[] canBePlaced);

    void setMyTurn(boolean bool);
    boolean getMyTurn();
}
