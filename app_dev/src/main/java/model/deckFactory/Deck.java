package model.deckFactory;
import model.cards.*;

// Abstract class defining common deck properties and functionalities
public abstract class Deck {
    public Card[] cards; // Array to store cards

    public abstract void generate();

    public void shuffle() {
        // Default shuffling logic
    }

    public void extract() {
        // Default card extraction logic
    }

    public void addCard(Card card) {
        // Implement logic to add the card to the cards array
        // (e.g., check for available space, resize the array if needed)
    }
}
