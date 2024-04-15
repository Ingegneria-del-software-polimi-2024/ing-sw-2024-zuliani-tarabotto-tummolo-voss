package controller;

import Exceptions.EmptyCardSourceException;
import model.GameState.GameState;
import model.placementArea.Coordinates;
import model.player.Player;
import model.enums.Pawn;
import view.CliView;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
    private GameState gameState;
    private ArrayList<String> nickNames;
    private CliView view;
    private Scanner sc = new Scanner(System.in);
    private Player initialPlayer;


    //main method to handle game flow
    public void gameLoop() throws EmptyCardSourceException {
        int cont = 0;

        //FIRST: every player must select a face side for the starting card which is then placed ans also selects a pawn
        //LOGIN SECTION
        for (String player : nickNames) {
            //selecting and placing starter card
            System.out.println("YOUR STARTER CARD: \n");
            view.printStarterCard();
            System.out.println(player + ", select a face side for the starting card: ");
            gameState.setStartingCardFace(sc.nextBoolean());
            gameState.playStarterCard();
            //selecting pawn
            System.out.println("SELECT A PAWN: ");
            Pawn.printAvailablePawns();
            Pawn pawn = Pawn.valueOf(sc.next());
            gameState.setPlayerPawnColor(pawn);
            pawn.setIsAvailable();
            gameState.nextPlayer();
        }

        //SECOND: every player draws three random cards: 1 goldCard and 2 resourceCard
        //gameState.initializePlayersHands();

        //THIRD: display the two commonObjectives cards and each player selects his secret objective between two given cards
        System.out.println("COMMON OBJECTIVES");
        gameState.printCommonObjectives();
        for (String player: nickNames) {
            System.out.println("CHOOSE A SECRET OBJECTIVE: (0/1)\n");
            gameState.getObjectiveDeck().get(0).printCard();
            gameState.getObjectiveDeck().get(1).printCard();
            //set the player secret objective
            gameState.getTurnPlayer().setSecretObjective(gameState.getObjectiveDeck().get(sc.nextInt()));
            //remove the two extracted objectives
            gameState.getObjectiveDeck().extract();
            gameState.getObjectiveDeck().extract();
            System.out.println("YOUR SECRET OBJECTIVE");
            gameState.getTurnPlayer().getSecretObjective().printCard();
        }

        //FOURTH: loops until getLastTurn is true
        while (gameState.getLastTurn()) {
            playTurn();
        }

        //FIFTH: we continue playing until the initial player is reached and then we play the final round
        while (gameState.getTurnPlayer() != initialPlayer) {
            cont ++;
            playTurn();
        }

        //if we already played an entire round we end the game, else we play another additional round
        if(cont == nickNames.size() -1 ){
            gameState.calculateFinalPoints();
            printWinner();
        }else{
            for(int i = 0; i < nickNames.size(); i++){
                playTurn();
            }
            gameState.calculateFinalPoints();
            printWinner();
        }
    }

    //creates a new gameState and ask users for nicknames
    public void initializeGameState() {
        String id = "gameState_0";
        nickNames = new ArrayList<String>();
        System.out.print("Enter number of players: ");
        int numPlayers = sc.nextInt();

        for (int i = 0; i < numPlayers; i++){
            System.out.print("Enter player_" + (i+1) + " nickname: ");
            String name = sc.next();
            nickNames.add(name);
        }
        //creates a GameState
        gameState = new GameState(nickNames, id);
        initialPlayer = gameState.getTurnPlayer();
        //initialize view
        view = new CliView(gameState);
    }

    private void callDrawFunction(int i) throws EmptyCardSourceException {
        try{
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
        catch (EmptyCardSourceException ex) {
            System.out.println(ex.getMessage());
            System.out.println("choose another source to draw a card from");
            callDrawFunction(new Scanner(System.in).nextInt());
        }

    }

    private void playTurn() throws EmptyCardSourceException {
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
        gameState.printPlayerAvailablePlaces();
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

    private void printWinner() {
        Player winner = gameState.getPlayer(0);
        for (int i = 1; i < nickNames.size(); i++) {
            if (gameState.getPlayer(i).getPoints() > winner.getPoints()){
                System.out.println(winner.getNickname().toUpperCase() + " YOU WIN!!!!");
            }
        }
    }

}
