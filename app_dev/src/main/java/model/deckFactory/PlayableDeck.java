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

    /**
     * the first card in the deck gets removed and returned
     * @return extracted
     */
    public PlayableCard extract() {
        PlayableCard extracted;
        extracted = cards.get(0);
        cards.remove(0);
        return extracted;
    }

    /**
     * adds card to the back (tail/end) of cards list
     * @param card
     */
    public void addCard(PlayableCard card) {
        cards.add(card);
    }



    ////////////// GETTER METHODS ////////////////////////

    public List<PlayableCard> getCards() { return cards; }
    @Override
    public int getSize() {
        return cards.size();
    }


}
