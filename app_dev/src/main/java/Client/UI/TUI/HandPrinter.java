package Client.UI.TUI;

import model.cards.PlayableCards.PlayableCard;

import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class HandPrinter {
    private CardBuilder cb = new CardBuilder();//TODO inizializzare il CardBuilder in TUI e poi passarlo come parametro a tutti i printer
    private List<PlayableCard> hand;
    private final int color = 226;
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
        System.out.println("FRONT:");
        card.setFaceSide(true);
        for(int i = 0; i < 6; i++){
            System.out.println(cb.buildLine(i, card));
        }
        System.out.println("BACK");
        card.setFaceSide(false);
        for(int i = 0; i < 6; i++){
            System.out.println(cb.buildLine(i, card));
        }
    }

}
