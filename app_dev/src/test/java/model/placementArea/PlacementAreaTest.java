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
            ObjectiveCard obj = null;
            for(ObjectiveCard o: gameState.getCommonObjectives())
                if(o.getId() == objectives[i]) {
                    obj = o;
                    break;
                }
            if(obj == null)
                obj = gameState.getObjectiveDeck().getCard(objectives[i]);
            if(obj == null){
                System.out.println("can't find the objective");
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

}