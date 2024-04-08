package model.GameState;

import model.deckFactory.ObjectiveDeck;
import model.deckFactory.PlayableDeck;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    public static void main(String[] args) {
        GameState state;
        PlayableDeck deck;
        ObjectiveDeck objectiveDeck;
        final int ITERATIONS = 1000;
        final String id = "gioco";
        ArrayList<String> nicknames = new ArrayList<String>();

        nicknames.add("andrea");
        for(int i=0; i<ITERATIONS; i++){
            state = new GameState(nicknames, id);
            deck = state.getStarterDeck();
            assert deck.getSize() == 6: "giro "+i;
            deck = (PlayableDeck) state.getGoldDeck();
            assert deck.getSize() == 40: "giro "+i;
            deck = state.getResourceDeck();
            assert deck.getSize() == 40: "giro "+i;
            objectiveDeck = state.getObjectiveDeck();
            assert objectiveDeck.getSize() == 16: "giro "+i;
        }
        System.out.println("everything fine here");
    }
}