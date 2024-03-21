package controller;

import model.GameState.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {
    private GameState gameState;
    private ArrayList<String> nickNames;


    //main method to handle game flow
    public void gameLoop() {
        //FIRST: every player must select a face side for the starting card which is then placed
        for (String player : nickNames) {
            Scanner sc = new Scanner(System.in);
            System.out.println(player + ", select a faceside for the starting card: ");
            gameState.setStartingCardFace(sc.nextBoolean());
            gameState.playStarterCard();
            gameState.nextPlayer();
        }

        //SECOND: every player draws three random cards: 1 goldCard and 2 resourceCard
        gameState.initializePlayersHands();
       /* while (! gameState.getLastTurn()) {
            System.out.println(gameState.getTurnPlayer().getNickname());
        }*/
    }

    //creates a new gameState and ask users for nicknames
    public void initializeGameState() {
        String id = "gameState_0";
        nickNames = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of players: ");
        int numPlayers = scanner.nextInt();

        for (int i = 0; i < numPlayers; i++){
            System.out.print("Enter player_" + (i+1) + " nickname: ");
            String name = scanner.next();
            nickNames.add(name);
        }
        //creates a GameState
        gameState = new GameState(nickNames, id);
        //creates and shuffles decks
        gameState.initializeDecks();
        //Extract open cards
        gameState.initializeOpenCards();
        //give each player a random starting card
        gameState.initializePlayersStartCard();
    }
}
