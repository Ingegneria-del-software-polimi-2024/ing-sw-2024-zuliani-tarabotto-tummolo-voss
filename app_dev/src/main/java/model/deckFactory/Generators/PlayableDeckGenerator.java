package model.deckFactory.Generators;

import model.deckFactory.Deck;
import model.deckFactory.PlayableDeck;

public abstract class PlayableDeckGenerator {


        public PlayableDeck generateDeck(){
            PlayableDeck deck = generateDeckBasedOnType();
            deck.generate();
            return deck;
        }

        public abstract PlayableDeck generateDeckBasedOnType();


}
