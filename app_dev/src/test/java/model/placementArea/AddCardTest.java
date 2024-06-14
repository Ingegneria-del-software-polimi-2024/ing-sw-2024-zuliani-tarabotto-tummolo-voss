package model.placementArea;
import junit.framework.TestCase;
import model.GameState.GameState;
import model.cards.Card;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.Deck;
import model.deckFactory.Generators.GoldCardsDeckGenerator;
import model.deckFactory.Generators.ObjectiveCardsDeckGenerator;
import model.deckFactory.Generators.ResourceCardsDeckGenerator;
import model.deckFactory.Generators.StarterCardsDeckGenerator;
import model.deckFactory.ObjectiveDeck;
import model.deckFactory.PlayableDeck;
import model.enums.Artifact;
import model.enums.Element;
import org.junit.Test;

import java.util.*;
import java.util.Map.Entry;


import static org.junit.jupiter.api.Assertions.*;

/*public class AddCardTest {

    private static GameState gameState;

    //initializes the gamestate (please always specify one player), inserts the cards expressed from cli
    // in the placementArea of the palyer, the starterCard will be automatically added
    public static void initialize(ArrayList<Integer> cardList, ArrayList<Coordinates> coord, boolean face, int starterCard, boolean faceStarterCard) {
        String id = "test4Cards";
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
            if(c.getId() == id) {
                return c;
            }
        for(PlayableCard c : state.getOpenResources())
            if(c.getId()==id) {
                return c;
            }
        for(PlayableCard c : state.getTurnPlayer().getPlayingHand())
            if(c.getId()==id) {
                return c;
            }
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

    public static void runTest(int[] cards, int[] coordinates, int expectedResults, boolean face, int starterCard, boolean faceStarterCard, Coordinates testCoord, int testCardID, int expAnimals, int expInsects, int expMushrooms, int expVegetals){
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

        PlayableCard c = getCard(gameState, testCardID);
        c.setFaceSide(face);

        int cardPoint = gameState.getTurnPlayer().getPlacementArea().addCard(testCoord, c);

        int animals = PlacementArea.getNumberElements(Element.animals);
        int insects = PlacementArea.getNumberElements(Element.insects);
        int mushrooms = PlacementArea.getNumberElements(Element.mushrooms);
        int vegetals = PlacementArea.getNumberElements(Element.vegetals);

        int avaiblePosition = PlacementArea.getNAvaiblePositions();

        assert (expectedResults == cardPoint): "Errore";
        assert (expInsects == insects): "Errore";
        assert (expAnimals == animals): "Errore";
        assert (expMushrooms == mushrooms): "Errore";
        assert (expVegetals == vegetals): "Errore";


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
/*
int[] e = {22, 1, 20};
int[] c_e = {1,1, 2,2, 3,3};
int exp_e = 1;
int starterCard_e = 81;
int testCardID_e = 41;
Coordinates testCoord_e = new Coordinates(4,4);

runTest(e, c_e, exp_e, true, starterCard_e, true, testCoord_e, testCardID_e);

    }
            }*/

public class AddCardTest extends TestCase {

    public static void initializeStarterCard(PlacementArea area, Deck[] deckList, int starterCard, boolean faceStarterCard){
        PlayableCard cardToBePlaced = (PlayableCard) getCard(deckList, starterCard);
        cardToBePlaced.setFaceSide(faceStarterCard);
        area.addCard(cardToBePlaced);
        System.out.println("starter card placed");
    }
    public static int initialize(PlacementArea area, ArrayList<Integer> cardList, ArrayList<Coordinates> coord, boolean face, Deck[] deckList) {

        int lastCardPlacedPoints;


        System.out.println("artifacts:");
        System.out.println("            feather: "+area.getNumberArtifacts(Artifact.feather));
        System.out.println("            ink: "+area.getNumberArtifacts(Artifact.ink));
        System.out.println("            paper: "+area.getNumberArtifacts(Artifact.paper));
        System.out.println("element:");
        System.out.println("            animals: "+area.getNumberElements(Element.animals));
        System.out.println("            insects: "+area.getNumberElements(Element.insects));
        System.out.println("            mushrooms: "+area.getNumberElements(Element.mushrooms));
        System.out.println("            vegetals: "+area.getNumberElements(Element.vegetals));


        lastCardPlacedPoints = putCards(cardList, coord, face, area, deckList);

        return lastCardPlacedPoints;
    }

