package com.prova.jsonparsing.DeckFactory.Generators;

import com.prova.jsonparsing.DeckFactory.Deck;
import com.prova.jsonparsing.DeckFactory.Generators.DeckGenerator;
import com.prova.jsonparsing.DeckFactory.GoldDeck;

public class GoldCardsDeckGenerator extends DeckGenerator {
    @Override
    public Deck generateDeckBasedOnType(){
        return new GoldDeck();
    }
}
