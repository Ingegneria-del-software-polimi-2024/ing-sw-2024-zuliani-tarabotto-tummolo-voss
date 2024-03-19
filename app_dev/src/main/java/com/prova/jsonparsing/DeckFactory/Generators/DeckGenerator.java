package com.prova.jsonparsing.DeckFactory.Generators;

import com.prova.jsonparsing.DeckFactory.Deck;

public abstract class DeckGenerator {

    public Deck generateDeck(){
        Deck deck = generateDeckBasedOnType();
        deck.generate();
        return deck;
    }

    public abstract Deck generateDeckBasedOnType();



}
