package model.placementArea;

import model.GameState.GameState;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.Deck;
import model.deckFactory.ObjectiveDeck;
import model.deckFactory.PlayableDeck;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlacementAreaTest {
    private static GameState gameState;
    static int ID = 81;

    //initializes the gamestate (please always specify one player), inserts the cards expressed from cli
    // in the placementArea of the palyer, the starterCard will be automatically added
    public static void initialize() {
        String id = "test4Objectives";
        ArrayList<String> nickNames = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of players: ");
        int numPlayers = scanner.nextInt();

        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter player_" + (i + 1) + " nickname: ");
            String name = scanner.next();
            nickNames.add(name);
        }

        //creates a GameState
        gameState = new GameState(nickNames, id);

        System.out.println("how many cards do you want to insert?");
        int num = scanner.nextInt();
        ArrayList<Integer> cardList = new ArrayList<Integer>();
        for(int i = 0; i < num; i++){
            System.out.println(i + " insert card id:");
            cardList.add(scanner.nextInt());
        }
        PlayableCard cardToBePlaced = gameState.getStarterDeck().getCard(ID);
        cardToBePlaced.setFaceSide(false);
        gameState.getTurnPlayer().getPlacementArea().addCard(cardToBePlaced);
        putCards(cardList, gameState.getResourceDeck(), gameState.getTurnPlayer().getPlacementArea());
    }

    public static void putCards(ArrayList<Integer> cards, PlayableDeck deck, PlacementArea placementArea) {
        Scanner sc = new Scanner(System.in);
        for (int i : cards) {
            //ask for coordinates
            System.out.println("current free coordinates:\n");
            for (Coordinates c : gameState.getTurnPlayer().getPlacementArea().freePositions())
                System.out.println(c.getX()+" "+c.getY());
            System.out.println("Insert coordinates for card number " + i + "\n");
            Coordinates coord = new Coordinates(sc.nextInt(), sc.nextInt());
            gameState.setSelectedCoordinates(coord);
            PlayableCard x = deck.getCard(i);
            if(x == null) {
                System.out.println("errore");
                return;
            }
            gameState.setSelectedHandCard(x);
            gameState.setSelectedCardFace(false);
            gameState.playCard();
        }
    }

    public static int checkForObjectives(GameState state, ObjectiveCard obj){return obj.countPoints(state.getTurnPlayer().getPlacementArea());}

    //insert 999 when asking "what objective id do you want to check? " if you want to stop
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int id;

        initialize();
        do {
            System.out.println("what objective id do you want to check? ");
            id = scanner.nextInt();
            if(gameState.getObjectiveDeck().getCard(id) == null) return;
            System.out.println(checkForObjectives(gameState, gameState.getObjectiveDeck().getCard(id)));
        }while (id != 999);
    }

}