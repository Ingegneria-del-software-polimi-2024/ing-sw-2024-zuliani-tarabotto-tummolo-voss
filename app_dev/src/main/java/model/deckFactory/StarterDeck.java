package model.deckFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.cards.PlayableCards.StarterCard;

import java.util.ArrayList;

public class StarterDeck extends Deck{
    @Override
    public void generate() {
        cards = new ArrayList<>();  // Initialize cards list

        for (int i = 81; i <= 86; i++) {
            try {
                StarterCard card = StarterCard.parse(i);
                addCard(card);
            } catch (JsonProcessingException e) {

                e.printStackTrace();
            }
        }
    }



    // Static method to create a GoldenDeck instance and check if it's finished

    public static boolean isDeckFinished(StarterDeck deck) {
        return deck.checkDeck();
    }
}