    private static Card getCard(Deck[] deckList, int id) throws RuntimeException{
        if(87<=id && id<=102) {
            ObjectiveDeck deck = (ObjectiveDeck) deckList[3];
            return deck.getCard(id);
        }
        PlayableDeck deck = null;

        if(0<=id && id<= 40){
            deck =(PlayableDeck) deckList[0];
        }else if(41<=id && id <=80){
            deck =(PlayableDeck)  deckList[1];
        }else if(81<=id && id<=86){
            deck =(PlayableDeck)  deckList[2];
        }
        if (deck == null)
            throw new RuntimeException("couldn't find the deck");
        try {
            return deck.getCard(id);
        }catch(IllegalArgumentException e){
            throw new RuntimeException("couldn't find the deck");
        }
    }

    public static int putCards(ArrayList<Integer> cards, ArrayList<Coordinates> coordList, boolean face, PlacementArea area, Deck[] deckList) throws RuntimeException{

        int cardPoint = 0;
        if(coordList.size() != cards.size())
            throw new RuntimeException("error in data inserted");

        for (int i=0; i< cards.size(); i++) {
            Coordinates coord = coordList.get(i);
            PlayableCard x = (PlayableCard) getCard(deckList, cards.get(i));
            System.out.println("putting card n. "+cards.get(i));
            x.setFaceSide(face);
            //area.addCard(coord, x);

            cardPoint = area.addCard(coord, x);


            System.out.println("inserted card "+i);
            System.out.println("card point: "+cardPoint);
            System.out.println("artifacts:");
            System.out.println("            feather: "+area.getNumberArtifacts(Artifact.feather));
            System.out.println("            ink: "+area.getNumberArtifacts(Artifact.ink));
            System.out.println("            paper: "+area.getNumberArtifacts(Artifact.paper));
            System.out.println("element:");
            System.out.println("            animals: "+area.getNumberElements(Element.animals));
            System.out.println("            insects: "+area.getNumberElements(Element.insects));
            System.out.println("            mushrooms: "+area.getNumberElements(Element.mushrooms));
            System.out.println("            vegetals: "+area.getNumberElements(Element.vegetals));

        }

        List<Coordinates> availablePosition = area.getAvailablePlaces();
        HashMap<Coordinates, PlayableCard> disposition = area.getDisposition();
        int numberNearbyCards = area.getNumberNearbyCards();

        for (Coordinates c: disposition.keySet()){
            System.out.println("X: " + c.getX() + ", Y: " + c.getY());
            disposition.get(c).getId();
        }

        // Iterate through the list and print each element
        for (Coordinates position : availablePosition) {
            System.out.println(position);
        }

        System.out.println("disposition");
        System.out.println("numberNearbyCards: "+numberNearbyCards);

        return cardPoint;
    }


