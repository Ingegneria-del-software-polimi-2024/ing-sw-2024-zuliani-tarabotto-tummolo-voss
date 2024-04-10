package model.GameState;

import Exceptions.EmptyCardSourceException;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Pawn;
import model.placementArea.Coordinates;
import model.player.Player;
import model.deckFactory.*;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    private List<Player> players;
    private String id;
    private Player turnPlayer;
    private boolean isLastTurn = false;
    private PlayableCard selectedHandCard;
    private Coordinates selectedCoordinates;
    private CommonTable commonTable;

    /////// CONSTRUCTOR //////////////////////////////////////////////////////////////////
    public GameState(ArrayList<String> nickNames, String id) {
        //creates a new players list with the nicknames taken from input
        players = new ArrayList<Player>();
        for(String name : nickNames) {
            Player p;
            players.add(p = new Player());
            p.setNickname(name);
        }
        this.turnPlayer = players.get(0);
        this.id = id;
        // initialize commonTable
        this.commonTable = new CommonTable(players);
    }

    //////////////////// GENERAL TURN CONTROL METHODS ///////////////////////////////////////
    //update each player's final points by calling the methods in Player class
    public void calculateFinalPoints(){
        int i, j;
        for(i=0; i< players.size(); i++){
            players.get(i).calculateSecretObj();
            for(j=0; j<2; j++){
                players.get(i).calculateSingleCommonObj(commonTable.getCommonObjectives().get(j));
            }
        }
    }

    //checks if any player reached 20 points or if both the gold and the resource decks are empty
    public void setLastTurnTrue(){
        //checks if a player has reached 20 points
        for(int i=0; i<players.size(); i++){
            if (players.get(i).getPoints() >= 20) {
                isLastTurn = true;
                break;
            }
        }
        //checks if both decks are empty and also openGold and openResources are empty
        isLastTurn = commonTable.checkEmtpyDecks();
    }

    //updates turnPlayer
    public void nextPlayer() {
        int currentPlayer = players.indexOf(turnPlayer);
        if(currentPlayer == players.size() - 1) {
            turnPlayer = players.get(0);
        } else{turnPlayer = players.get(currentPlayer+1);}
    }


    ////////////////// CARDS PLACEMENT RELATED METHODS //////////////////////////////////////////////////////
    public void playCard(){
        //calls the playCard method contained in the Player class
        turnPlayer.playCard(selectedHandCard, selectedCoordinates);
        //method to check if the player has reached 20 points
        setLastTurnTrue();
    }

    public void playStarterCard() {
        turnPlayer.playStarterCard();
    }

    public void setSelectedHandCard(PlayableCard card) {
        this.selectedHandCard = card;
    }

    public void setSelectedCoordinates(Coordinates coordinates) {
        this.selectedCoordinates = coordinates;
    }

    //sets the faceSide for the player's starting card
    public void setStartingCardFace(boolean faceSide) {
        turnPlayer.getStarterCard().setFaceSide(faceSide);
    }

    //sets the faceSide for the card that the player has selected from his hand
    public void setSelectedCardFace(boolean faceSide) {
        selectedHandCard.setFaceSide(faceSide);
    }


////////////////////////////////INTERFACE METHODS///////////////////////////////////////
    public void drawCardGoldDeck() throws EmptyCardSourceException {
        commonTable.drawCardGoldDeck(turnPlayer);
        setLastTurnTrue();
    }

    public void drawCardResourcesDeck() throws EmptyCardSourceException {
        commonTable.drawCardResourcesDeck(turnPlayer);
        setLastTurnTrue();
    }

    public void drawCardOpenGold(int index) throws EmptyCardSourceException {
        commonTable.drawCardOpenGold(index, turnPlayer);
        setLastTurnTrue();
    }

    public void drawCardOpenResources(int index) throws EmptyCardSourceException {
        commonTable.drawCardOpenResources(index, turnPlayer);
        setLastTurnTrue();
    }

    public void setPlayerPawnColor(Pawn pawnColor) { turnPlayer.setPawnColor(pawnColor);}


/////////////// GETTER METHODS FOR COMMONTABLE ATTRIBUTES ////////////////////////
    public PlayableDeck getGoldDeck() { return commonTable.getGoldDeck(); }
    public PlayableDeck getResourceDeck() { return commonTable.getResourceDeck(); }
    public PlayableDeck getStarterDeck(){return commonTable.getStarterDeck();}
    public ObjectiveDeck getObjectiveDeck() {return commonTable.getObjectiveDeck();}
    public List<PlayableCard> getOpenResources() { return commonTable.getOpenResources(); }
    public List<PlayableCard> getOpenGold() { return commonTable.getOpenGold(); }
    public Player getPlayer(int index){ return players.get(index); }
    public String getId(){ return id; }
    public Player getTurnPlayer(){ return turnPlayer; }
    public boolean getLastTurn(){ return isLastTurn; }
    public int getPoints() {return turnPlayer.getPoints(); }
    //returns turnPlayer's card at specified index in his hand
    public PlayableCard getPlayerHandCard(int index) { return turnPlayer.getPlayingHand().get(index); }

    public List<ObjectiveCard> getCommonObjectives(){return commonTable.getCommonObjectives();}


/////////////////////// PRINTING METHODS FOR CONSOLE TESTING ////////////////////////////////////////////////////////////////

    //calls the PlacementArea method to print available places where the player can put the selected card
    public void printPlayerAvailablePlaces() {turnPlayer.getPlacementArea().printAvailablePlaces();}

    //calls the PlacementArea method to print the player's cards disposition
    public void printPlayerDisposition(){
        turnPlayer.getPlacementArea().printDisposition();
    }

    public void printCommonObjectives() {
        System.out.println("COMMON OBJECTIVE 1:");
        commonTable.getCommonObjectives().get(0).printCard();
        System.out.println("COMMON OBJECTIVE 2:");
        commonTable.getCommonObjectives().get(1).printCard();
    }

}
