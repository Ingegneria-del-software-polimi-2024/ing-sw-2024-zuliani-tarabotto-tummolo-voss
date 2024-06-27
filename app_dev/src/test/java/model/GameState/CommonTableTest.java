package model.GameState;
import model.Exceptions.EmptyCardSourceException;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommonTableTest {

    private CommonTable commonTable;
    private List<Player> players;
    private Player player;

    @BeforeEach
    void setUp() {
        commonTable = new CommonTable();

        players = new ArrayList<>();
        player = new Player();
        players.add(player);
    }

    @Test
    void testInitialize() {
        commonTable.initialize(players);
        assertEquals(2, commonTable.getOpenGold().size());
        assertEquals(2, commonTable.getOpenResources().size());
        assertEquals(2, commonTable.getCommonObjectives().size());
        for (Player player : players) {
            assertEquals(3, player.getPlayingHand().size());
            assertNotNull(player.getStarterCard());
        }
    }

    @Test
    void testInitializeOpenCards() {
        commonTable.initialize(players);
        assertEquals(2, commonTable.getOpenResources().size());
        assertEquals(2, commonTable.getOpenGold().size());
    }

    @Test
    void testInitializeCommonObjectives() {
        commonTable.initialize(players);
        assertEquals(2, commonTable.getCommonObjectives().size());
    }

    @Test
    void testInitializePlayersHands() {
        commonTable.initialize(players);
        for (Player player : players) {
            assertEquals(3, player.getPlayingHand().size());
        }
    }

    @Test
    void testInitializePlayersStarterCard() {
        commonTable.initialize(players);
        for (Player player : players) {
            assertNotNull(player.getStarterCard());
        }
    }

    @Test
    void testCheckEmptyDecks() {
        commonTable.initialize(players);
        assertFalse(commonTable.checkEmptyDecks());
        commonTable.getGoldDeck().getCards().clear();
        commonTable.getResourceDeck().getCards().clear();
        assertTrue(commonTable.checkEmptyDecks());
    }

    @Test
    void testDrawCardGoldDeck() throws EmptyCardSourceException {
        commonTable.initialize(players);
        int initialSize = player.getPlayingHand().size();
        commonTable.drawCardGoldDeck(player);
        assertEquals(initialSize + 1, player.getPlayingHand().size());
    }

    @Test
    void testDrawCardGoldDeck_Empty() {
        commonTable.initialize(players);
        commonTable.getGoldDeck().getCards().clear();
        assertThrows(EmptyCardSourceException.class, () -> {
            commonTable.drawCardGoldDeck(player);
        });
    }

    @Test
    void testDrawCardResourcesDeck() throws EmptyCardSourceException {
        commonTable.initialize(players);
        int initialSize = player.getPlayingHand().size();
        commonTable.drawCardResourcesDeck(player);
        assertEquals(initialSize + 1, player.getPlayingHand().size());
    }

    @Test
    void testDrawCardResourcesDeck_Empty() {
        commonTable.initialize(players);
        commonTable.getResourceDeck().getCards().clear();
        assertThrows(EmptyCardSourceException.class, () -> {
            commonTable.drawCardResourcesDeck(player);
        });
    }

    @Test
    void testDrawCardOpenGold() throws EmptyCardSourceException {
        commonTable.initialize(players);
        int initialSize = player.getPlayingHand().size();
        commonTable.drawCardOpenGold(0, player);
        assertEquals(initialSize + 1, player.getPlayingHand().size());
    }

    @Test
    void testDrawCardOpenGold_Empty() {
        commonTable.initialize(players);
        commonTable.getOpenGold().set(0, null);
        assertThrows(EmptyCardSourceException.class, () -> {
            commonTable.drawCardOpenGold(0, player);
        });
    }

    @Test
    void testDrawCardOpenResources() throws EmptyCardSourceException {
        commonTable.initialize(players);
        int initialSize = player.getPlayingHand().size();
        commonTable.drawCardOpenResources(0, player);
        assertEquals(initialSize + 1, player.getPlayingHand().size());
    }

    @Test
    void testDrawCardOpenResources_Empty() {
        commonTable.initialize(players);
        commonTable.getOpenResources().set(0, null);
        assertThrows(EmptyCardSourceException.class, () -> {
            commonTable.drawCardOpenResources(0, player);
        });
    }

    @Test
    void testDefinedDeckInitialization() {
        commonTable.definedDeckInitialization(players);
        assertEquals(2, commonTable.getOpenGold().size());
        assertEquals(2, commonTable.getOpenResources().size());
        assertEquals(2, commonTable.getCommonObjectives().size());
        for (Player player : players) {
            assertEquals(3, player.getPlayingHand().size());
            assertNotNull(player.getStarterCard());
        }
    }
}