    public void testSingoli(){

        GoldCardsDeckGenerator gdGenerator = new GoldCardsDeckGenerator();
        ObjectiveCardsDeckGenerator objGenerator = new ObjectiveCardsDeckGenerator();
        ResourceCardsDeckGenerator rcGenerator = new ResourceCardsDeckGenerator();
        StarterCardsDeckGenerator stGenerator = new StarterCardsDeckGenerator();

        ObjectiveDeck objDeck = objGenerator.generateDeck();
        PlayableDeck resDeck = rcGenerator.generateDeck();
        PlayableDeck gldDeck = gdGenerator.generateDeck();
        PlayableDeck strDeck = stGenerator.generateDeck();

        Deck[] deckList = {resDeck, gldDeck, strDeck, objDeck};

        //test a particolar position

        int[] cards_a = {4, 11, 21};
        int[] coordinates_a = {1,1, 1,-1, 2,0};
        int expectedResults_a= 0;
        boolean face_a = true;
        int starterCard_a = 82;
        boolean faceStarterCard_a = true;
        Coordinates testCoord_a = new Coordinates(3, 0);
        int testPointCardID_a = 21;
        int expAnimals_a = 3;
        int expInsects_a = 0; //prima 2 (ricontrallare)
        int expMushrooms_a = 2;
        int expVegetals_a = 2;
        int expFeather_a = 0;
        int expInk_a = 0;
        int expPaper_a = 0;

        List<Coordinates> expAvailablePosition_a = new ArrayList<>();
        Coordinates coord1 = new Coordinates(-1, 1);
        Coordinates coord2 = new Coordinates(-1, -1);
        Coordinates coord3 = new Coordinates(1, -2);
        Coordinates coord4 = new Coordinates(2, 2);
        Coordinates coord5 = new Coordinates(3, 1);
        expAvailablePosition_a.add(coord1);
        expAvailablePosition_a.add(coord2);
        expAvailablePosition_a.add(coord3);
        expAvailablePosition_a.add(coord4);
        expAvailablePosition_a.add(coord5);

        HashMap<Coordinates, PlayableCard> expDisposition_a = new HashMap<>();
        Coordinates coord_1 = new Coordinates(0, 0);
        PlayableCard card_1 = (PlayableCard) getCard(deckList, 81);
        Coordinates coord_2 = new Coordinates(1, 1);
        PlayableCard card_2 = (PlayableCard) getCard(deckList,4);
        Coordinates coord_3 = new Coordinates(1, -1);
        PlayableCard card_3 = (PlayableCard) getCard(deckList, 11);
        Coordinates coord_4 = new Coordinates(2, 0);
        PlayableCard card_4 = (PlayableCard) getCard(deckList, 21);

        expDisposition_a.put(coord_1, card_1);
        expDisposition_a.put(coord_2, card_2);
        expDisposition_a.put(coord_3, card_3);
        expDisposition_a.put(coord_4, card_4);

        int expNumberNearbyCards_a = 2;

        runTest(cards_a, coordinates_a, expectedResults_a, face_a, starterCard_a, faceStarterCard_a, testCoord_a, testPointCardID_a,
                expAnimals_a, expInsects_a, expMushrooms_a, expVegetals_a, expFeather_a, expInk_a, expPaper_a, expAvailablePosition_a, expDisposition_a, expNumberNearbyCards_a);


        //test b artifacts

        /*int[] cards_b = {25, 36};
        int[] coordinates_b = {1,1, 2,-2};
        int expectedResults_b = 2;
        boolean face_b = true;
        int starterCard_b = 81;
        boolean faceStarterCard_b = true;
        Coordinates testCoord_b = new Coordinates(1,1);
        int testPointCardID_b = 0;
        int expAnimals_b = 1;
        int expInsects_b = 1;
        int expMushrooms_b = 2;
        int expVegetals_b = 1;
        int expFeather_b = 1;
        int expInk_b = 0;
        int expPaper_b = 1;

        List<Coordinates> expAvailablePosition_b = new ArrayList<>();
        Coordinates coord6 = new Coordinates(-1, 1);
        Coordinates coord7 = new Coordinates(-1, -1);
        Coordinates coord8 = new Coordinates(2, -2);
        Coordinates coord9 = new Coordinates(2, 0);
        Coordinates coord10 = new Coordinates(2, -2);
        expAvailablePosition_a.add(coord6);
        expAvailablePosition_a.add(coord7);
        expAvailablePosition_a.add(coord8);
        expAvailablePosition_a.add(coord9);
        expAvailablePosition_a.add(coord10);

        HashMap<Coordinates, PlayableCard> expDisposition_b = new HashMap<>();
        Coordinates coord_5 = new Coordinates(0, 0);
        PlayableCard card_5 = (PlayableCard) getCard(deckList, 81);
        Coordinates coord_6 = new Coordinates(1, 1);
        PlayableCard card_6 = (PlayableCard) getCard(deckList,25);
        Coordinates coord_7 = new Coordinates(2, -2);
        PlayableCard card_7 = (PlayableCard) getCard(deckList, 36);

        expDisposition_a.put(coord_5, card_5);
        expDisposition_a.put(coord_6, card_6);
        expDisposition_a.put(coord_7, card_7);

        int expNumberNearbyCards_b = 2;

        runTest(cards_b, coordinates_b, expectedResults_b, face_b, starterCard_b, faceStarterCard_b, testCoord_b, testPointCardID_b,
                expAnimals_b, expInsects_b, expMushrooms_b, expVegetals_b, expFeather_b, expInk_b, expPaper_b, expAvailablePosition_b, expDisposition_b, expNumberNearbyCards_b);


        //test c constrains 1

        int[] cards_c = {33, 24, 72};
        int[] coordinates_c = {1,1, 1,-1, 2,-2};
        int expectedResults_c = 0;
        boolean face_c = true;
        int starterCard_c = 81;
        boolean faceStarterCard_c = true;
        Coordinates testCoord_c = new Coordinates(2, -2); // Replace with desired coordinates
        int testPointCardID_c = 72;
        int expAnimals_c = 1;
        int expInsects_c = 4;
        int expMushrooms_c = 0;
        int expVegetals_c = 0;
        int expFeather_c = 0;
        int expInk_c = 0;
        int expPaper_c = 1;

        List<Coordinates> expAvailablePosition_c = new ArrayList<>();
        Coordinates coord11 = new Coordinates(0, 2);
        Coordinates coord12 = new Coordinates(2, 0);
        Coordinates coord13 = new Coordinates(2, -2);
        Coordinates coord14 = new Coordinates(-1, 1);
        Coordinates coord15 = new Coordinates(-1, -1);
        expAvailablePosition_a.add(coord11);
        expAvailablePosition_a.add(coord12);
        expAvailablePosition_a.add(coord13);
        expAvailablePosition_a.add(coord14);
        expAvailablePosition_a.add(coord15);

        HashMap<Coordinates, PlayableCard> expDisposition_c = new HashMap<>();
        Coordinates coord_8 = new Coordinates(0, 0);
        PlayableCard card_8 = (PlayableCard) getCard(deckList, 81);
        Coordinates coord_9 = new Coordinates(1, -1);
        PlayableCard card_9 = (PlayableCard) getCard(deckList,33);
        Coordinates coord_10 = new Coordinates(1, -1);
        PlayableCard card_10= (PlayableCard) getCard(deckList, 24);
        Coordinates coord_11 = new Coordinates(2, -2);
        PlayableCard card_11= (PlayableCard) getCard(deckList, 72);

        expDisposition_a.put(coord_8, card_8);
        expDisposition_a.put(coord_9, card_9);
        expDisposition_a.put(coord_10, card_10);
        expDisposition_a.put(coord_11, card_11);

        int expNumberNearbyCards_c = 2;

        runTest(cards_c, coordinates_c, expectedResults_c, face_c, starterCard_c, faceStarterCard_c, testCoord_c, testPointCardID_c,
                expAnimals_c, expInsects_c, expMushrooms_c, expVegetals_c, expFeather_c, expInk_c, expPaper_c, expAvailablePosition_c, expDisposition_c, expNumberNearbyCards_c);



        //test d constrains 2

        int[] cards_d = {33, 24, 72, 74};
        int[] coordinates_d = {1,1, 1,-1, 2,-2, -1,1};
        int expectedResults_d = 2;
        boolean face_d = true;
        int starterCard_d = 81;
        boolean faceStarterCard_d = true;
        Coordinates testCoord_d = new Coordinates(-1, 1); // Replace with desired coordinates
        int testPointCardID_d = 74;
        int expAnimals_d = 1;
        int expInsects_d = 4;
        int expMushrooms_d = 0;
        int expVegetals_d = 0;
        int expFeather_d = 0;
        int expInk_d = 0;
        int expPaper_d = 1;

        List<Coordinates> expAvailablePosition_d = new ArrayList<>();
        coord11 = new Coordinates(0, 2);
        coord12 = new Coordinates(2, 0);
        coord13 = new Coordinates(2, -2);
        coord14 = new Coordinates(-1, 1);
        coord15 = new Coordinates(-2, 2);
        coord15 = new Coordinates(-1, -1);
        expAvailablePosition_a.add(coord11);
        expAvailablePosition_a.add(coord12);
        expAvailablePosition_a.add(coord13);
        expAvailablePosition_a.add(coord14);
        expAvailablePosition_a.add(coord15);
        expAvailablePosition_a.add(coord15);

        HashMap<Coordinates, PlayableCard> expDisposition_d = new HashMap<>();
        coord_8 = new Coordinates(0, 0);
        card_8 = (PlayableCard) getCard(deckList, 81);
        coord_9 = new Coordinates(1, -1);
        card_9 = (PlayableCard) getCard(deckList,33);
        coord_10 = new Coordinates(1, -1);
        card_10= (PlayableCard) getCard(deckList, 24);
        coord_11 = new Coordinates(2, -2);
        card_11= (PlayableCard) getCard(deckList, 72);
        Coordinates coord_12 = new Coordinates(-1, -1);
        PlayableCard card_12= (PlayableCard) getCard(deckList, 74);

        expDisposition_a.put(coord_8, card_8);
        expDisposition_a.put(coord_9, card_9);
        expDisposition_a.put(coord_10, card_10);
        expDisposition_a.put(coord_11, card_11);
        expDisposition_a.put(coord_11, card_11);
        expDisposition_a.put(coord_11, card_11);

        int expNumberNearbyCards_d = 2;

        runTest(cards_d, coordinates_d, expectedResults_d, face_d, starterCard_d, faceStarterCard_d, testCoord_d, testPointCardID_d,
                expAnimals_d, expInsects_d, expMushrooms_d, expVegetals_d, expFeather_d, expInk_d, expPaper_d, expAvailablePosition_d, expDisposition_d, expNumberNearbyCards_d);



        //test e contrains 3

        int[] cards_e = {33, 24, 72, 74, 79};
        int[] coordinates_e = {1,1, 1,-1, 2,-2, -1,1, -2,2};
        int expectedResults_e = 3;
        boolean face_e = true;
        int starterCard_e = 81;
        boolean faceStarterCard_e = true;
        Coordinates testCoord_e = new Coordinates(2, -2); // Replace with desired coordinates
        int testPointCardID_e = 72;
        int expAnimals_e = 1;
        int expInsects_e = 4;
        int expMushrooms_e = 0;
        int expVegetals_e = 0;
        int expFeather_e = 0;
        int expInk_e = 0;
        int expPaper_e = 1;

        List<Coordinates> expAvailablePosition_e = new ArrayList<>();
         coord11 = new Coordinates(0, 2);
         coord12 = new Coordinates(2, 0);
         coord13 = new Coordinates(2, -2);
         coord14 = new Coordinates(-1, 1);
         coord15 = new Coordinates(-3, 3);
         coord15 = new Coordinates(-1, -1);
         expAvailablePosition_a.add(coord11);
         expAvailablePosition_a.add(coord12);
         expAvailablePosition_a.add(coord13);
         expAvailablePosition_a.add(coord14);

        HashMap<Coordinates, PlayableCard> expDisposition_e = new HashMap<>();
        coord_8 = new Coordinates(0, 0);
        card_8 = (PlayableCard) getCard(deckList, 81);
        coord_9 = new Coordinates(1, -1);
        card_9 = (PlayableCard) getCard(deckList,33);
        coord_10 = new Coordinates(1, -1);
        card_10= (PlayableCard) getCard(deckList, 24);
        coord_11 = new Coordinates(2, -2);
        card_11= (PlayableCard) getCard(deckList, 72);
        coord_12 = new Coordinates(-1, 1);
        card_12= (PlayableCard) getCard(deckList, 74);
        coord_12 = new Coordinates(-2, -12);
        card_12= (PlayableCard) getCard(deckList, 79);

        expDisposition_a.put(coord_8, card_8);
        expDisposition_a.put(coord_9, card_9);
        expDisposition_a.put(coord_10, card_10);
        expDisposition_a.put(coord_11, card_11);
        expDisposition_a.put(coord_11, card_11);
        expDisposition_a.put(coord_11, card_11);
        expDisposition_a.put(coord_11, card_11);

        int expNumberNearbyCards_e = 2;

        runTest(cards_e, coordinates_e, expectedResults_e, face_e, starterCard_e, faceStarterCard_e, testCoord_e, testPointCardID_e,
                expAnimals_e, expInsects_e, expMushrooms_e, expVegetals_e, expFeather_e, expInk_e, expPaper_e, expAvailablePosition_e, expDisposition_e, expNumberNearbyCards_e);


        //test f commond points

        int[] cards_f = {1, 2, 3};
        int[] coordinates_f = {1, 2, 3};
        int expectedResults_f = 42;
        boolean face_f = true;
        int starterCard_f = 5;
        boolean faceStarterCard_f = false;
        Coordinates testCoord_f = new Coordinates(3, 4);
        int testPointCardID_f = 7;
        int expAnimals_f = 10;
        int expInsects_f = 5;
        int expMushrooms_f = 2;
        int expVegetals_f = 3;
        int expFeather_f = 1;
        int expInk_f = 0;
        int expPaper_f = 4;
        List<Coordinates> expAvailablePosition_f = new ArrayList<>();
        HashMap<Coordinates, PlayableCard> expDisposition_f = new HashMap<>();
        int expNumberNearbyCards_f = 2;

        runTest(cards_f, coordinates_f, expectedResults_f, face_f, starterCard_f, faceStarterCard_f, testCoord_f, testPointCardID_f,
                expAnimals_f, expInsects_f, expMushrooms_f, expVegetals_f, expFeather_f, expInk_f, expPaper_f, expAvailablePosition_f, expDisposition_f, expNumberNearbyCards_f);
*/
        //test g



        //test h



        //test i


    }

