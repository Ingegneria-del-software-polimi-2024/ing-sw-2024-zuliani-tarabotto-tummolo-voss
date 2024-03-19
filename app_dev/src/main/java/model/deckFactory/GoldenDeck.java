package model.deckFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.cards.PlayableCards.GoldCard;

import java.util.ArrayList;

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

    public int isDeckFinished(){

        return remainingCards
    }
}
