package SharedWebInterfaces.SharedInterfaces;


import model.GameState.TurnState;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.ObjectiveDeck;
import model.enums.Artifact;
import model.enums.Element;
import model.objective.Objective;
import model.placementArea.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ViewAPI_Interface extends GeneralAPI_Interface {

    void startHeartbeatThread();

    void readyToPlay();
    void setState(TurnState state);
    void setGoldDeck(List<PlayableCard> deck);
    void setResourceDeck(List<PlayableCard>deck);
    void setPlayers(List<String> players);
    void setGameId(String gameId);
    void setOpenGold(List<PlayableCard> openGold);
    void setOpenResource(List<PlayableCard> openResource);
    void setStarterCard(PlayableCard starterCard);
    void setHand(List<PlayableCard> hand);

    void setSecretObjectives(ObjectiveCard obj1, ObjectiveCard obj2);
    // se player == client allora il secret objective del client è settato, sennò viene aggiornato quello del client corrispondente a player
    void setSecretObjective(ObjectiveCard secretObjective);
    void setCommonObjectives(ObjectiveCard commonObjective1, ObjectiveCard commonObjective2);
    //card(1,2,3)

    void setDisposition(String player, HashMap<Coordinates, PlayableCard> disposition);
    void setPoints(String player, int points);
    void updateArtifacts(String player, HashMap<Artifact, Integer> artifacts);
    void updateElements(String player, HashMap<Element, Integer> elements);
    void updateCardSource(List<PlayableCard> deck, int cardSource);
    void updateOpenCards(List<PlayableCard> decK, int card);
    void setFinalPoints( HashMap<String, Integer> finalPoints, ArrayList<String> winners);
    void setPawnColor(String pawnColor);
    void setAvailablePlaces(List<Coordinates> availablePlaces);
    void setCanBePlaced(boolean[] canBePlaced);

    boolean getMyTurn();

    public void confirmSecretObjective(ObjectiveCard card);
    public void setTurnPlayer(String turnPlayer);
    void askNickname();
    void setAvailableGames(ArrayList<String> listOfGames);
    void displayAvailableGames();
    void displayAvailableGames(ArrayList<String> availableGames);
    void nickNameAlreadyInUse();
    void cantPlaceACard(PlayableCard card, Coordinates coord);
    void cantDrawCard(int source);
    void cantJoinRoom();
    void cantCreateRoom();

    /**
     * brings the player back to the lobby
     */
    void returnToLobby();

    void ackNickName(String name);

    void displayStarterCard();
    void displaySecretObjective();
    void displayReconnection();
    void setPlayerID(String playerID); //used for recovery from disconnections

}
