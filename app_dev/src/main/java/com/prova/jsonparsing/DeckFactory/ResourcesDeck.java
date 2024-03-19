package com.prova.jsonparsing.DeckFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prova.jsonparsing.cardsImplementation.PlayableCards.ResourceCard;

import java.util.ArrayList;

public class ResourcesDeck extends Deck {

    @Override
    public void generate() {
        cards = new ArrayList<>();  // Initialize cards list

        for (int i = 1; i <= 40; i++) {
            try {
                ResourceCard card = ResourceCard.parse(i);
                addCard(card);
            } catch (JsonProcessingException e) {

                e.printStackTrace();
            }
        }
    }
}
