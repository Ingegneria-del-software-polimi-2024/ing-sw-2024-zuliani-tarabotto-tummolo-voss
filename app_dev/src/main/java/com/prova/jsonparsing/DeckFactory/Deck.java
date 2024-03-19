package com.prova.jsonparsing.DeckFactory;

import com.prova.jsonparsing.cardsImplementation.Card;

import java.util.Collections;
import java.util.List;

public abstract class Deck {

    protected List<Card> cards; // List to store cards

    public abstract void generate();

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card extract() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty!");
        }
        return cards.remove(0); // Extract and remove the first card (head/beginning of list)
    }

    public void addCard(Card card) {
        cards.add(card); // Add card to the back (tail/end of list)
    }

    public int getSize() {
        return cards.size();
    }
}
