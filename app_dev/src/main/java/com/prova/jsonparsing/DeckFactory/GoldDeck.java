package com.prova.jsonparsing.DeckFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prova.jsonparsing.cardsImplementation.PlayableCards.GoldCard;
import com.prova.jsonparsing.cardsImplementation.PlayableCards.ResourceCard;

import java.util.ArrayList;

public class GoldDeck extends Deck{
    @Override
    public void generate() {
        cards = new ArrayList<>();  // Initialize cards list



       for (int i = 41; i <= 80; i++) {
            try {
                GoldCard card = GoldCard.parse(i);
                addCard(card);
            } catch (JsonProcessingException e) {

                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
