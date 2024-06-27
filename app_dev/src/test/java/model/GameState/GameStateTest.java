package model.GameState;

import Server.ModelTranslator;
import Server.ModelListener;
import model.Exceptions.KickOutOfGameException;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class tests the functionality of the GameState class.
 * It uses mock objects for ModelListener and ModelTranslator.
 * It tests various methods of the GameState class like nextPlayer, setTurnState, distributeSecretOjectives, playingTurn, quitGame, setStartingCardFace, setSelectedCardFace, setPlayerActive, inizializationReconnection, starterCardReconnection, recoveryStarterCard, and recoveryDrawing.
 */
class GameStateTest {
    /**
     * The GameState object to be tested.
     */
    private GameState gameState;

    /**
     * The mock ModelListener object.
     */
    private ModelListener modelListener;

    /**
     * The mock ModelTranslator object.
     */
    private ModelTranslator modelController;

    /**
     * This method sets up the test environment before each test method.
     */
    @BeforeEach
    void setUp() {
        modelListener = mock(ModelListener.class);
        modelController = mock(ModelTranslator.class);
        ArrayList<String> nicknames = new ArrayList<>();
        nicknames.add("Player1");
        nicknames.add("Player2");
        gameState = new GameState(nicknames, "testId", modelListener, modelController, new HashSet<>());
    }

    /**
     * This method tests the nextPlayer method of the GameState class.
     */
    @Test
    void testNextPlayer() {
        gameState.nextPlayer();
        assertEquals("Player2", gameState.getTurnPlayer().getNickname());
    }

    /**
     * This method tests the setTurnState method of the GameState class.
     */
    @Test
    void testSetTurnState() {
        gameState.setTurnState(TurnState.PLACING_CARD_SELECTION);
        verify(modelListener).notifyChanges(TurnState.PLACING_CARD_SELECTION);
    }

    /**
     * This method tests the distributeSecretOjectives method of the GameState class.
     */
    @Test
    void testDistributeSecretObjectives() {
        gameState.distributeSecretOjectives();
        verify(modelListener, times(2)).notifyChanges(any(ObjectiveCard.class), any(ObjectiveCard.class), anyString());
    }


    /**
     * This method tests the playingTurn method of the GameState class.
     */
    @Test
    void testPlayingTurn() {
        gameState.playingTurn();
        verify(modelListener).notifyChanges(anyString(), anyList(), any(boolean[].class));
    }

    /**
     * This method tests the quitGame method of the GameState class.
     */
    @Test
    void testQuitGame() {
        gameState.quitGame("Player1");
        verify(modelListener).notifyChanges(eq("Player1"), any(KickOutOfGameException.class));
    }

    /**
     * This method tests the setStartingCardFace method of the GameState class.
     */
    @Test
    void testSetStartingCardFace() {
        gameState.setStartingCardFace(true, "Player1");
        assertTrue(gameState.getPlayer(0).getStarterCard().getFaceSide());
    }

    /**
     * This method tests the setSelectedCardFace method of the GameState class.
     */
    @Test
    void testSetSelectedCardFace() {
        PlayableCard card = mock(PlayableCard.class);
        gameState.setSelectedHandCard(card);
        gameState.setSelectedCardFace(true);
        verify(card).setFaceSide(true);
    }

    /**
     * This method tests the setPlayerActive method of the GameState class.
     */
    @Test
    void testSetPlayerActive() {
        gameState.setPlayerActive(0);
        assertTrue(gameState.getPlayer(0).isActive());
    }

    /**
     * This method tests the inizializationReconnection method of the GameState class.
     */
    @Test
    void testInizializationReconnection() {
        gameState.inizializationReconnection(gameState.getPlayer(0));
        verify(modelListener).notifyChanges(anyString(), any(), any(), anyInt());
    }

    /**
     * This method tests the starterCardReconnection method of the GameState class.
     */
    @Test
    void testStarterCardReconnection() {
        gameState.starterCardReconnection(gameState.getPlayer(0));
        verify(modelListener).displayStarterCardNotification(anyString(), any(), any());
    }

    /**
     * This method tests the recoveryStarterCard method of the GameState class.
     */
    @Test
    void testRecoveryStarterCard() {
        gameState.recoveryStarterCard(gameState.getPlayer(0));
        verify(modelController).playStarterCard(anyBoolean(), anyString());
    }

    /**
     * This method tests the recoveryDrawing method of the GameState class.
     */
    @Test
    void testRecoveryDrawing() {
        gameState.recoveryDrawing(gameState.getPlayer(0));
        verify(modelController).drawCard(anyInt());
    }
}
