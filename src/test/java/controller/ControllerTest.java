package controller;


import model.Exceptions.CantPlaceCardException;
import model.Exceptions.EmptyCardSourceException;
import model.GameState.GameState;
import model.GameState.MockModelListener;
import model.enums.Pawn;
import model.objective.Shape;
import model.placementArea.Coordinates;
import model.placementArea.PlacementAreaIterator;
import model.player.Player;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;


/**
 * This class is used to test the controller of the game.
 * It simulates a game flow and checks if the game state changes as expected.
 */
class ControllerTest {

    /**
     * This class is used to test the controller of the game.
     * It simulates a game flow and checks if the game state changes as expected.
     */
    private GameState gameState;

    /**
     * The list of nicknames of the players.
     */
    private ArrayList<String> nickNames;

    /**
     * The path to the file used for testing.
     */
    private String path = "C:\\Users\\nicol\\OneDrive\\Documenti\\GitHub\\ing-sw-2024-zuliani-tarabotto-tummolo-voss\\app_dev\\src\\test\\java\\controller\\";

    /**
     * The scanner used to read the test file.
     */
    private Scanner sc = new Scanner(new File(path+"final"));

    /**
     * The player who starts the game.
     */
    private Player initialPlayer;

    /**
     * Constructs a new ControllerTest.
     *
     * @throws FileNotFoundException if the test file is not found.
     */
    ControllerTest() throws FileNotFoundException {
    }


    /**
     * The main method for the test.
     *
     * @throws EmptyCardSourceException if the card source is empty.
     * @throws IOException if an I/O error occurs.
     */
    @Test
    public void main() throws EmptyCardSourceException, IOException {
        ControllerTest controller = new ControllerTest();
        controller.initializeGameState();
        controller.gameLoop();
    }

    /**
     * Initialize the game state.
     */
    public void initializeGameState() {
        String id = "gameState_0";
        nickNames = new ArrayList<String>();
        int numPlayers = Integer.parseInt(sc.next());
        for(int x= 0; x<numPlayers; x++)
            nickNames.add(sc.next());
        //creates a GameState
        int i = 0;
        gameState = new GameState(nickNames, id, i, new MockModelListener());
        initialPlayer = gameState.getTurnPlayer();
    }


    /**
     * This method is the main game loop. It handles the game flow, including player login,
     * objective selection, game rounds, and final scoring. It also writes the game results to a file
     * and compares the output with the expected output.
     *
     * @throws EmptyCardSourceException if the card source is empty.
     * @throws IOException if an I/O error occurs.
     */
    public void gameLoop() throws EmptyCardSourceException, IOException {
        int cont = 0;

        //FIRST: every player must select a face side for the starting card which is then placed ans also selects a pawn
        //LOGIN SECTION
        for (String player : nickNames) {
            //selecting and placing starter card
            gameState.setStartingCardFace(sc.nextBoolean(), player);
            gameState.playStarterCard(player);
            //selecting pawn
            Pawn pawn = Pawn.valueOf(sc.next());
            pawn.setFalseIsAvailable();
            gameState.setPlayerPawnColor(pawn);
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
            System.out.println("round");
            playTurn();
        }

        System.out.println("penultimo round");
        //FIFTH: we continue playing until the initial player is reached and then we play the final round
        while (gameState.getTurnPlayer() != initialPlayer) {
            cont ++;
            playTurn();
        }

        //if we already played an entire round we end the game, else we play another additional round
        if(cont == nickNames.size() -1 ){
            gameState.calculateFinalPoints();
            System.out.println("fine partita al penultimo round");


        }else{
            for(int i = 0; i < nickNames.size(); i++){
                playTurn();
            }
            gameState.calculateFinalPoints();
        }

        printWinner();
        fileWriting();

        String filePath1 = path+"output";
        String filePath2 = path+"expectedOutput";
        File f1 = new File(filePath1);
        File f2 = new File(filePath2);


        assert filesCompareByLine(f1.toPath(), f2.toPath()) == -1;

    }


    /**
     * This method calls the appropriate draw function based on the input parameter.
     *
     * @param i the index representing the draw function to call.
     * @throws EmptyCardSourceException if the card source is empty.
     */
    private void callDrawFunction(int i) throws EmptyCardSourceException {
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

    /**
     * This method handles a single turn in the game. It includes card selection, card placement,
     * and drawing a new card.
     *
     * @throws EmptyCardSourceException if the card source is empty.
     */
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
        try {
            gameState.playCard();
        }catch (CantPlaceCardException e) {
            throw new RuntimeException(e);
        }
        callDrawFunction(sc.nextInt());
        gameState.setLastTurnTrue();
        gameState.nextPlayer();
    }

    /**
     * This method prints the winner of the game.
     */
    private void printWinner() {
        Player winner = gameState.getPlayer(0);
        for (int i = 0; i < nickNames.size(); i++) {
            System.out.println(gameState.getPlayer(i).getNickname() + ", points: " + gameState.getPlayer(i).getPoints());
            if (gameState.getPlayer(i).getPoints() > winner.getPoints()){
                winner = gameState.getPlayer(i);
            }
        }
        System.out.println(winner.getNickname().toUpperCase() + " YOU WIN!!!!");
    }

    /**
     * This method writes the game results to a file.
     */
    private void  fileWriting() {
        //we find the winner
        Player winner = gameState.getPlayer(0);
        for (int i = 1; i < nickNames.size(); i++) {
            if (gameState.getPlayer(i).getPoints() > winner.getPoints()){
                winner = gameState.getPlayer(i);
            }
        }


        //we write the output file
        String filename = path+"output"; // Specify the file name

        try {
            // Create a FileWriter object
            FileWriter writer = new FileWriter(filename);

            // Write content to the file
            writer.write("winner: " +  winner.getNickname() + "\n");
            for (int i = 0; i < nickNames.size(); i++) {
                writer.write(gameState.getPlayer(i).getNickname()+ ": " + gameState.getPlayer(i).getPoints() + " points\n");

                PlacementAreaIterator placementAreaIterator = new PlacementAreaIterator(gameState.getPlayer(i).getPlacementArea().getDisposition(), Shape.TOPLEFTL);
                Coordinates c;
                while(placementAreaIterator.hasNext()){
                    c = placementAreaIterator.current();
                    writer.write("Card: " + gameState.getPlayer(i).getPlacementArea().getDisposition().get(c).getId() + ", (" + c.getX() + ";" + c.getY() + ")\n");
                    placementAreaIterator.next();
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

    /**
     * This method compares two files line by line. It returns the line number of the first difference,
     * or -1 if the files are identical.
     *
     * @param path1 the path to the first file.
     * @param path2 the path to the second file.
     * @return the line number of the first difference, or -1 if the files are identical.
     * @throws IOException if an I/O error occurs.
     */
    public static long filesCompareByLine(Path path1, Path path2) throws IOException {
        try (BufferedReader bf1 = Files.newBufferedReader(path1);
             BufferedReader bf2 = Files.newBufferedReader(path2)) {

            long lineNumber = 1;
            String line1 = "", line2 = "";
            while ((line1 = bf1.readLine()) != null) {
                line2 = bf2.readLine();
                if (line2 == null || !line1.equals(line2)) {
                    return lineNumber;
                }
                lineNumber++;
            }
            if (bf2.readLine() == null) {
                return -1;
            }
            else {
                return lineNumber;
            }
        }
    }
}