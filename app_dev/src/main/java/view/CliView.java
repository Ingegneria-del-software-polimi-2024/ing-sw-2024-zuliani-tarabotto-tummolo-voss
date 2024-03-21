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
        PlayableCard card = gameState.getTurnPlayer().getStartCard();
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
        }
    }


    public void printHandCard(PlayableCard card){
        System.out.println("Card ID: " + card.getId());
        if(card.getFaceSide()){
            for (Corner c : card.getCorners()) {printCorner(c);}
            System.out.println("Points policy:");
            System.out.println();
        } else {
            int i = 0;
            for(Element el : card.getBackFaceCorners()){
                System.out.println("Corner_" + i + ": " + el.toString());
                i++;
            }
        }
    }

    public void printCorner(Corner c) {
        if (c.getElement() != null) System.out.println("Corner_" + c.getId() + ": " + c.getElement());
        else if (c.getArtifact() != null) System.out.println("Corner_" + c.getId() + ": " + c.getArtifact());
        else System.out.println("Corner_" + c.getId() + ": empty");
    }
    //public void printPointsPolicy(PlayableCard card) {
      //  switch (card.)
    //}
}
