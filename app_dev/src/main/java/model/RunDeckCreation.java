package model;

import model.cards.PlayableCards.ResourceCard;
import model.deckFactory.Deck;
import model.deckFactory.Generators.DeckGenerator;
import model.deckFactory.Generators.GoldCardsDeckGenerator;
import model.deckFactory.Generators.ResourceCardsDeckGenerator;
import model.deckFactory.Generators.StarterCardsDeckGenerator;

/*public class RunDeckCreation {

    public void RunDeckCreation() throws Exception {

        DeckGenerator resourcesDeckGenerator = new ResourceCardsDeckGenerator();
        DeckGenerator goldenDeckGenerator = new GoldCardsDeckGenerator();
        DeckGenerator starterDeckGenerator = new StarterCardsDeckGenerator();
        Deck resourcesDeck = resourcesDeckGenerator.generateDeck();


        //to shuffle
        //resourcesDeck.shuffle();

        System.out.println("Deck:");
        // Loop through cards and print them
        for (int i = 1; i <= resourcesDeck.getSize(); i++) {
            ResourceCard card = (ResourceCard) resourcesDeck.extract(); // Extract and print
            System.out.println(card.getId());
            //How to check if corner exist (in the function getCorner)
            if((card.getCorner(2)) != null){
                System.out.println(card.getCorner(2).getArtifact());
            }
            resourcesDeck.addCard(card); // Add card back to the deck
        }



        Deck goldenDeck = goldenDeckGenerator.generateDeck();
        Deck starterDeck = starterDeckGenerator.generateDeck();
    }
}*/
