package Client.UI.TUI;

import model.cards.PlayableCards.PlayableCard;
import model.cards.PlayableCards.ResourceCard;

import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class DrawCardPrinter {

    private CardBuilder cb;

    public DrawCardPrinter(CardBuilder cb){
        this.cb = cb;

    }
    public void print(PlayableCard gold, PlayableCard resource, List<PlayableCard> openGold, List<PlayableCard> openResource){
        int  color = 221;
        System.out.println("\u2554\u2550\u2550\u2550"+ ansi().fg(color).bold().a(" DRAWABLE CARDS ").reset() + "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557");
        System.out.println("\u2551                                                                   \u2551");

        System.out.println("\u2551      GOLD DECK (1)          Gold:1 (2)           Gold:2 (3)       \u2551");

        //print goldDeck and openGold
        printFaces1(gold, openGold);

        System.out.println("\u2551     RESOURCE DECK (4)    Resource:1 (5)       Resource:2 (6)      \u2551");
        //print resourceDeck and openResource
        printFaces2(resource, openResource);

        System.out.println("\u2551                                                                   \u2551");
        System.out.println("\u2551                                                                   \u2551");
        System.out.println("\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D");
    }

    private void printFaces1(PlayableCard gold, List<PlayableCard> openGold){
        openGold.get(0).setFaceSide(true);
        openGold.get(1).setFaceSide(true);
        for(int i = 0; i < 6; i++){
            System.out.println("\u2551     " + buildLine(i,gold) + "      " + buildLine(i, openGold.get(0)) + "      " + buildLine(i, openGold.get(1)) + "     \u2551");
        }
    }

    private void printFaces2(PlayableCard resource, List<PlayableCard> openResource){
        openResource.get(0).setFaceSide(true);
        openResource.get(1).setFaceSide(true);
        for(int i = 0; i < 6; i++){
            System.out.println("\u2551     " + buildLine(i, resource) + "      " + buildLine(i, openResource.get(0)) + "      " + buildLine(i, openResource.get(1)) + "     \u2551");
        }
    }

    private String buildLine(int i, PlayableCard c){
        if(c == null) return "               ";
        else return cb.buildLine(i, c);
    }
}
