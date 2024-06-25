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
                card.buildBackCorners();
            } catch (JsonProcessingException e) {

                e.printStackTrace();
            }
        }
    }
}