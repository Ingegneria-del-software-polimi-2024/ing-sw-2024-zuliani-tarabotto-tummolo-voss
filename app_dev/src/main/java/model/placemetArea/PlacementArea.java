package main.java.model.placemetArea;

import main.java.model.enums.Artifact;
import main.java.model.enums.Element;
import main.java.model.cards.*;

import java.util.HashMap;
import java.util.List;


//!!!!!!!we will need to declare a constructor for the initialization of the game!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

/*
the placement area for every player (where you put the card you are playing each turn)
*/

public class PlacementArea {
    private HashMap<Coordinates, PlayableCard> disposition; //the disposition on the table, used for search of patterns
    private HashMap<Artifact, Integer> availableArtifacts; //how many occurrences of that artifact you have, permits to rapidly check for points
    private HashMap<Element, Integer> availableElements; // as Artifacts but with elements
    private List<Coordinates> availablePlaces; //list of available places to put a card in, enable to search more rapidly where you can place cards
    private int numberNearbyCards; //saves the number of corners covered by last placed card

    public List<Coordinates> freePositions(){return availablePlaces;} //returns the free positions

    public void addCard(PlayableCard card, Coordinates xy) throws IllegalArgumentException{
        int i, j, count = 0;
        Coordinates coord;
        PlayableCard placedCard = null;
        this.numberNearbyCards = 0;

        //we should throw an exception if there is already a card in those coordinates
        if(disposition.containsKey(xy)){
            throw new IllegalArgumentException();
        }else{
            //add card to disposition
            disposition.put(xy, card);

            //remove elements or objects in corners covered
            for(j = xy.getY()-1; j <= xy.getY()+1; j++){
                for(i = xy.getX()+1; i >= xy.getX()-1; i--){
                    if(i != xy.getX() && j != xy.getY()){
                        coord = new Coordinates(i, j);
                        if(disposition.containsKey(coord)){
                            placedCard = disposition.get(coord);
                            if(placedCard.getCorner(count) != null) {
                                this.numberNearbyCards ++; // updates the number of corners covered by the card
                            }
                            if(placedCard.getCorner(count) != null && !placedCard.getCorner(count).isEmpty()){
                                if(placedCard.getCorner(count).getElement() != null) availableElements.put(placedCard.getCorner(count).getElement(), availableElements.get(placedCard.getCorner(count).getElement())-1);
                                else availableArtifacts.put(placedCard.getCorner(count).getArtifact(), availableElements.get(placedCard.getCorner(count).getElement())-1);

                            }
                        }
                        count += 1;
                    }
                }
            }

            //add elements and objects of newly placed card
            for(count = 0; count < 4; count++){
                if(card.getCorner(count) != null){
                    if(card.getCorner(count).getElement() != null) availableElements.put(card.getCorner(count).getElement(), availableElements.get(card.getCorner(count).getElement())+1);
                    else availableArtifacts.put(card.getCorner(count).getArtifact(), availableArtifacts.get(card.getCorner(count).getArtifact())+1);
                }
            }

            //update available positions
            availablePlaces.remove(xy);
            count = 3;
            for(j = xy.getY()-1; j <= xy.getY()+1; j++) {
                for (i = xy.getX() + 1; i >= xy.getX() - 1; i--) {
                    if (i != xy.getX() && j != xy.getY()) {
                        coord = new Coordinates(i, j);
                        if (!disposition.containsKey(coord) && card.getCorner(count) != null) availablePlaces.add(coord);
                    }
                    count -= 1;
                }
            }

        }

    }

    public void verifyObjective(ObjectiveCard){
        //to be implemented
    }

    public int getNumberElements (Element element) {
        return availableElements.get(element).intValue();
    }

    public int getNumberArtifacts (Artifact artifact) {
        return availableArtifacts.get(artifact).intValue();
    }

    public int getNumberNearbyCards () {
        return this.numberNearbyCards;
    }
}
