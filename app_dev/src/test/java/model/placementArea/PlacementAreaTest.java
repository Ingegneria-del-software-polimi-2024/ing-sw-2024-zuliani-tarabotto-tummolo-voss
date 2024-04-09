package model.placementArea;

import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.Generators.PlayableDeckGenerator;
import model.deckFactory.Generators.StarterCardsDeckGenerator;
import model.deckFactory.PlayableDeck;
import org.junit.Assert;
import org.junit.Test;
import java.lang.*;
import java.io.PrintStream;

import static org.junit.Assert.assertArrayEquals;

class PlacementAreaTest {

    PlayableDeckGenerator starterDeckGenerator = new StarterCardsDeckGenerator();
    PlayableDeck startingDeck = starterDeckGenerator.generateDeck();
    System.out.println("Deck:");
    // Loop through cards and print them
    for(int i = 1; i <= startingDeck.getSize(); i++) {

        PlayableCard playableCard = startingDeck.extract();
        //Extract and print
        System.out.println(playableCard.getId());
        startingDeck.addCard(playableCard);
        //Add card back to the deck
    }

    PlacementArea prova = new PlacementArea();

    @org.junit.jupiter.api.Test
    void freePositions() {
    }

    @org.junit.jupiter.api.Test
    void addCard() {

        //assertArrayEquals(,prova.addcard(0.5, ));
    }

    @org.junit.jupiter.api.Test
    void testAddCard() {
    }

    @org.junit.jupiter.api.Test
    void getNumberArtifacts() {
    }

    @org.junit.jupiter.api.Test
    void getNumberElements() {
    }

    @org.junit.jupiter.api.Test
    void getAllArtifactsNumber() {
    }

    @org.junit.jupiter.api.Test
    void getAllElementsNumber() {
    }

    @org.junit.jupiter.api.Test
    void getNumberNearbyCards() {
    }

    @org.junit.jupiter.api.Test
    void printDisposition() {
    }

    @org.junit.jupiter.api.Test
    void printAvailablePlaces() {
    }
}