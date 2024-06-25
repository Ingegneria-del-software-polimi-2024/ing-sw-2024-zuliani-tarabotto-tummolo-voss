package model.placementArea;

import junit.framework.TestCase;
import model.Exceptions.CantPlaceCardException;
import model.Exceptions.KickOutOfGameException;
import model.GameState.GameState;
import model.cards.Card;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.cards.PlayableCards.StarterCard;
import model.deckFactory.Deck;
import model.deckFactory.Generators.*;
import model.deckFactory.ObjectiveDeck;
import model.deckFactory.PlayableDeck;
import model.deckFactory.ResourcesDeck;
import model.enums.Artifact;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Placement area test.
 */
public class PlacementAreaTest extends TestCase {
    //private static GameState gameState;

    /**
     * The Id.
     */
    static int ID = 81;

    /**
     * Initialize.
     *
     * @param area     the area
     * @param cardList the card list
     * @param coord    the coord
     * @param face     the face
     * @param deckList the deck list
     */
//initializes the gamestate (please always specify one player), inserts the cards expressed from cli
    // in the placementArea of the palyer, the starterCard will be automatically added
    public static void initialize(PlacementArea area, ArrayList<Integer> cardList, ArrayList<Coordinates> coord, boolean face, Deck[] deckList) {

        PlayableCard cardToBePlaced = (PlayableCard) getCard(deckList, ID);
        cardToBePlaced.setFaceSide(false);
        try {
            area.addCard(cardToBePlaced);
        }catch (KickOutOfGameException e){
            throw new RuntimeException(e);
        }
        System.out.println("starter card placed");

        putCards(cardList, coord, face, area, deckList);
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

    /**
     * Put cards.
     *
     * @param cards     the cards
     * @param coordList the coord list
     * @param face      the face
     * @param area      the area
     * @param deckList  the deck list
     * @throws RuntimeException the runtime exception
     */
    public static void putCards(ArrayList<Integer> cards, ArrayList<Coordinates> coordList, boolean face, PlacementArea area, Deck[] deckList) throws RuntimeException{

        if(coordList.size() != cards.size())
            throw new RuntimeException("error in data inserted");

        for (int i=0; i< cards.size(); i++) {
            Coordinates coord = coordList.get(i);
            PlayableCard x = (PlayableCard) getCard(deckList, cards.get(i));
            System.out.println("putting card n. "+cards.get(i));
            x.setFaceSide(face);
            try {
                area.addCard(coord, x);
            }catch (CantPlaceCardException e){
                throw new RuntimeException(e);
            }
            //System.out.println("points: "+ gameState.getTurnPlayer().getPoints());
            System.out.println("inserted card "+i);
            System.out.println("artifacts:");
            System.out.println("            feather: "+area.getNumberArtifacts(Artifact.feather));
            System.out.println("            ink: "+area.getNumberArtifacts(Artifact.ink));
            System.out.println("            paper: "+area.getNumberArtifacts(Artifact.paper));

        }
    }

    /**
     * Check for objectives int.
     *
     * @param area the area
     * @param obj  the obj
     * @return the int
     */
    public static int checkForObjectives(PlacementArea area, ObjectiveCard obj){return obj.countPoints(area);}


    /**
     * Test objective counts.
     *
     * @throws Exception the exception
     */
//insert 999 when asking "what objective id do you want to check? " if you want to stop
    @Test
    public void testObjectiveCounts() throws Exception{
        //test objective 87 ascDiag Mushrooms
        int[] a = {1, 2, 3, 4, 5, 6};
        int[] c_a = {1,1, 2,2, 3,3, 4,4, 5,5, 6,6};
        int[] obj_a = {87};
        int[] exp_a = {4};

        runTest(a, c_a, obj_a, exp_a, false);

        //test objective 88 descDiag Veg
        int[] b = {16, 17, 18};
        int[] c_b = {-1,1, -2,2, -3,3};
        int[] obj_b = {88};
        int[] exp_b = {2};

        runTest(b, c_b, obj_b, exp_b, false);

        //test objective 89 ascDiag animals
        int[] c = {21, 22, 23};
        int[] c_c = {-1,-1, -2,-2, -3,-3};
        int[] obj_c = {89};
        int[] exp_c = {2};

        runTest(c, c_c, obj_c, exp_c, false);

        //test objective 90 descDiag Insects
        int[] d = {31, 32, 33};
        int[] c_d = {1,-1, 2,-2, 3,-3};
        int[] obj_d = {90};
        int[] exp_d = {2};

        runTest(d, c_d, obj_d, exp_d, false);

        //test objective 91 L Mushrooms
        int[] e = {1, 33, 2, 16};
        int[] c_e = {1,-1, 0,-2, 1,-3, 2,-4};
        int[] obj_e = {91};
        int[] exp_e = {3};

        runTest(e, c_e, obj_e, exp_e, false);

        //test objective 92 L Vegetals
        int[] f = {16, 21, 17, 33};
        int[] c_f = {-1,1, 0,2, -1,3, -2,0};
        int[] obj_f = {92};
        int[] exp_f = {3};

        runTest(f, c_f, obj_f, exp_f, false);

        //test objective 93 L Animals
        int[] g = {21, 33, 22, 1};
        int[] c_g = {-1,1, 0,2, -1,3, 0,4};
        int[] obj_g = {93};
        int[] exp_g = {3};

        runTest(g, c_g, obj_g, exp_g, false);

        //test objective 94 L Insects
        int[] h = {31, 1, 32, 21};
        int[] c_h = {-1,1, 0,2, -1,3, -2,4};
        int[] obj_h = {94};
        int[] exp_h = {3};

        runTest(h, c_h, obj_h, exp_h, false);
        

        //tested animals L obj, complication
        int[] j = {21, 22, 1, 23, 2, 24, 3, 4, 25, 11, 26, 27, 28};
        int[] c_j = {1,1, 1,-1, 2,2, 1,3, 2,4, 1,5, 2,6, 2,-2, 1,-3, 2,-4, 1,-5, 0,-2, -1,-3};
        int[] obj_j = {89, 93};
        int[] exp_j = {2, 9};

        runTest(j, c_j, obj_j, exp_j, false);

        //tested mushrooms L obj, complication
        int[] k = {1, 2, 11, 3, 12, 4, 13, 14, 5, 15, 6, 16, 17, 18, 19, 7, 8};
        int[] c_k = {1,1, 1,-1, 2,2, 1,3, 2,4, 1,5, 2,0, 2,-2, 1,-3, 2,-4, 1,-5, 2,-6, 3,-5, 4,-6, 5,-7, 2,6, 0,4};
        int[] obj_k = {87, 88, 91};
        int[] exp_k = {2, 2, 9};

        runTest(k, c_k, obj_k, exp_k, false);

        //tested vegetals L obj, complication
        int[] l = {11, 12, 31, 32, 33, 13, 14, 34, 1, 15, 16, 36};
        int[] c_l = {-1,1, -1,-1, -2,0, -2,2, -2,-2, -1,3, -1,-3, -2,-4, -2,4, -1,-5, -1,5, -2,-6};
        int[] obj_l = {92};
        int[] exp_l = {9};

        runTest(l, c_l, obj_l, exp_l, false);

        //tested insects L obj, complication
        int[] m = {31, 32, 21, 22, 23, 33, 34, 24, 27, 35, 36, 26, 28};
        int[] c_m = {-1,1, -1,-1, -2,0, -2,2, -2,-2, -1,3, -1,-3, -2,-4, -2,4, -1,-5, -1,5, -2,-6, -2,6};
        int[] obj_m = {94};
        int[] exp_m = {9};

        runTest(m, c_m, obj_m, exp_m, false);

        //testing elements and artifacts objectives
        int[] n = {5, 16, 27, 37, 43, 22, 69, 11, 20, 13};
        int[] c_n = {1,-1, 1,1, -1,-1, -1,1, 0,-2, 2,-2, 0,2, 1,3, 2,4, -1,-3};
        int[] obj_n = {95, 96, 97, 98, 100, 101, 102, 99};
        int[] exp_n = {0, 4, 2, 0, 0, 2, 2, 3};

        runTest(n, c_n, obj_n, exp_n, true);

    }

    /**
     * Run test.
     *
     * @param cards           the cards
     * @param coordinates     the coordinates
     * @param objectives      the objectives
     * @param expectedResults the expected results
     * @param face            the face
     */
    public void runTest(int[] cards, int[] coordinates, int[] objectives, int[] expectedResults, boolean face){

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

        for (int card : cards) cardList_a.add(card);

        for(Integer c : cardList_a)
            System.out.println(c);

        for(int i = 0; i < 2*cards.length; i++)
            if(i%2 == 0)
                coord_a.add(new Coordinates(coordinates[i], coordinates[i+1]));

        for (Coordinates c : coord_a)
            System.out.println(c.getX()+ " "+c.getY());

        initialize(area, cardList_a, coord_a, face, deckList);

        for(int i = 0; i < objectives.length; i++) {
            ObjectiveCard obj = null;
            obj = objDeck.getCard(objectives[i]);
            if(obj == null){
                System.out.println("can't find the objective");
                return;
            }
        int n = checkForObjectives(area, obj);
        System.out.println("counted: "+n);
        System.out.println("exp: "+expectedResults[i]);
        assert(n == expectedResults[i]): "error while counting points";
        System.out.println("Obj "+i+" done: "+n);

        }
    }
}