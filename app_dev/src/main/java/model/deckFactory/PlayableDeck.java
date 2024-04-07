package model.deckFactory;

import model.cards.PlayableCards.PlayableCard;

import java.util.Collections;
import java.util.List;

public abstract class PlayableDeck implements Deck{
    protected List<PlayableCard> cards;

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }
    @Override
    public int getSize() {
        return cards.size();
    }

    public PlayableCard extract() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty!");
        }
        PlayableCard extracted;
        extracted = cards.get(0);
        cards.remove(0);
        return extracted; // Extract and remove the first card (head/beginning of list)
    }

    public void addCard(PlayableCard card) {
        cards.add(card); // Add card to the back (tail/end of list)
    }

    //just for test purposes
    public PlayableCard getCard(int id) throws IllegalArgumentException{
        for(PlayableCard c : cards)
            if(c.getId() == id)
                return c;
        throw new IllegalArgumentException("WTFFFF, card "+id+" not found");
    }

}
