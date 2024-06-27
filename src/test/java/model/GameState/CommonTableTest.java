package model.GameState;
import model.Exceptions.EmptyCardSourceException;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the CommonTable class.
 */
class CommonTableTest {

    /**
     * The CommonTable instance under test.
     */
    private CommonTable commonTable;

    /**
     * The list of players in the game.
     */
    private List<Player> players;

    /**
     * A single player used in the tests.
     */
    private Player player;

    /**
     * This method sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        commonTable = new CommonTable();

        players = new ArrayList<>();
        player = new Player();
        players.add(player);
    }

    /**
     * This test checks the initialization of the CommonTable.
     */
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

    /**
     * This test checks the initialization of open cards in the CommonTable.
     */
    @Test
    void testInitializeOpenCards() {
        commonTable.initialize(players);
        assertEquals(2, commonTable.getOpenResources().size());
        assertEquals(2, commonTable.getOpenGold().size());
    }

    /**
     * This test checks the initialization of common objectives in the CommonTable.
     */
    @Test
    void testInitializeCommonObjectives() {
        commonTable.initialize(players);
        assertEquals(2, commonTable.getCommonObjectives().size());
    }

    /**
     * This test checks if the players' hands are correctly initialized with 3 cards.
     */
    @Test
    void testInitializePlayersHands() {
        commonTable.initialize(players);
        for (Player player : players) {
            assertEquals(3, player.getPlayingHand().size());
        }
    }

    /**
     * This test checks if the players' starter cards are correctly initialized and are not null.
     */
    @Test
    void testInitializePlayersStarterCard() {
        commonTable.initialize(players);
        for (Player player : players) {
            assertNotNull(player.getStarterCard());
        }
    }

    /**
     * This test checks if the method checkEmptyDecks correctly identifies when the decks are empty.
     */
    @Test
    void testCheckEmptyDecks() {
        commonTable.initialize(players);
        assertFalse(commonTable.checkEmptyDecks());
        commonTable.getGoldDeck().getCards().clear();
        commonTable.getResourceDeck().getCards().clear();
        assertTrue(commonTable.checkEmptyDecks());
    }

    /**
     * This test checks if a card is correctly drawn from the gold deck and added to the player's hand.
     */
    @Test
    void testDrawCardGoldDeck() throws EmptyCardSourceException {
        commonTable.initialize(players);
        int initialSize = player.getPlayingHand().size();
        commonTable.drawCardGoldDeck(player);
        assertEquals(initialSize + 1, player.getPlayingHand().size());
    }

    /**
     * This test checks if an EmptyCardSourceException is thrown when trying to draw a card from an empty gold deck.
     */
    @Test
    void testDrawCardGoldDeck_Empty() {
        commonTable.initialize(players);
        commonTable.getGoldDeck().getCards().clear();
        assertThrows(EmptyCardSourceException.class, () -> {
            commonTable.drawCardGoldDeck(player);
        });
    }

    /**
     * This test checks if a card is correctly drawn from the resources deck and added to the player's hand.
     */
    @Test
    void testDrawCardResourcesDeck() throws EmptyCardSourceException {
        commonTable.initialize(players);
        int initialSize = player.getPlayingHand().size();
        commonTable.drawCardResourcesDeck(player);
        assertEquals(initialSize + 1, player.getPlayingHand().size());
    }

    /**
     * This test checks if an EmptyCardSourceException is thrown when trying to draw a card from an empty resources deck.
     */
    @Test
    void testDrawCardResourcesDeck_Empty() {
        commonTable.initialize(players);
        commonTable.getResourceDeck().getCards().clear();
        assertThrows(EmptyCardSourceException.class, () -> {
            commonTable.drawCardResourcesDeck(player);
        });
    }

    /**
     * This test checks if a card is correctly drawn from the common objectives and added to the player's hand.
     */
    @Test
    void testDrawCardOpenGold() throws EmptyCardSourceException {
        commonTable.initialize(players);
        int initialSize = player.getPlayingHand().size();
        commonTable.drawCardOpenGold(0, player);
        assertEquals(initialSize + 1, player.getPlayingHand().size());
    }

    /**
     * This test checks if an EmptyCardSourceException is thrown when trying to draw a card from an empty common objectives.
     */
    @Test
    void testDrawCardOpenGold_Empty() {
        commonTable.initialize(players);
        commonTable.getOpenGold().set(0, null);
        assertThrows(EmptyCardSourceException.class, () -> {
            commonTable.drawCardOpenGold(0, player);
        });
    }

    /**
     * This test checks if a card is correctly drawn from the open resources and added to the player's hand.
     */
    @Test
    void testDrawCardOpenResources() throws EmptyCardSourceException {
        commonTable.initialize(players);
        int initialSize = player.getPlayingHand().size();
        commonTable.drawCardOpenResources(0, player);
        assertEquals(initialSize + 1, player.getPlayingHand().size());
    }

    /**
     * This test checks if an EmptyCardSourceException is thrown when trying to draw a card from an empty open resources.
     */
    @Test
    void testDrawCardOpenResources_Empty() {
        commonTable.initialize(players);
        commonTable.getOpenResources().set(0, null);
        assertThrows(EmptyCardSourceException.class, () -> {
            commonTable.drawCardOpenResources(0, player);
        });
    }

    /**
     * This test checks if the defined deck initialization is correctly done.
     */
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
