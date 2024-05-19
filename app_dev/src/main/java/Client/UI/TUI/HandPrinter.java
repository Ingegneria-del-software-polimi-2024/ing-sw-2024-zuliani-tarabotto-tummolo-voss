package Client.UI.TUI;

import model.cards.PlayableCards.PlayableCard;

import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class HandPrinter {
    private CardBuilder cb = new CardBuilder();//TODO inizializzare il CardBuilder in TUI e poi passarlo come parametro a tutti i printer
    private List<PlayableCard> hand;
    private final int color = 226;
    List<String> handField;


    public void print(List<PlayableCard> hand){
        this.hand = hand;

        System.out.println("\u2554\u2550\u2550\u2550"+ ansi().fg(color).bold().a(" HAND ").reset() + "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557");
        System.out.println("\u2551                                                                   \u2551");

        System.out.println("\u2551         carta:1              carta:2              carta:3         \u2551");

        //front faces of cards
        for(int i = 0; i < hand.size(); i++){
            hand.get(i).setFaceSide(true);
        }
        printFaces();

        //back faces of cards
        for(int i = 0; i < hand.size(); i++){
            hand.get(i).setFaceSide(false);
        }
        printFaces();

        System.out.println("\u2551                                                                   \u2551");
        System.out.println("\u2551                                                                   \u2551");
        System.out.println("\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D");
    }

    private void printFaces(){

        for(int i = 0; i < 6; i++){
            System.out.println("\u2551     " + cb.buildLine(i,hand.get(0)) + "      " + cb.buildLine(i,hand.get(1)) + "      " + cb.buildLine(i,hand.get(2)) + "     \u2551");
        }
    }



    public void printStarterCard(PlayableCard card){

        System.out.println("\u2554\u2550\u2550\u2550"+ ansi().fg(color).bold().a(" STARTER CARD ").reset() + "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557");
        System.out.println("\u2551                                              \u2551");

        System.out.println("\u2551          FRONT                BACK           \u2551");

        for(int i = 0; i < 6; i++){
            card.setFaceSide(true);
            System.out.print("\u2551     " + cb.buildLine(i, card));
            card.setFaceSide(false);
            System.out.print("      " + cb.buildLine(i, card)  + "     \u2551\n");
        }


        System.out.println("\u2551                                              \u2551");
        System.out.println("\u2551                                              \u2551");
        System.out.println("\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D");
    }








    ///////////////////////////////////////////////////////// Test //////////////////////////////////////////////////////////////////
    public List<String> getHandField(List<PlayableCard> hand){
        this.hand = hand;
        this.handField = new ArrayList<>();

        handField.add("\u2554\u2550\u2550\u2550"+ ansi().fg(color).bold().a(" HAND ").reset() + "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557");
        handField.add("\u2551                                                                   \u2551");

        handField.add("\u2551         carta:1              carta:2              carta:3         \u2551");

        //front faces of cards
        for(int i = 0; i < hand.size(); i++){
            hand.get(i).setFaceSide(true);
        }
        getFaces();

        //back faces of cards
        for(int i = 0; i < hand.size(); i++){
            hand.get(i).setFaceSide(false);
        }
        getFaces();

        handField.add("\u2551                                                                   \u2551");
        handField.add("\u2551                                                                   \u2551");
        handField.add("\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D");

        return handField;
    }

    private void getFaces(){

        for(int i = 0; i < 6; i++){
            handField.add("\u2551     " + cb.buildLine(i,hand.get(0)) + "      " + cb.buildLine(i,hand.get(1)) + "      " + cb.buildLine(i,hand.get(2)) + "     \u2551");
        }
    }


}
