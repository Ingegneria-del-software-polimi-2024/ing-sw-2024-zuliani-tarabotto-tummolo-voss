package view;

import model.GameState.GameState;
import model.cards.Card;
import model.cards.PlayableCards.Corner;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Element;

public class CliView {

    private GameState gameState;

    public CliView(GameState gameState){
        this.gameState = gameState;
    }
    public void printStarterCard(){
        /*PlayableCard card = gameState.getTurnPlayer().getStartCard();
        System.out.println("Card ID: " + card.getId());
        if(card.getFaceSide()){
            for (Corner c : card.getCorners()){System.out.println("Corner_" + card.getCorners().indexOf(c) + ": "+c.getElement());}
            for (Element el : card.getBlockedElements()) {System.out.println("Blocked elements: " + el.toString());}
                System.out.println();
        } else {
            int i = 0;
            for(Element el : card.getBackFaceCorners()){
                System.out.println("Corner_" + i + ": " + el.toString());
                i++;
            }
        }*/
        gameState.getTurnPlayer().getStarterCard().printCard();
    }

    /*
    public void printHandCard(PlayableCard card){
        System.out.println("Card ID: " + card.getId());
        if(card.getFaceSide()){
            for (Corner c : card.getCorners()) {printCorner(c);}
            System.out.println("Points policy:");
            System.out.println();
        } else {
            System.out.println("Blocked element" + card.getBlockedElement());
        }
    }
    */

    /*
    public void printCorner(Corner c) {
        if (c.getElement() != null) System.out.println("Corner_" + c.getId() + ": " + c.getElement());
        else if (c.getArtifact() != null) System.out.println("Corner_" + c.getId() + ": " + c.getArtifact());
        else System.out.println("Corner_" + c.getId() + ": empty");
    }
    */

    public void printPlayerHand() {
        System.out.println(" MANO DI " + gameState.getTurnPlayer().getNickname());
        for (PlayableCard card : gameState.getTurnPlayer().getPlayingHand()) {
            System.out.println();
            card.printCard();
        }
    }
}
