package Client.UI.TUI;

import model.cards.PlayableCards.PlayableCard;
import java.util.List;
import static org.fusesource.jansi.Ansi.ansi;


/**
 * class with methods for printing the draw card panel on console
 */
public class DrawCardPrinter {

    private CardBuilder cb;

    public DrawCardPrinter(CardBuilder cb){
        this.cb = cb;

    }

    /**
     * prints the panel containing the cards that can be drawn by the player
     * @param gold GoldCard on top of the GoldDeck
     * @param resource ResourceCard on top of the ResourceDeck
     * @param openGold List containing the two openGold cards
     * @param openResource List containing the two openResource cards
     */
    public void print(PlayableCard gold, PlayableCard resource, List<PlayableCard> openGold, List<PlayableCard> openResource){
        int  color = 226;
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

    /**
     * prints on console the back face of the GoldCard on top of the GoldDeck and the two openGold cards facing up
     * @param gold the GoldCard on top of the GoldDeck
     * @param openGold a List of size two containing the openGold Cards
     */
    private void printFaces1(PlayableCard gold, List<PlayableCard> openGold){

       if (openGold.isEmpty()){
            openGold.add(0, null);
            openGold.add(1, null);
        } else if (openGold.size()==1) {
            openGold.get(0).setFaceSide(true);
            openGold.add(1, null);
        }else {
            openGold.get(0).setFaceSide(true);
            openGold.get(1).setFaceSide(true);
        }

        for(int i = 0; i < 6; i++){
            System.out.println("\u2551     " + buildLine(i,gold) + "      " + buildLine(i, openGold.get(0)) + "      " + buildLine(i, openGold.get(1)) + "     \u2551");
        }
    }

    /**
     * prints on console the back face of the GoldCard on top of the GoldDeck and the two openGold cards facing up
     * @param resource the ResourceCard on top of the ResourceDeck
     * @param openResource a List of size two containing the openResource Cards
     */
    private void printFaces2(PlayableCard resource, List<PlayableCard> openResource){
        if(openResource.get(0) != null) openResource.get(0).setFaceSide(true);
        if(openResource.get(1) != null) openResource.get(1).setFaceSide(true);

        if (openResource.isEmpty()){
            openResource.add(0, null);
            openResource.add(1, null);
        } else if (openResource.size()==1) {
            openResource.get(0).setFaceSide(true);
            openResource.add(1, null);
        }else {
            openResource.get(0).setFaceSide(true);
            openResource.get(1).setFaceSide(true);
        }
        for(int i = 0; i < 6; i++){
            System.out.println("\u2551     " + buildLine(i, resource) + "      " + buildLine(i, openResource.get(0)) + "      " + buildLine(i, openResource.get(1)) + "     \u2551");
        }
    }

    /**
     * if the PlayableCard c is null, then a line of white spaces is returned, meaning that the cards in that deck are finished.
     * if c is not null, then the buildLine method in CardBuilder is called
     * @param i the row that has to be printed
     * @param c the corresponding PlayableCard
     * @return
     */
    private String buildLine(int i, PlayableCard c){
        if(c == null) return "               ";
        else return cb.buildLine(i, c);
    }
}
