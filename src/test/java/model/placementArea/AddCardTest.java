package model.placementArea;
import junit.framework.TestCase;
import junit.framework.TestResult;
import model.Exceptions.CantPlaceCardException;
import model.Exceptions.KickOutOfGameException;
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

/**
 * This class is used to test the addition of cards to a placement area.
 */
public class AddCardTest extends TestCase {

    /**
     * This method is used to initialize a starter card in the placement area.
     * @param area The placement area where the card will be placed.
     * @param deckList The list of decks from which the card will be retrieved.
     * @param starterCard The ID of the starter card.
     * @param faceStarterCard The face side of the starter card.
     */
    public static void initializeStarterCard(PlacementArea area, Deck[] deckList, int starterCard, boolean faceStarterCard){
        PlayableCard cardToBePlaced = (PlayableCard) getCard(deckList, starterCard);
        cardToBePlaced.setFaceSide(faceStarterCard);
        try {
            area.addCard(cardToBePlaced);
        } catch (KickOutOfGameException e) {
            throw new RuntimeException(e);
        }
        System.out.println("starter card placed");
    }

    /**
     * This method is used to initialize the placement area with a list of cards.
     * @param area The placement area where the cards will be placed.
     * @param cardList The list of card IDs to be placed.
     * @param coord The list of coordinates where the cards will be placed.
     * @param face The face side of the cards.
     * @param deckList The list of decks from which the cards will be retrieved.
     * @return The total points of the placed cards.
     */
    public static int initialize(PlacementArea area, ArrayList<Integer> cardList, ArrayList<Coordinates> coord, boolean face, Deck[] deckList) {

        int cardsPoints;


        System.out.println("artifacts:");
        System.out.println("            feather: "+area.getNumberArtifacts(Artifact.feather));
        System.out.println("            ink: "+area.getNumberArtifacts(Artifact.ink));
        System.out.println("            paper: "+area.getNumberArtifacts(Artifact.paper));
        System.out.println("element:");
        System.out.println("            animals: "+area.getNumberElements(Element.animals));
        System.out.println("            insects: "+area.getNumberElements(Element.insects));
        System.out.println("            mushrooms: "+area.getNumberElements(Element.mushrooms));
        System.out.println("            vegetals: "+area.getNumberElements(Element.vegetals));


        cardsPoints = putCards(cardList, coord, face, area, deckList);

        return cardsPoints;
    }

    /**
     * Retrieves a card from the appropriate deck based on the card's ID.
     *
     * @param deckList The list of decks from which the card will be retrieved.
     * @param id The ID of the card to be retrieved.
     * @return The card retrieved from the deck.
     * @throws RuntimeException if the deck cannot be found or if the card cannot be retrieved from the deck.
     */
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

