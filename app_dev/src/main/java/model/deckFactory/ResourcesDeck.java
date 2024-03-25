package model.deckFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.cards.PlayableCards.ResourceCard;

import java.util.ArrayList;

public class ResourcesDeck extends PlayableDeck {

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

    // Static method to create a GoldenDeck instance and check if it's finished

    //public static boolean isDeckFinished(ResourcesDeck deck) {
        //return deck.checkDeck();
    //}
}