    public void runTest(int[] cards, int[] coordinates, int expectedResults, boolean face, int starterCard, boolean faceStarterCard, Coordinates testCoord, int testPointCardID, int expAnimals, int expInsects, int expMushrooms, int expVegetals, int expFeather, int expInk, int expPaper, List<Coordinates> expAvailablePosition, HashMap<Coordinates, PlayableCard> expDisposition, int expNumberNearbyCards){

        PlacementArea area = new PlacementArea();

        GoldCardsDeckGenerator gdGenerator = new GoldCardsDeckGenerator();
        ObjectiveCardsDeckGenerator objGenerator = new ObjectiveCardsDeckGenerator();
        ResourceCardsDeckGenerator rcGenerator = new ResourceCardsDeckGenerator();
        StarterCardsDeckGenerator stGenerator = new StarterCardsDeckGenerator();

        ObjectiveDeck objDeck = objGenerator.generateDeck();
        PlayableDeck resDeck = rcGenerator.generateDeck();
        PlayableDeck gldDeck = gdGenerator.generateDeck();
        PlayableDeck strDeck = stGenerator.generateDeck();

        Deck[] deckList = {resDeck, gldDeck, strDeck, objDeck};

        ArrayList<Coordinates> coord_a = new ArrayList<Coordinates>();
        ArrayList<Integer> cardList_a = new ArrayList<Integer>();

        //addcard startercard


        initializeStarterCard(area,deckList, starterCard, faceStarterCard);

        for(int i = 0; i < cards.length; i++)
            cardList_a.add(cards[i]);

        for(Integer c : cardList_a)
            System.out.println(c);

        for(int i = 0; i < 2*cards.length; i++)
            if(i%2 == 0)
                coord_a.add(new Coordinates(coordinates[i], coordinates[i+1]));

        for (Coordinates c : coord_a)
            System.out.println(c.getX()+ " "+c.getY());

        //addcard

        int cardPoint = initialize(area, cardList_a, coord_a, face, deckList);

        PlayableCard c = (PlayableCard) getCard(deckList, testPointCardID);
        c.setFaceSide(face);

        //int cardPoint = area.addCard(testCoord, c);

        int animals = area.getNumberElements(Element.animals);
        int insects = area.getNumberElements(Element.insects);
        int mushrooms = area.getNumberElements(Element.mushrooms);
        int vegetals = area.getNumberElements(Element.vegetals);

        int feather = area.getNumberArtifacts(Artifact.feather);
        int ink = area.getNumberArtifacts(Artifact.ink);
        int paper = area.getNumberArtifacts(Artifact.paper);

        List<Coordinates> availablePosition = area.getAvailablePlaces();

        HashMap<Coordinates, PlayableCard> disposition = area.getDisposition();

        int numberNearbyCards = area.getNumberNearbyCards();

        assert (expectedResults == cardPoint): "Errore";

        assertEquals (expInsects, insects);
        assert (expAnimals == animals): "Errore";
        assert (expMushrooms == mushrooms): "Errore";
        assert (expVegetals == vegetals): "Errore";

        for(Entry<Coordinates, PlayableCard> position : disposition.entrySet())
            assert (expDisposition.containsKey(position.getKey()) && expDisposition.get(position.getKey()).equals(position.getValue())) : "Errore";

        assert (expFeather == feather): "Errore";
        assert (expInk == ink): "Errore";
        assert (expPaper == paper): "Errore";

        assert (expDisposition == disposition): "Errore";

        assert (expAvailablePosition.containsAll(availablePosition) && availablePosition.containsAll(expAvailablePosition)): "Errore";

        assert (expAvailablePosition == availablePosition): "Errore";

        assert (expNumberNearbyCards == numberNearbyCards): "Errore";
    }

}