    /**
     * Places the cards in the placement area.
     *
     * @param cards The list of card IDs to be placed.
     * @param coordList The list of coordinates where the cards will be placed.
     * @param face The face side of the cards.
     * @param area The placement area where the cards will be placed.
     * @param deckList The list of decks from which the cards will be retrieved.
     * @return The total points of the placed cards.
     * @throws RuntimeException if the number of cards does not match the number of coordinates or if a card cannot be placed in the area.
     */
    public static int putCards(ArrayList<Integer> cards, ArrayList<Coordinates> coordList, boolean face, PlacementArea area, Deck[] deckList) throws RuntimeException{

        int cardsPoints = 0;
        if(coordList.size() != cards.size())
            throw new RuntimeException("error in data inserted");

        for (int i=0; i< cards.size(); i++) {
            Coordinates coord = coordList.get(i);
            PlayableCard x = (PlayableCard) getCard(deckList, cards.get(i));
            System.out.println("putting card n. "+cards.get(i));
            x.setFaceSide(face);
            //area.addCard(coord, x);

            try {
                cardsPoints = cardsPoints + area.addCard(coord, x);
            } catch (CantPlaceCardException e) {
                throw new RuntimeException(e);
            }


            System.out.println("inserted card "+i);
            System.out.println("card point: "+cardsPoints);
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

        return cardsPoints;
    }

    /**
     * This method is used to test the placement of cards in the area.
     *
     * It creates instances of various deck generators and generates the decks.
     * It then defines the parameters for two test cases (a and b) and runs these tests.
     * Each test case includes the cards to be placed, their coordinates, expected results, face side of the cards, starter card details, expected number of each element and artifact, expected available positions, expected disposition of cards, expected number of nearby cards, and details of any expected exceptions.
     * The tests are run using the `runTest` method.
     */
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
        PlayableCard card_1 = (PlayableCard) getCard(deckList, 82);
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

        int exceptionCoord_a[] = {0,0};
        int exceptionCard_a = 0;
        int exceptionCode_a = 0;

        runTest(cards_a, coordinates_a, expectedResults_a, face_a, starterCard_a, faceStarterCard_a,
                expAnimals_a, expInsects_a, expMushrooms_a, expVegetals_a, expFeather_a, expInk_a, expPaper_a, expAvailablePosition_a, expDisposition_a, expNumberNearbyCards_a, exceptionCoord_a, exceptionCard_a, exceptionCode_a);


        //test b artifacts

        int[] cards_b = {25, 36};
        int[] coordinates_b = {1,1, 1,-1};
        int expectedResults_b = 0;
        boolean face_b = true;
        int starterCard_b = 81;
        boolean faceStarterCard_b = true;
        int expAnimals_b = 1;
        int expInsects_b = 4;
        int expMushrooms_b = 1;
        int expVegetals_b = 0;
        int expFeather_b = 0;
        int expInk_b = 1;
        int expPaper_b = 1;

        List<Coordinates> expAvailablePosition_b = new ArrayList<>();
        Coordinates coord6 = new Coordinates(-1, 1);
        Coordinates coord7 = new Coordinates(-1, -1);
        Coordinates coord8 = new Coordinates(2, 2);
        Coordinates coord9 = new Coordinates(2, 0);
        Coordinates coord10 = new Coordinates(2, -2);
        expAvailablePosition_b.add(coord6);
        expAvailablePosition_b.add(coord7);
        expAvailablePosition_b.add(coord8);
        expAvailablePosition_b.add(coord9);
        expAvailablePosition_b.add(coord10);

        HashMap<Coordinates, PlayableCard> expDisposition_b = new HashMap<>();
        Coordinates coord_5 = new Coordinates(0, 0);
        PlayableCard card_5 = (PlayableCard) getCard(deckList, 81);
        Coordinates coord_6 = new Coordinates(1, 1);
        PlayableCard card_6 = (PlayableCard) getCard(deckList,25);
        Coordinates coord_7 = new Coordinates(1, -1);
        PlayableCard card_7 = (PlayableCard) getCard(deckList, 36);
        expDisposition_b.put(coord_5, card_5);
        expDisposition_b.put(coord_6, card_6);
        expDisposition_b.put(coord_7, card_7);

        int expNumberNearbyCards_b = 1;

        int exceptionCoord_b[] = {0,0};
        int exceptionCard_b = 0;
        int exceptionCode_b = 0;

        runTest(cards_b, coordinates_b, expectedResults_b, face_b, starterCard_b, faceStarterCard_b, expAnimals_b, expInsects_b, expMushrooms_b, expVegetals_b, expFeather_b, expInk_b, expPaper_b, expAvailablePosition_b, expDisposition_b, expNumberNearbyCards_b, exceptionCoord_b, exceptionCard_b, exceptionCode_b);


        //test c constrains 1

        int[] cards_c = {33, 24, 72};
        int[] coordinates_c = {1,1, 1,-1, 2,-2};
        int expectedResults_c = 1;
        boolean face_c = true;
        int starterCard_c = 81;
        boolean faceStarterCard_c = true;
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
        expAvailablePosition_c.add(coord11);
        expAvailablePosition_c.add(coord12);
        expAvailablePosition_c.add(coord13);
        expAvailablePosition_c.add(coord14);
        expAvailablePosition_c.add(coord15);

        HashMap<Coordinates, PlayableCard> expDisposition_c = new HashMap<>();
        Coordinates coord_8 = new Coordinates(0, 0);
        PlayableCard card_8 = (PlayableCard) getCard(deckList, 81);
        Coordinates coord_9 = new Coordinates(1, 1);
        PlayableCard card_9 = (PlayableCard) getCard(deckList,33);
        Coordinates coord_10 = new Coordinates(1, -1);
        PlayableCard card_10= (PlayableCard) getCard(deckList, 24);
        Coordinates coord_11 = new Coordinates(2, -2);
        PlayableCard card_11= (PlayableCard) getCard(deckList, 72);

        expDisposition_c.put(coord_8, card_8);
        expDisposition_c.put(coord_9, card_9);
        expDisposition_c.put(coord_10, card_10);
        expDisposition_c.put(coord_11, card_11);

        int expNumberNearbyCards_c = 1;

        int exceptionCoord_c[] = {0,0};
        int exceptionCard_c = 0;
        int exceptionCode_c = 0;

        runTest(cards_c, coordinates_c, expectedResults_c, face_c, starterCard_c, faceStarterCard_c, expAnimals_c, expInsects_c, expMushrooms_c, expVegetals_c, expFeather_c, expInk_c, expPaper_c, expAvailablePosition_c, expDisposition_c, expNumberNearbyCards_c, exceptionCoord_c, exceptionCard_c, exceptionCode_c);


        //test d constrains 2

        int[] cards_d = {33, 24, 72, 74};
        int[] coordinates_d = {1,1, 1,-1, 2,-2, -1,1};
        int expectedResults_d = 3;
        boolean face_d = true;
        int starterCard_d = 81;
        boolean faceStarterCard_d = true;
        int expAnimals_d = 1;
        int expInsects_d = 4;
        int expMushrooms_d = 0;
        int expVegetals_d = 0;
        int expFeather_d = 0;
        int expInk_d = 0;
        int expPaper_d = 1;

        List<Coordinates> expAvailablePosition_d = new ArrayList<>();
        Coordinates coord16 = new Coordinates(0, 2);
        Coordinates coord17 = new Coordinates(2, 0);
        Coordinates coord18 = new Coordinates(2, 0);
        Coordinates coord19 = new Coordinates(2, -2);
        Coordinates coord20 = new Coordinates(-1, 1);
        Coordinates coord21 = new Coordinates(-2, 2);
        Coordinates coord22 = new Coordinates(-1, -1);
        expAvailablePosition_d.add(coord16);
        expAvailablePosition_d.add(coord17);
        expAvailablePosition_d.add(coord18);
        expAvailablePosition_d.add(coord19);
        expAvailablePosition_d.add(coord20);
        expAvailablePosition_d.add(coord21);
        expAvailablePosition_d.add(coord22);

        HashMap<Coordinates, PlayableCard> expDisposition_d = new HashMap<>();
        Coordinates coord_12 = new Coordinates(0, 0);
        PlayableCard card_12 = (PlayableCard) getCard(deckList, 81);
        Coordinates coord_13 = new Coordinates(1, 1);
        PlayableCard card_13 = (PlayableCard) getCard(deckList,33);
        Coordinates coord_14 = new Coordinates(1, -1);
        PlayableCard card_14 = (PlayableCard) getCard(deckList, 24);
        Coordinates coord_15 = new Coordinates(2, -2);
        PlayableCard card_15 = (PlayableCard) getCard(deckList, 72);
        Coordinates coord_16 = new Coordinates(-1, 1);
        PlayableCard card_16 = (PlayableCard) getCard(deckList, 74);

        expDisposition_d.put(coord_12, card_12);
        expDisposition_d.put(coord_13, card_13);
        expDisposition_d.put(coord_14, card_14);
        expDisposition_d.put(coord_15, card_15);
        expDisposition_d.put(coord_16, card_16);

        int expNumberNearbyCards_d = 1;

        int exceptionCoord_d[] = {0,0};
        int exceptionCard_d = 0;
        int exceptionCode_d = 0;

        runTest(cards_d, coordinates_d, expectedResults_d, face_d, starterCard_d, faceStarterCard_d, expAnimals_d, expInsects_d, expMushrooms_d, expVegetals_d, expFeather_d, expInk_d, expPaper_d, expAvailablePosition_d, expDisposition_d, expNumberNearbyCards_d, exceptionCoord_d, exceptionCard_d, exceptionCode_d);



        //test e contrains 3

        int[] cards_e = {33, 24, 72, 74, 79};
        int[] coordinates_e = {1,1, 1,-1, 2,-2, -1,1, -2,2};
        int expectedResults_e = 6;
        boolean face_e = true;
        int starterCard_e = 81;
        boolean faceStarterCard_e = true;
        int expAnimals_e = 1;
        int expInsects_e = 4;
        int expMushrooms_e = 0;
        int expVegetals_e = 0;
        int expFeather_e = 1;
        int expInk_e = 0;
        int expPaper_e = 1;

        List<Coordinates> expAvailablePosition_e = new ArrayList<>();
        Coordinates coord23 = new Coordinates(0, 2);
        Coordinates coord24 = new Coordinates(2, 0);
        Coordinates coord25 = new Coordinates(2, 0);
        Coordinates coord26 = new Coordinates(2, -2);
        Coordinates coord27 = new Coordinates(-1, 1);
        Coordinates coord28 = new Coordinates(-1, -1);
        expAvailablePosition_e.add(coord23);
        expAvailablePosition_e.add(coord24);
        expAvailablePosition_e.add(coord25);
        expAvailablePosition_e.add(coord26);
        expAvailablePosition_e.add(coord27);
        expAvailablePosition_e.add(coord28);


        HashMap<Coordinates, PlayableCard> expDisposition_e = new HashMap<>();
        Coordinates coord_101 = new Coordinates(0, 0);
        PlayableCard card_101 = (PlayableCard) getCard(deckList, 81);
        Coordinates coord_17 = new Coordinates(1, 1);
        PlayableCard card_17 = (PlayableCard) getCard(deckList,33);
        Coordinates coord_18 = new Coordinates(1, -1);
        PlayableCard card_18 = (PlayableCard) getCard(deckList, 24);
        Coordinates coord_19 = new Coordinates(2, -2);
        PlayableCard card_19 = (PlayableCard) getCard(deckList, 72);
        Coordinates coord_20 = new Coordinates(-1, 1);
        PlayableCard card_20 = (PlayableCard) getCard(deckList, 74);
        Coordinates coord_21 = new Coordinates(-2, 2);
        PlayableCard card_21 = (PlayableCard) getCard(deckList, 79);

        expDisposition_e.put(coord_101, card_101);
        expDisposition_e.put(coord_17, card_17);
        expDisposition_e.put(coord_18, card_18);
        expDisposition_e.put(coord_19, card_19);
        expDisposition_e.put(coord_20, card_20);
        expDisposition_e.put(coord_21, card_21);

        int expNumberNearbyCards_e = 1;

        int exceptionCoord_e[] = {0,0};
        int exceptionCard_e = 0;
        int exceptionCode_e = 0;

        runTest(cards_e, coordinates_e, expectedResults_e, face_e, starterCard_e, faceStarterCard_e, expAnimals_e, expInsects_e, expMushrooms_e, expVegetals_e, expFeather_e, expInk_e, expPaper_e, expAvailablePosition_e, expDisposition_e, expNumberNearbyCards_e, exceptionCoord_e, exceptionCard_e, exceptionCode_e);


        //test f commond points
        int[] cards_f = {20, 29};
        int[] coordinates_f = {1, 1, -1, 1};
        int expectedResults_f = 2;
        boolean face_f = true;
        int starterCard_f = 81;
        boolean faceStarterCard_f = true;
        int expAnimals_f = 1;
        int expInsects_f = 2;
        int expMushrooms_f = 0;
        int expVegetals_f = 1;
        int expFeather_f = 0;
        int expInk_f = 0;
        int expPaper_f = 0;

        List<Coordinates> expAvailablePosition_f = new ArrayList<>();
        Coordinates coord100 = new Coordinates(0, 2);
        Coordinates coord29 = new Coordinates(2, 0);
        Coordinates coord30 = new Coordinates(1, -1);
        Coordinates coord31 = new Coordinates(-1, -1);
        Coordinates coord32 = new Coordinates(-2, 0);
        expAvailablePosition_f.add(coord100);
        expAvailablePosition_f.add(coord29);
        expAvailablePosition_f.add(coord30);
        expAvailablePosition_f.add(coord31);
        expAvailablePosition_f.add(coord32);
        ;

        HashMap<Coordinates, PlayableCard> expDisposition_f = new HashMap<>();
        Coordinates coord_100 = new Coordinates(0, 0);
        PlayableCard card_100 = (PlayableCard) getCard(deckList, 81);
        Coordinates coord_22 = new Coordinates(1, 1);
        PlayableCard card_22 = (PlayableCard) getCard(deckList, 20);
        Coordinates coord_23 = new Coordinates(-1, 1);
        PlayableCard card_23 = (PlayableCard) getCard(deckList, 29);

        expDisposition_f.put(coord_100, card_100);
        expDisposition_f.put(coord_22, card_22);
        expDisposition_f.put(coord_23, card_23);

        int expNumberNearbyCards_f = 1;

        int exceptionCoord_f[] = {0, 0};
        int exceptionCard_f = 0;
        int exceptionCode_f = 0;

        runTest(cards_f, coordinates_f, expectedResults_f, face_f, starterCard_f, faceStarterCard_f, expAnimals_f, expInsects_f, expMushrooms_f, expVegetals_f, expFeather_f, expInk_f, expPaper_f, expAvailablePosition_f, expDisposition_f, expNumberNearbyCards_f, exceptionCoord_f, exceptionCard_f, exceptionCode_f);

        //test g exception noavailableplaces

        int[] cards_g = {20, 29};
        int[] coordinates_g = {1, 1, -1, 1};
        int expectedResults_g = 2;
        boolean face_g = true;
        int starterCard_g = 81;
        boolean faceStarterCard_g = true;
        int expAnimals_g = 1;
        int expInsects_g = 2;
        int expMushrooms_g = 0;
        int expVegetals_g = 1;
        int expFeather_g = 0;
        int expInk_g = 0;
        int expPaper_g = 0;

        List<Coordinates> expAvailablePosition_g = new ArrayList<>();
        Coordinates coord28_g = new Coordinates(0, 2);
        Coordinates coord29_g = new Coordinates(2, 0);
        Coordinates coord30_g = new Coordinates(1, -1);
        Coordinates coord31_g = new Coordinates(-1, -1);
        Coordinates coord32_g = new Coordinates(-2, 0);
        expAvailablePosition_g.add(coord28_g);
        expAvailablePosition_g.add(coord29_g);
        expAvailablePosition_g.add(coord30_g);
        expAvailablePosition_g.add(coord31_g);
        expAvailablePosition_g.add(coord32_g);

        HashMap<Coordinates, PlayableCard> expDisposition_g = new HashMap<>();
        Coordinates coord_21_g = new Coordinates(0, 0);
        PlayableCard card_21_g = (PlayableCard) getCard(deckList, 81);
        Coordinates coord_22_g = new Coordinates(1, 1);
        PlayableCard card_22_g = (PlayableCard) getCard(deckList, 20);
        Coordinates coord_23_g = new Coordinates(-1, 1);
        PlayableCard card_23_g = (PlayableCard) getCard(deckList, 29);

        expDisposition_g.put(coord_21_g, card_21_g);
        expDisposition_g.put(coord_22_g, card_22_g);
        expDisposition_g.put(coord_23_g, card_23_g);

        int expNumberNearbyCards_g = 1;

        int exceptionCoord_g[] = {-1, 1};
        int exceptionCard_g = 29;
        int exceptionCode_g = 1;

        runTest(cards_g, coordinates_g, expectedResults_g, face_g, starterCard_g, faceStarterCard_g, expAnimals_g, expInsects_g, expMushrooms_g, expVegetals_g, expFeather_g, expInk_g, expPaper_g, expAvailablePosition_g, expDisposition_g, expNumberNearbyCards_g, exceptionCoord_g, exceptionCard_g, exceptionCode_g);


        //test h exception no nearby card

        int[] cards_h = {20, 29};
        int[] coordinates_h = {1, 1, -1, 1};
        int expectedResults_h = 2;
        boolean face_h = true;
        int starterCard_h = 81;
        boolean faceStarterCard_h = true;
        int expAnimals_h = 1;
        int expInsects_h = 2;
        int expMushrooms_h = 0;
        int expVegetals_h = 1;
        int expFeather_h = 0;
        int expInk_h = 0;
        int expPaper_h = 0;

        List<Coordinates> expAvailablePosition_h = new ArrayList<>();
        Coordinates coord28_h = new Coordinates(0, 2);
        Coordinates coord29_h = new Coordinates(2, 0);
        Coordinates coord30_h = new Coordinates(1, -1);
        Coordinates coord31_h = new Coordinates(-1, -1);
        Coordinates coord32_h = new Coordinates(-2, 0);
        expAvailablePosition_h.add(coord28_h);
        expAvailablePosition_h.add(coord29_h);
        expAvailablePosition_h.add(coord30_h);
        expAvailablePosition_h.add(coord31_h);
        expAvailablePosition_h.add(coord32_h);

        HashMap<Coordinates, PlayableCard> expDisposition_h = new HashMap<>();
        Coordinates coord_21_h = new Coordinates(0, 0);
        PlayableCard card_21_h = (PlayableCard) getCard(deckList, 81);
        Coordinates coord_22_h = new Coordinates(1, 1);
        PlayableCard card_22_h = (PlayableCard) getCard(deckList, 20);
        Coordinates coord_23_h = new Coordinates(-1, 1);
        PlayableCard card_23_h = (PlayableCard) getCard(deckList, 29);

        expDisposition_h.put(coord_21_h, card_21_h);
        expDisposition_h.put(coord_22_h, card_22_h);
        expDisposition_h.put(coord_23_h, card_23_h);

        int expNumberNearbyCards_h = 1;

        int exceptionCoord_h[] = {4, 6};
        int exceptionCard_h = 2;
        int exceptionCode_h = 1;

        runTest(cards_h, coordinates_h, expectedResults_h, face_h, starterCard_h, faceStarterCard_h, expAnimals_h, expInsects_h, expMushrooms_h, expVegetals_h, expFeather_h, expInk_h, expPaper_h, expAvailablePosition_h, expDisposition_h, expNumberNearbyCards_h, exceptionCoord_h, exceptionCard_h, exceptionCode_h);


        //test i exception no costrains

        int[] cards_i = {20, 29};
        int[] coordinates_i = {1, 1, -1, 1};
        int expectedResults_i = 2;
        boolean face_i = true;
        int starterCard_i = 81;
        boolean faceStarterCard_i = true;
        int expAnimals_i = 1;
        int expInsects_i = 2;
        int expMushrooms_i = 0;
        int expVegetals_i = 1;
        int expFeather_i = 0;
        int expInk_i = 0;
        int expPaper_i = 0;

        List<Coordinates> expAvailablePosition_i = new ArrayList<>();
        Coordinates coord28_i = new Coordinates(0, 2);
        Coordinates coord29_i = new Coordinates(2, 0);
        Coordinates coord30_i = new Coordinates(1, -1);
        Coordinates coord31_i = new Coordinates(-1, -1);
        Coordinates coord32_i = new Coordinates(-2, 0);
        expAvailablePosition_i.add(coord28_i);
        expAvailablePosition_i.add(coord29_i);
        expAvailablePosition_i.add(coord30_i);
        expAvailablePosition_i.add(coord31_i);
        expAvailablePosition_i.add(coord32_i);

        HashMap<Coordinates, PlayableCard> expDisposition_i = new HashMap<>();
        Coordinates coord_21_i = new Coordinates(0, 0);
        PlayableCard card_21_i = (PlayableCard) getCard(deckList, 81);
        Coordinates coord_22_i = new Coordinates(1, 1);
        PlayableCard card_22_i = (PlayableCard) getCard(deckList, 20);
        Coordinates coord_23_i = new Coordinates(-1, 1);
        PlayableCard card_23_i = (PlayableCard) getCard(deckList, 29);

        expDisposition_i.put(coord_21_i, card_21_i);
        expDisposition_i.put(coord_22_i, card_22_i);
        expDisposition_i.put(coord_23_i, card_23_i);

        int expNumberNearbyCards_i = 1;

        int exceptionCoord_i[] = {-2, 0};
        int exceptionCard_i = 78;
        int exceptionCode_i = 0;

        runTest(cards_i, coordinates_i, expectedResults_i, face_i, starterCard_i, faceStarterCard_i, expAnimals_i, expInsects_i, expMushrooms_i, expVegetals_i, expFeather_i, expInk_i, expPaper_i, expAvailablePosition_i, expDisposition_i, expNumberNearbyCards_i, exceptionCoord_i, exceptionCard_i, exceptionCode_i);


    }


