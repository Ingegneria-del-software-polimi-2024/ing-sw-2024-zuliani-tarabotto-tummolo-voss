package model.placementArea;

import model.GameState.GameState;
import model.cards.Card;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.Deck;
import model.deckFactory.ObjectiveDeck;
import model.deckFactory.PlayableDeck;
import model.enums.Artifact;
import model.enums.Element;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AddCardTest {

    private static GameState gameState;

    //initializes the gamestate (please always specify one player), inserts the cards expressed from cli
    // in the placementArea of the palyer, the starterCard will be automatically added
    public static void initialize(ArrayList<Integer> cardList, ArrayList<Coordinates> coord, boolean face, int starterCard, boolean faceStarterCard) {
        String id = "test4Objectives";
        ArrayList<String> nickNames = new ArrayList<String>();
        int numPlayers = 1;
        String name = "ciao";
        PlayableDeck deck;

        for (int i = 0; i < numPlayers; i++)
            nickNames.add(name);


        //creates a GameState

        gameState = new GameState(nickNames, id);
        PlayableCard cardToBePlaced = getCard(gameState, starterCard);
        cardToBePlaced.setFaceSide(faceStarterCard);
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
            System.out.println("element:");
            System.out.println("            animals: "+gameState.getTurnPlayer().getPlacementArea().getNumberElements(Element.animals));
            System.out.println("            insects: "+gameState.getTurnPlayer().getPlacementArea().getNumberElements(Element.insects));
            System.out.println("            mushrooms: "+gameState.getTurnPlayer().getPlacementArea().getNumberElements(Element.mushrooms));
            System.out.println("            vegetals: "+gameState.getTurnPlayer().getPlacementArea().getNumberElements(Element.vegetals));
        }
    }

    //public static int checkForObjectives(GameState state, ObjectiveCard obj){return obj.countPoints(state.getTurnPlayer().getPlacementArea());}

    public static void runTest(int[] cards, int[] coordinates, int expectedResults, boolean face, int starterCard, boolean faceStarterCard, Coordinates testCoord, int testCardID){
        ArrayList<Coordinates> coord_a = new ArrayList<Coordinates>();
        ArrayList<Integer> cardList_a = new ArrayList<Integer>();


        for(int i = 0; i < cards.length; i++)
            cardList_a.add(cards[i]);

        for(Integer c : cardList_a)
            System.out.println(c);

        for(int i = 0; i < 2*cards.length; i++)
            if(i%2 == 0)
                coord_a.add(new Coordinates(coordinates[i], coordinates[i+1]));

        for (Coordinates c : coord_a)
            System.out.println(c.getX()+ " "+c.getY());

        initialize(cardList_a, coord_a, face, starterCard, faceStarterCard);

        //addCard test

        int cardPoint = gameState.getTurnPlayer().getPlacementArea().addCard(testCoord, getCard(gameState, testCardID));

        assertEquals(expectedResults, cardPoint);

        System.out.println("testing card n. "+testCardID);
        System.out.println("points: "+ gameState.getTurnPlayer().getPoints());
        System.out.println("testing card point "+cardPoint);
        System.out.println("artifacts:");
        System.out.println("            feather: "+gameState.getTurnPlayer().getPlacementArea().getNumberArtifacts(Artifact.feather));
        System.out.println("            ink: "+gameState.getTurnPlayer().getPlacementArea().getNumberArtifacts(Artifact.ink));
        System.out.println("            paper: "+gameState.getTurnPlayer().getPlacementArea().getNumberArtifacts(Artifact.paper));
        System.out.println("element:");
        System.out.println("            animals: "+gameState.getTurnPlayer().getPlacementArea().getNumberElements(Element.animals));
        System.out.println("            insects: "+gameState.getTurnPlayer().getPlacementArea().getNumberElements(Element.insects));
        System.out.println("            mushrooms: "+gameState.getTurnPlayer().getPlacementArea().getNumberElements(Element.mushrooms));
        System.out.println("            vegetals: "+gameState.getTurnPlayer().getPlacementArea().getNumberElements(Element.vegetals));
    }

    //insert 999 when asking "what objective id do you want to check? " if you want to stop
    public static void main(String[] args){

        //test commond card
        int[] a = {13};
        int[] c_a = {1,1};
        int exp_a = 0;
        int starterCard = 81;
        int testCardID = 14;
        Coordinates testCoord = new Coordinates(-1,-1);

        runTest(a, c_a, exp_a, false, starterCard, true, testCoord, testCardID);

        //test commond card with points
        int[] b = {16, 17};
        int[] c_b = {-1,1, -2,2};
        int exp_b = 1;
        int starterCard_b = 82;
        int testCardID_b = 18;
        Coordinates testCoord_b = new Coordinates(-3,3);

        runTest(b, c_b, exp_b, true, starterCard_b, true, testCoord_b, testCardID_b);

        //test gold cards
        int[] c = {16, 17, 18};
        int[] c_c = {-1,1, -2,2, -3,3};
        int exp_c = 0;
        int starterCard_c = 82;
        int testCardID_c = 14;
        Coordinates testCoord_c = new Coordinates(-1,-1);

        runTest(c, c_c, exp_c, true, starterCard_c, true, testCoord_c, testCardID_c);

        /*test commond card with points
        int[] d = {16, 17, 18};
        int[] c_d = {-1,1, -2,2, -3,3};
        int exp_d = 0;
        int starterCard_d = 82;
        int testCardID_d = 14;
        Coordinates testCoord_d = new Coordinates(-1,-1);

        runTest(d, c_d, exp_d, false, starterCard_d, true, testCoord_d, testCardID_d);*/

        //test gold card with constrains
        int[] e = {22, 1, 20};
        int[] c_e = {1,1, 2,2, 3,3};
        int exp_e = 1;
        int starterCard_e = 81;
        int testCardID_e = 41;
        Coordinates testCoord_e = new Coordinates(4,4);

        runTest(e, c_e, exp_e, true, starterCard_e, true, testCoord_e, testCardID_e);

    }
}