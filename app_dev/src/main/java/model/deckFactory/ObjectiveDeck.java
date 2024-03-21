package model.deckFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.GoldCard;

import java.util.ArrayList;

public class ObjectiveDeck  extends Deck{
    @Override
    public void generate() {
        cards = new ArrayList<>();  // Initialize cards list



        for (int i = 87; i <= 102; i++) {
            try {
                ObjectiveCard card = ObjectiveCard.parse(i);
                addCard(card);
            } catch (JsonProcessingException e) {

                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }



    // Static method to create a GoldenDeck instance and check if it's finished

    public static boolean isDeckFinished(ObjectiveDeck deck) {
        return deck.checkDeck();
    }

}
