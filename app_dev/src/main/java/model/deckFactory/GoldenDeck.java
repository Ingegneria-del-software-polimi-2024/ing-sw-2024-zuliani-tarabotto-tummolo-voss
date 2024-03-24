package model.deckFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.cards.Card;
import model.cards.PlayableCards.GoldCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoldenDeck extends Deck{




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

    public boolean checkDeck() {
        return cards.isEmpty();
    }

    // Static method to create a GoldenDeck instance and check if it's finished

    public static boolean isDeckFinished(GoldenDeck deck) {
        return deck.checkDeck();
    }

}
