package model.placementArea;

import model.GameState.GameState;
import model.deckFactory.PlayableDeck;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlacementAreaTest {
    GameState gameState;

    void initialize() {
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
            System.out.println("insert card id:");
            cardList.add(scanner.nextInt());
        }
        //insert a getter from resource decks
        putCards(cardList, gameState.get, gameState.getTurnPlayer().getPlacementArea());
    }

    void putCards(ArrayList<Integer> cards, PlayableDeck deck, PlacementArea placementArea) {
        Scanner sc = new Scanner(System.in);

        placementArea.addCard(deck.getCard(0));
        cards.remove(0);
        for (int i : cards) {
            //ask for coordinates
            System.out.println("Insert coordinates for card number" + i + "\n");
            Coordinates coord = new Coordinates(sc.nextInt(), sc.nextInt());
            placementArea.addCard(deck.getCard(i));
        }
    }


}