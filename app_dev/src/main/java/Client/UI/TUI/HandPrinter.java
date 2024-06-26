package Client.UI.TUI;

import model.cards.PlayableCards.PlayableCard;

import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * cards with methods for printing the hand panel on console
 */
public class HandPrinter {
    private CardBuilder cb;
    /**
     * List of PlayableCard containing the three cards in the player's hand
     */
    private List<PlayableCard> hand;
    private final int color = 226;
    /**
     * List of Strings containing representing the player's hand
     */
    List<String> handField;

    /**
     * Instantiates a new Hand printer.
     *
     * @param cb the cb
     */
    public HandPrinter(CardBuilder cb){
        this.cb = cb;
    }

    /**
     * directly prints on console the three cards in the player's hand by showing both the front and the back sides
     *
     * @param hand List of PlayableCard containing the three cards in the player's hand
     */
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

    /**
     * for each one of the six rows that compose a card face, the method calls the CardBuilder corresponding printing method
     */
    private void printFaces(){

        for(int i = 0; i < 6; i++){
            System.out.println("\u2551     " + cb.buildLine(i,hand.get(0)) + "      " + cb.buildLine(i,hand.get(1)) + "      " + cb.buildLine(i,hand.get(2)) + "     \u2551");
        }
    }


    /**
     * method only used in the selection of the face side for the starter card.
     * the method prints on console the player's StarterCard.
     *
     * @param card the player's StarterCard
     */
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


    /**
     * builds a List of Strings containing the player's hand cards, the List is then returned
     *
     * @param hand List of PlayableCard containing the three cards in the player's hand
     * @return List of Strings containing the player's hand panel
     */
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

    /**
     * for each one of the six rows that compose a card face, the method calls the CardBuilder corresponding printing method and adds the
     * Strings returned by the CardBuilder to the handField List of Strings
     */
    private void getFaces(){
        for(int i = 0; i < 6; i++){
            handField.add("\u2551     " + cb.buildLine(i,hand.get(0)) + "      " + cb.buildLine(i,hand.get(1)) + "      " + cb.buildLine(i,hand.get(2)) + "     \u2551");
        }
    }


}
