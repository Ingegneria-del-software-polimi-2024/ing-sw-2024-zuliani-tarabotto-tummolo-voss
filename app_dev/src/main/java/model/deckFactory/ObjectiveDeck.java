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

    /**
     * adds card to the back (tail/end) of cards list
     * @param card
     */
    public void addCard(ObjectiveCard card) {
        cards.add(card);
    }

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * the first card in the deck gets removed and returned
     * @return extracted
     */
    public ObjectiveCard extract() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty!");
        }
        ObjectiveCard extracted;
        extracted = cards.get(0);
        cards.remove(0);
        return extracted; // Extract and remove the first card (head/beginning of list)
    }



    ///////////////////// GETTER METHODS ////////////////////////////////////////////
    @Override
    public int getSize() {
        return cards.size();
    }
    public ObjectiveCard get(int i) {
        return cards.get(i);
    }
    public ArrayList<ObjectiveCard> getCards(){ return cards;}
}
