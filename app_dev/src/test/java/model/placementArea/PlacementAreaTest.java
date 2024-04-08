package model.placementArea;

import model.GameState.GameState;
import model.cards.Card;
import model.cards.ObjectiveCard;
import model.cards.PlayableCards.PlayableCard;
import model.deckFactory.Deck;
import model.deckFactory.ObjectiveDeck;
import model.deckFactory.PlayableDeck;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlacementAreaTest {
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

        PlayableCard cardToBePlaced = gameState.getStarterDeck().getCard(ID);
        cardToBePlaced.setFaceSide(false);
        gameState.getTurnPlayer().getPlacementArea().addCard(cardToBePlaced);
        System.out.println("starter card placed");
        if(face)
            deck = (PlayableDeck) gameState.getGoldDeck();
        else deck = gameState.getResourceDeck();
        putCards(cardList, deck, gameState.getTurnPlayer().getPlacementArea(), coord, face);
    }

    public static void putCards(ArrayList<Integer> cards, PlayableDeck deck, PlacementArea placementArea, ArrayList<Coordinates> coordList, boolean face) throws RuntimeException{
        if(coordList.size() != cards.size())
            throw new RuntimeException("error in data inserted");

        for (int i=0; i< cards.size(); i++) {
            Coordinates coord = coordList.get(i);
            gameState.setSelectedCoordinates(coord);
            PlayableCard x = deck.getCard(cards.get(i));
            if(x == null)
                throw new RuntimeException("error when getting the card with id: "+cards.get(i));

            gameState.setSelectedHandCard(x);
            gameState.setSelectedCardFace(face);
            gameState.playCard();
            System.out.println("inserted card "+i);
        }
    }

    public static int checkForObjectives(GameState state, ObjectiveCard obj){return obj.countPoints(state.getTurnPlayer().getPlacementArea());}

    public static void runTest(int[] cards, int[] coordinates, int[] objectives, int[] expectedResults, boolean face){
        ArrayList<Coordinates> coord_a = new ArrayList<Coordinates>();
        ArrayList<Integer> cardList_a = new ArrayList<Integer>();

        int id;


        for(int i = 0; i < cards.length; i++)
            cardList_a.add(cards[i]);

        for(Integer c : cardList_a)
            System.out.println(c);

        for(int i = 0; i < 2*cards.length; i++)
            if(i%2 == 0)
                coord_a.add(new Coordinates(coordinates[i], coordinates[i+1]));

        for (Coordinates c : coord_a)
            System.out.println(c.getX()+ " "+c.getY());

        initialize(cardList_a, coord_a, face);

        for(int i = 0; i < objectives.length; i++) {
            ObjectiveCard obj = gameState.getObjectiveDeck().getCard(objectives[i]);
            if(obj == null){
                System.out.println("ERROR");
                return;
            }
            int n = checkForObjectives(gameState, obj);
            System.out.println("counted: "+n);
            System.out.println("exp: "+expectedResults[i]);
            assert(n == expectedResults[i]): "error while counting points";
            System.out.println("Obj "+i+" done: "+n);
        }
    }
    //insert 999 when asking "what objective id do you want to check? " if you want to stop
    public static void main(String[] args){

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

        int[] i = {41, 42, 43, 44};
        int[] c_i = {1,1, 1,-1, -1,1, -1,-1};
        int[] obj_i = {95};
        int[] exp_i = {0};

        runTest(i, c_i, obj_i, exp_i, true);


    }

}