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
        final int ITERATIONS = 100;
        final String id = "gioco";
        ArrayList<String> nicknames = new ArrayList<String>();

        nicknames.add("andrea");

        for(int i=0; i<ITERATIONS; i++){
            state = new GameState(nicknames, id);
            deck = state.getStarterDeck();
            assert deck.getSize() == 6;
            deck = (PlayableDeck) state.getGoldDeck();
            assert deck.getSize() == 40;
            deck = state.getResourceDeck();
            assert deck.getSize() == 40;
            objectiveDeck = state.getObjectiveDeck();
            assert objectiveDeck.getSize() == 16;
        }
        System.out.println("everything fine here");
    }
}