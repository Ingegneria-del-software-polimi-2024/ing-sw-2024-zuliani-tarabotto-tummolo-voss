package model.placementArea;

import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;
import model.objective.Shape;

import java.util.Comparator;
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
    private int numberNearbyCards;
    public List<Coordinates> freePositions(){return availablePlaces;} //returns the free positions

//adds a playable card to the placementArea
    public void addCard(PlayableCard card, Coordinates xy) throws IllegalArgumentException{
        int i, j, count = 0;
        Coordinates coord;
        PlayableCard placedCard;
        numberNearbyCards = 0;

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

                            if (placedCard.getCorner(count) != null) {//updates the number of cards covered by the placed card
                                numberNearbyCards ++;
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

    //work in progress, returns the NUMBER OF COMBINATION of the shape "shape"
    public int verifyObjective(Shape shape, Element element){
        for(Coordinates c : disposition.keySet().stream().sorted(Comparator.comparing((a) -> {return a;}))/*serve un metodo per ordinare la lista*/){
            if(disposition.get(c).getElement().equals(element)){
                //starts looking the contiguous cards for the pattern
            }
        }
    }

//returns the number of artifacts "artifact" in the Placement Area
    public int getNumberArtifacts(Artifact artifact){return availableArtifacts.get(artifact);}

//returns the numbers of elements "element" in the Placement Area
    public int getNumberElements(Element element){return availableElements.get(element);}

//returns an Hashmap containing the couples (artifact, numberOfThatArtifacts)
    public HashMap<Artifact, Integer> getAllArtifactsNumber(){
        HashMap<Artifact, Integer> retCopy;
        retCopy = availableArtifacts;
        return retCopy;
    }

//returns an Hashmap containing the couples (element, numberOfThatArtifacts)
    public HashMap<Element, Integer> getAllElementsNumber(){
        HashMap<Element, Integer> retCopy;
        retCopy = availableElements;
        return retCopy;
    }
    public int getNumberNearbyCards(){
        return numberNearbyCards;
    }

}
