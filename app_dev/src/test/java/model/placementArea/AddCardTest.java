package model.placementArea;

import model.GameState.GameState;
import model.cards.Card;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.Deck;
import model.deckFactory.ObjectiveDeck;
import model.deckFactory.PlayableDeck;
import model.enums.Artifact;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AddCardTest {

    private static GameState gameState;

    static int ID = 81;

    //initializes the gamestate (please always specify one player), inserts the cards expressed from cli
    // in the placementArea of the palyer, the starterCard will be automatically added
    public static void initialize(ArrayList<Integer> cardList, ArrayList<Coordinates> coord, boolean face) {
        String id = "test4Objectives";
        ArrayList<String> nickNames = new ArrayList<String>();
        int numPlayers = 1;
        String name = "ciao";
        PlayableDeck deck;

        for (int i = 0; i < numPlayers; i++)
            nickNames.add(name);


        //creates a GameState
        gameState = new GameState(nickNames, id);

        PlayableCard cardToBePlaced = getCard(gameState, ID);
        cardToBePlaced.setFaceSide(false);
        gameState.getTurnPlayer().getPlacementArea().addCard(cardToBePlaced);
        System.out.println("starter card placed");

        putCards(cardList, coord, face);
    }

    private static PlayableCard getCard(GameState state, int id) throws RuntimeException{
        PlayableDeck deck;

        //check if the card is contatined in the hand of the player or in the open resoruces/gold
        for(PlayableCard c : state.getOpenGold())
            if(c.getId() == id)
                return c;
        for(PlayableCard c : state.getOpenResources())
            if(c.getId()==id)
                return c;
        for(PlayableCard c : state.getTurnPlayer().getPlayingHand())
            if(c.getId()==id)
                return c;
        if(state.getTurnPlayer().getStarterCard().getId() == id)
            return state.getTurnPlayer().getStarterCard();
        if (0 <= id && id <= 40)
            deck = state.getResourceDeck();
        else if (40 < id && id < 81)
            deck = state.getGoldDeck();
        else if (81 <= id && id <= 85)
            deck = state.getStarterDeck();
        else
            throw new RuntimeException("couldn't find the deck");
        try {
            return deck.getCard(id);
        }catch(IllegalArgumentException e){
            throw new RuntimeException("couldn't find the deck");
        }
    }

    public static void putCards(ArrayList<Integer> cards, ArrayList<Coordinates> coordList, boolean face) throws RuntimeException{

        if(coordList.size() != cards.size())
            throw new RuntimeException("error in data inserted");

        for (int i=0; i< cards.size(); i++) {
            Coordinates coord = coordList.get(i);
            gameState.setSelectedCoordinates(coord);
            PlayableCard x = getCard(gameState, cards.get(i));
            if(x == null)
                throw new RuntimeException("error when getting the card with id: "+cards.get(i));

            System.out.println("putting card n. "+cards.get(i));

            gameState.setSelectedHandCard(x);
            gameState.setSelectedCardFace(face);
            gameState.playCard();
            System.out.println("points: "+ gameState.getTurnPlayer().getPoints());
            System.out.println("inserted card "+i);
            System.out.println("artifacts:");
            System.out.println("            feather: "+gameState.getTurnPlayer().getPlacementArea().getNumberArtifacts(Artifact.feather));
            System.out.println("            ink: "+gameState.getTurnPlayer().getPlacementArea().getNumberArtifacts(Artifact.ink));
            System.out.println("            paper: "+gameState.getTurnPlayer().getPlacementArea().getNumberArtifacts(Artifact.paper));

        }
    }
}