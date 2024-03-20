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
        return cards.remove(0); // Extract and remove the first card (head/beginning of list)
    }

    public void addCard(PlayableCard card) {
        cards.add(card); // Add card to the back (tail/end of list)
    }

}
