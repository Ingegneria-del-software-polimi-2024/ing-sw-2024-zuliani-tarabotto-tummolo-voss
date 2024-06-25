package model.deckFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.cards.Card;
import model.cards.PlayableCards.GoldCard;
import model.cards.PlayableCards.PlayableCard;

import java.util.ArrayList;
import java.util.Collections;

public class GoldenDeck extends PlayableDeck{
    @Override
    public void generate() {
        cards = new ArrayList<>();  // Initialize cards list

        for (int i = 41; i <= 80; i++) {
            try {
                GoldCard card = GoldCard.parse(i);
                addCard(card);
                card.buildBackCorners();
            } catch (JsonProcessingException e) {

                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
