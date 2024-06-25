package model.GameState;

import model.deckFactory.ObjectiveDeck;
import model.deckFactory.PlayableDeck;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


class GameStateTest {

    @Test
    public void testGameStateCreation() {
        GameState state;
        PlayableDeck deck;
        ObjectiveDeck objectiveDeck;
        final int ITERATIONS = 10;
        final String id = "gioco";
        ArrayList<String> nicknames = new ArrayList<String>();

        nicknames.add("andrea");
        for(int i=0; i<ITERATIONS; i++){
//            //state = new GameState(nicknames, id, new MockModelListener());
//          //  objectiveDeck = state.getObjectiveDeck();
//           // System.out.println(objectiveDeck.getSize());
//            //deck = state.getGoldDeck();
//            System.out.println(deck.getSize());
//            assert deck.getSize() == 37: "giro "+i;
//            deck = state.getResourceDeck();
//            System.out.println(deck.getSize());
//            assert deck.getSize() == 36: "giro "+i;
//            objectiveDeck = state.getObjectiveDeck();
//            System.out.println(deck.getSize());
//            assert objectiveDeck.getSize() == 14: "giro "+i;
//            assert true;
        }
        System.out.println("everything fine here");
    }
}