    /**
     * Runs a test for the placement of cards in the area.
     *
     * @param cards An array of card IDs to be placed.
     * @param coordinates An array of coordinates where the cards will be placed.
     * @param expectedResults The expected result of the test.
     * @param face The face side of the cards.
     * @param starterCard The ID of the starter card.
     * @param faceStarterCard The face side of the starter card.
     * @param expAnimals The expected number of animal elements.
     * @param expInsects The expected number of insect elements.
     * @param expMushrooms The expected number of mushroom elements.
     * @param expVegetals The expected number of vegetal elements.
     * @param expFeather The expected number of feather artifacts.
     * @param expInk The expected number of ink artifacts.
     * @param expPaper The expected number of paper artifacts.
     * @param expAvailablePosition The expected list of available positions after the cards are placed.
     * @param expDisposition The expected disposition of the cards in the area.
     * @param expNumberNearbyCards The expected number of nearby cards.
     * @param exceptionCoord The coordinates where an exception is expected to occur.
     * @param exceptionCard The card ID where an exception is expected to occur.
     * @param exceptionCode The expected exception code.
     */
    public void runTest(int[] cards, int[] coordinates, int expectedResults, boolean face, int starterCard, boolean faceStarterCard, int expAnimals, int expInsects, int expMushrooms, int expVegetals, int expFeather, int expInk, int expPaper, List<Coordinates> expAvailablePosition, HashMap<Coordinates, PlayableCard> expDisposition, int expNumberNearbyCards, int exceptionCoord[], int exceptionCard, int exceptionCode){

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

        int cardsPoints;
        cardsPoints = initialize(area, cardList_a, coord_a, face, deckList);

        Coordinates xy = new Coordinates(exceptionCoord[0], exceptionCoord[1]);

        //System.out.println(xy.getX()+ " "+xy.getY());
        //System.out.println(getCard(cards[cards.length -1]));

        if(exceptionCode != 0){
            switch (exceptionCode) {
                case 1:
                    assertThrows(IllegalArgumentException.class, () -> {
                        area.testWrapper(xy, (PlayableCard) getCard(deckList, exceptionCard));
                    });
                    break;

                case 2:
                    area.testWrapper(xy, (PlayableCard) getCard(deckList, exceptionCard));
                    break;

                case 3:
                    area.testWrapper(xy, (PlayableCard) getCard(deckList, exceptionCard));
                    break;

                default:
                    System.out.println("Unknown exception card: " + exceptionCard);
                    break;
            }
        }


        List<Coordinates> availablePosition = area.getAvailablePlaces();

        HashMap<Coordinates, PlayableCard> disposition = area.getDisposition();

        int numberNearbyCards = area.getNumberNearbyCards();
        System.out.println(expectedResults);
        assertEquals (expectedResults, cardsPoints);
        System.out.println(expectedResults);
        assertEquals (expInsects, area.getNumberElements(Element.insects));
        assert (expAnimals == area.getNumberElements(Element.animals)): "Errore";
        assert (expMushrooms == area.getNumberElements(Element.mushrooms)): "Errore";
        assert (expVegetals == area.getNumberElements(Element.vegetals)): "Errore";

        for(Coordinates position : disposition.keySet()) {
            boolean flag = false;
            for(Coordinates positionExp : expDisposition.keySet()) {
                if(position.equals(positionExp)) {
                    flag = true;
                    assert (expDisposition.get(positionExp).getId() == disposition.get(position).getId());
                    break;
                }
            }
            if (!flag) {
                assert false; // position not found
            }
        }
        //assert (expDisposition.containsKey(position.getKey()) && expDisposition.get(position.getKey()).getId() == position.getValue().getId()) : "Errore";

        assert (expFeather == area.getNumberArtifacts(Artifact.feather)): "Errore";
        assert (expInk == area.getNumberArtifacts(Artifact.ink)): "Errore";
        assert (expPaper == area.getNumberArtifacts(Artifact.paper)): "Errore";

        //assert (expDisposition == disposition): "Errore";

        int found = 0;
        for(Coordinates expPos : expAvailablePosition) {
            found = 0;


            for (Coordinates pos : availablePosition) {
                if (expPos.equals(pos)) {
                    found = 1;
                    break;
                }
            }


        }

        assertEquals(1, found);

        //assert (expAvailablePosition == availablePosition): "Errore";

        assert (expNumberNearbyCards == numberNearbyCards): "Errore";
    }

}