package controller;

import model.GameState.GameState;
import model.placementArea.Coordinates;
import view.CliView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {
    private GameState gameState;
    private ArrayList<String> nickNames;
    private CliView view;


    //main method to handle game flow
    public void gameLoop() {
        //FIRST: every player must select a face side for the starting card which is then placed
        Scanner sc = new Scanner(System.in);
        for (String player : nickNames) {
            System.out.println("YOUR STARTER CARD: \n");
            view.printStarterCard();
            System.out.println(player + ", select a face side for the starting card: ");
            gameState.setStartingCardFace(sc.nextBoolean());
            gameState.playStarterCard();
            gameState.nextPlayer();
        }

        //SECOND: every player draws three random cards: 1 goldCard and 2 resourceCard
        gameState.initializePlayersHands();

        //THIRD: loops until getLastTurn is true
        while (! gameState.getLastTurn()) {
            System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
            System.out.println("\u001B[35mCURRENT PLAYER: \u001B[0m " + gameState.getTurnPlayer().getNickname().toString());
            System.out.println("\u001B[35mPLAYER DISPOSITION: \u001B[0m ");
            gameState.printPlayerDisposition();
            System.out.println();

            //NB: spostare printPlayerHand
            view.printPlayerHand();
            //ASK PLAYER TO SELECT A CARD FROM HAND
            System.out.println("CHOOSE A CARD FROM YOUR HAND: ( 1 for C_1, 2 for C_2, 3 for C_3)");
            switch(sc.nextInt()){
                case 1:
                    gameState.setSelectedHandCard(gameState.getPlayerHandCard(0));
                    break;
                case 2:
                    gameState.setSelectedHandCard(gameState.getPlayerHandCard(1));
                    break;
                case 3:
                    gameState.setSelectedHandCard(gameState.getPlayerHandCard(2));
                    break;
            }
            System.out.println("CHOOSE A FACE SIDE FOR THE SELECTED CARD: ");
            gameState.setSelectedCardFace(sc.nextBoolean());
            //PRINT ALL AVAILABLE POSITIONS
            System.out.println("YOU CAN PLACE THE CARD IN ONE OF THESE POSITIONS: (enter coordinates)");
            gameState.getTurnPlayer().getPlacementArea().printAvailablePlaces();
            Coordinates c = new Coordinates(sc.nextInt(), sc.nextInt());
            gameState.setSelectedCoordinates(c);
            gameState.playCard();
            gameState.printPlayerDisposition();
            System.out.println("DRAW A CARD: 1 -> gold deck");
            System.out.println("             2 -> resource deck");
            System.out.println("             3 -> open gold 1");
            System.out.println("             4 -> open gold 2");
            System.out.println("             5 -> open resource 1");
            System.out.println("             6 -> open resource 2");
            callDrawFunction(sc.nextInt());
            view.printPlayerHand();
            System.out.println("YOUR POINTS: " + gameState.getPoints());
            gameState.nextPlayer();
        }
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
        //initialize view
        view = new CliView(gameState);
    }

    private void callDrawFunction(int i) {
        switch (i) {
            case 1:
                gameState.drawCardGoldDeck();
                break;
            case 2:
                gameState.drawCardResourcesDeck();
                break;
            case 3:
                gameState.drawCardOpenGold(0);
                break;
            case 4:
                gameState.drawCardOpenGold(1);
                break;
            case 5:
                gameState.drawCardOpenResources(0);
                break;
            case 6:
                gameState.drawCardOpenResources(1);
                break;
        }
    }

}
