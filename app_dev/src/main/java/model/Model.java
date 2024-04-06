package model;

import controller.Controller;
import model.deckFactory.*;
import model.deckFactory.Generators.*;
import view.CliView;

public class Model {


    public static void main(String[] args) throws Exception {
        //RunDeckCreation runDeckCreation = new RunDeckCreation();
        //runDeckCreation.RunDeckCreation();
       // DeckGenerator objectiveDeckGenerator = new ObjectiveCardsDeckGenerator();

        //ObjectiveCardsDeckGenerator objectiveCardsDeckGenerator = new ObjectiveCardsDeckGenerator();
       // ObjectiveDeck objectiveDeck = objectiveCardsDeckGenerator.generateDeck();

       // System.out.println(objectiveDeck.getCard(91));



        Controller controller = new Controller();
        controller.initializeGameState();
        controller.gameLoop();
    }
}
