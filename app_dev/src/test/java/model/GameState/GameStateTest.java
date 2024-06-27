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

class GameStateTest {
    private GameState gameState;
    private ModelListener modelListener;
    private ModelTranslator modelController;

    @BeforeEach
    void setUp() {
        modelListener = mock(ModelListener.class);
        modelController = mock(ModelTranslator.class);
        ArrayList<String> nicknames = new ArrayList<>();
        nicknames.add("Player1");
        nicknames.add("Player2");
        gameState = new GameState(nicknames, "testId", modelListener, modelController, new HashSet<>());
    }



    @Test
    void testNextPlayer() {
        gameState.nextPlayer();
        assertEquals("Player2", gameState.getTurnPlayer().getNickname());
    }


    @Test
    void testSetTurnState() {
        gameState.setTurnState(TurnState.PLACING_CARD_SELECTION);
        verify(modelListener).notifyChanges(TurnState.PLACING_CARD_SELECTION);
    }

    @Test
    void testDistributeSecretObjectives() {
        gameState.distributeSecretOjectives();
        verify(modelListener, times(2)).notifyChanges(any(ObjectiveCard.class), any(ObjectiveCard.class), anyString());
    }



    @Test
    void testPlayingTurn() {
        gameState.playingTurn();
        verify(modelListener).notifyChanges(anyString(), anyList(), any(boolean[].class));
    }


    @Test
    void testQuitGame() {
        gameState.quitGame("Player1");
        verify(modelListener).notifyChanges(eq("Player1"), any(KickOutOfGameException.class));
    }






    @Test
    void testSetStartingCardFace() {
        gameState.setStartingCardFace(true, "Player1");
        assertTrue(gameState.getPlayer(0).getStarterCard().getFaceSide());
    }

    @Test
    void testSetSelectedCardFace() {
        PlayableCard card = mock(PlayableCard.class);
        gameState.setSelectedHandCard(card);
        gameState.setSelectedCardFace(true);
        verify(card).setFaceSide(true);
    }




    @Test
    void testSetPlayerActive() {
        gameState.setPlayerActive(0);
        assertTrue(gameState.getPlayer(0).isActive());
    }

    @Test
    void testInizializationReconnection() {
        gameState.inizializationReconnection(gameState.getPlayer(0));
        verify(modelListener).notifyChanges(anyString(), any(), any(), anyInt());
    }

    @Test
    void testStarterCardReconnection() {
        gameState.starterCardReconnection(gameState.getPlayer(0));
        verify(modelListener).displayStarterCardNotification(anyString(), any(), any());
    }



    @Test
    void testRecoveryStarterCard() {
        gameState.recoveryStarterCard(gameState.getPlayer(0));
        verify(modelController).playStarterCard(anyBoolean(), anyString());
    }

    @Test
    void testRecoveryDrawing() {
        gameState.recoveryDrawing(gameState.getPlayer(0));
        verify(modelController).drawCard(anyInt());
    }


}
