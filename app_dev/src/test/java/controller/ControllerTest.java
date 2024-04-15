package controller;

import Exceptions.EmptyCardSourceException;
import model.GameState.GameState;
import model.enums.Pawn;
import model.placementArea.Coordinates;
import model.player.Player;
import org.junit.jupiter.api.Test;
import view.CliView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class ControllerTest {
    private GameState gameState;
    private ArrayList<String> nickNames;
    private CliView view;
    private Scanner sc = new Scanner(new File("/Users/francesco/dev/ing-sw-2024-zuliani-tarabotto-tummolo-voss/app_dev/src/test/java/controller/input_file"));
    private Player initialPlayer;


    ControllerTest() throws FileNotFoundException {
    }
@Test
    public void main() throws EmptyCardSourceException, FileNotFoundException {
        ControllerTest controller = new ControllerTest();
        controller.initializeGameState();
        controller.gameLoop();
    }

    //creates a new gameState and ask users for nicknames
    public void initializeGameState() {
        String id = "gameState_0";
        nickNames = new ArrayList<String>();
        int numPlayers = Integer.parseInt(sc.next());
        nickNames.add(sc.next());
        nickNames.add(sc.next());
        //creates a GameState
        int i = 0;
        gameState = new GameState(nickNames, id, i);
        initialPlayer = gameState.getTurnPlayer();

    }


    //main method to handle game flow

    public void gameLoop() throws EmptyCardSourceException {
        int cont = 0;

        //FIRST: every player must select a face side for the starting card which is then placed ans also selects a pawn
        //LOGIN SECTION
        for (String player : nickNames) {
            //selecting and placing starter card
            gameState.setStartingCardFace(sc.nextBoolean());
            gameState.playStarterCard();
            //selecting pawn
            Pawn pawn = Pawn.valueOf(sc.next());
            gameState.setPlayerPawnColor(pawn);
            pawn.setIsAvailable();
            gameState.nextPlayer();
        }

        //THIRD: display the two commonObjectives cards and each player selects his secret objective between two given cards
        for (String player: nickNames) {
            //set the player secret objective
            gameState.getTurnPlayer().setSecretObjective(gameState.getObjectiveDeck().get(Integer.parseInt(sc.next())));
            //remove the two extracted objectives
            gameState.getObjectiveDeck().extract();
            gameState.getObjectiveDeck().extract();
            gameState.nextPlayer();
        }

        //FOURTH: loops until getLastTurn is true
        while (!gameState.getLastTurn()) {
            System.out.println("giro");
            playTurn();
        }

        System.out.println("penultimo giro");
        //FIFTH: we continue playing until the initial player is reached and then we play the final round
        while (gameState.getTurnPlayer() != initialPlayer) {
            cont ++;
            playTurn();
        }

        //if we already played an entire round we end the game, else we play another additional round
        if(cont == nickNames.size() -1 ){
            gameState.calculateFinalPoints();
            System.out.println("fine partita al penultimo giro");


        }else{
            for(int i = 0; i < nickNames.size(); i++){
                playTurn();
            }
            gameState.calculateFinalPoints();
        }

        printWinner();
        fileWriting();

        File f1 = new File("/Users/francesco/dev/ing-sw-2024-zuliani-tarabotto-tummolo-voss/app_dev/src/test/java/controller/output");
        File f2 = new File("/Users/francesco/dev/ing-sw-2024-zuliani-tarabotto-tummolo-voss/app_dev/src/test/java/controller/expectedOutput");
        assert !f1.equals(f2);

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

        //ASK PLAYER TO SELECT A CARD FROM HAND
        switch(this.sc.nextInt()){
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

        gameState.setSelectedCardFace(sc.nextBoolean());
        Coordinates c = new Coordinates(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
        gameState.setSelectedCoordinates(c);

        gameState.playCard();
        callDrawFunction(sc.nextInt());
        gameState.setLastTurnTrue();
        gameState.nextPlayer();
    }

    private void printWinner() {
        Player winner = gameState.getPlayer(0);
        for (int i = 1; i < nickNames.size(); i++) {
            if (gameState.getPlayer(i).getPoints() > winner.getPoints()){
                winner = gameState.getPlayer(i);
            }
        }
        System.out.println(winner.getNickname().toUpperCase() + " YOU WIN!!!!");
    }

    private void  fileWriting() {
        //we find the winner
        Player winner = gameState.getPlayer(0);
        for (int i = 1; i < nickNames.size(); i++) {
            if (gameState.getPlayer(i).getPoints() > winner.getPoints()){
                winner = gameState.getPlayer(i);
            }
        }


        //we write the output file
        String filename = "/Users/francesco/dev/ing-sw-2024-zuliani-tarabotto-tummolo-voss/app_dev/src/test/java/controller/output"; // Specify the file name

        try {
            // Create a FileWriter object
            FileWriter writer = new FileWriter(filename);

            // Write content to the file
            writer.write("winner: " +  winner.getNickname() + "\n");
            for (int i = 0; i < nickNames.size(); i++) {
                writer.write(gameState.getPlayer(i).getNickname()+ ": " + gameState.getPlayer(i).getPoints() + " points\n");
                for(Coordinates c : gameState.getPlayer(i).getPlacementArea().getDisposition().keySet()){
                    writer.write("Card: " + gameState.getPlayer(i).getPlacementArea().getDisposition().get(c).getId() + ", (" + c.getX() + ";" + c.getY() + ")\n");
                }
            }

            // Close the FileWriter
            writer.close();

            System.out.println("Content has been written to " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}