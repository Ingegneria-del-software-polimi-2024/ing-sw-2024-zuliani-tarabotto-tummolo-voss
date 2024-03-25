package model.deckFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.GoldCard;
import model.cards.PlayableCards.PlayableCard;
import model.objective.Objective;

import java.util.ArrayList;
import java.util.Collections;

public class ObjectiveDeck  implements Deck{

    private ArrayList<ObjectiveCard> cards;
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

    //public static boolean isDeckFinished(ObjectiveDeck deck) {
    //    return deck.checkDeck();
    //}

    public void addCard(ObjectiveCard card) {
        cards.add(card);
    }

    public int getSize() {
        return cards.size();
    }
    public void shuffle() {
        Collections.shuffle(cards);
    }
    public ObjectiveCard extract() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty!");
        }
        return cards.remove(0); // Extract and remove the first card (head/beginning of list)
    }

}
