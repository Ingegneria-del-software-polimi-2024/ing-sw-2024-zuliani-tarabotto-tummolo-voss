package com.prova.jsonparsing;

import com.prova.jsonparsing.DeckFactory.*;
import com.prova.jsonparsing.DeckFactory.Generators.DeckGenerator;
import com.prova.jsonparsing.DeckFactory.Generators.GoldCardsDeckGenerator;
import com.prova.jsonparsing.DeckFactory.Generators.ResourceCardsDeckGenerator;
import com.prova.jsonparsing.DeckFactory.Generators.StarterCardsDeckGenerator;
import com.prova.jsonparsing.cardsImplementation.PlayableCards.ResourceCard;

public class GameState {

    public static void main(String[] args) throws Exception {

        DeckGenerator resourcesDeckGenerator = new ResourceCardsDeckGenerator();
        DeckGenerator goldenDeckGenerator = new GoldCardsDeckGenerator();
        DeckGenerator starterDeckGenerator = new StarterCardsDeckGenerator();
        Deck resourcesDeck = resourcesDeckGenerator.generateDeck();


        //to shuffle
        //resourcesDeck.shuffle();

        System.out.println("Deck:");
        // Loop through cards and print them
        for (int i = 1; i <= resourcesDeck.getSize(); i++) {
            ResourceCard card = (ResourceCard) resourcesDeck.extract(); // Extract and print
            System.out.println(card.getId()); // Assuming card.toString() is implemented
            resourcesDeck.addCard(card); // Add card back to the deck
        }



        Deck goldenDeck = goldenDeckGenerator.generateDeck();
        Deck starterDeck = starterDeckGenerator.generateDeck();
    }
}
