package model.placementArea;


import model.cards.PlayableCards.PlayableCard;
import model.cards.PlayableCards.StarterCard;
import model.enums.Artifact;
import model.enums.Element;
import model.objective.Shape;

import java.util.ArrayList;
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
    public List<Coordinates> freePositions(){return availablePlaces;} //returns the free positions
    private int numberNearbyCards;

//adds a playable card to the placementArea
    public void addCard(Coordinates xy, PlayableCard card) throws IllegalArgumentException{
        int i, j, count = 0;
        Coordinates coord;
        PlayableCard cardOnTable;
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
                            cardOnTable = disposition.get(coord);

                            if (cardOnTable.getCorner(count) != null) {//updates the number of cards covered by the placed card
                                numberNearbyCards ++;
                            }
                            if(cardOnTable.getCorner(count) != null && !cardOnTable.getCorner(count).isEmpty()){
                                if(cardOnTable.getCorner(count).getElement() != null) availableElements.put(cardOnTable.getCorner(count).getElement(), availableElements.get(cardOnTable.getCorner(count).getElement())-1);
                                else availableArtifacts.put(cardOnTable.getCorner(count).getArtifact(), availableArtifacts.get(cardOnTable.getCorner(count).getArtifact())-1);

                            }
                        }
                        count += 1;
                    }
                }
            }

            //add elements and objects of newly placed card
            for(count = 0; count < 4 && card.isFaceSide(); count++) {
                if (card.getCorner(count) != null) {
                    if (card.getCorner(count).getElement() != null)
                        availableElements.put(card.getCorner(count).getElement(), availableElements.get(card.getCorner(count).getElement()) + 1);
                    else if (card.getCorner(count).getArtifact() != null)
                        availableArtifacts.put(card.getCorner(count).getArtifact(), availableArtifacts.get(card.getCorner(count).getArtifact()) + 1);
                }
            }
            if(!card.isFaceSide()) availableElements.put(card.getBlockedElement(), availableElements.get(card.getBlockedElement())+1);

            //update available positions
            availablePlaces.remove(xy);
            count = 3;
            for(j = xy.getY()-1; j <= xy.getY()+1; j++) {
                for (i = xy.getX() + 1; i >= xy.getX() - 1; i--) {
                    if (i != xy.getX() && j != xy.getY()) {
                        coord = new Coordinates(i, j);
                        if (!disposition.containsKey(coord) && card.getCorner(count) != null)
                            availablePlaces.add(coord);
                    }
                    count -= 1;
                }
            }
        }

    }

//adds the starter card in position (0,0)
    public void addCard(StarterCard firstCard){
        //add card to disposition
        disposition.put(new Coordinates(0,0), firstCard);

        //add free positions
        int count = 0;
        for(int j = 1; j >= -1; j--)
            for (int i = -1; i <= 1; i++)
                if(i != 0 && j != 0)
                    if(firstCard.getCorner(count) != null)
                        availablePlaces.add(new Coordinates(i,j));




        //add elements to list

        if(firstCard.isFaceSide())
            for(Element e : firstCard.getBlockedElements())
                availableElements.put(e, availableElements.get(e)+1);

        for(count = 0; count <= 3; count++)
            if(firstCard.getCorner(count) != null && firstCard.getCorner(count).getElement() != null)
                    availableElements.put(firstCard.getCorner(count).getElement(), availableElements.get(firstCard.getCorner(count).getElement()));
    }

//returns the NUMBER OF COMBINATION of the shape "shape"
    public int verifyObjective(Shape shape, Element element){
        //declare and initialize
        boolean found;
        int counter = 0;
        ArrayList<PlayableCard> countedCards = new ArrayList<PlayableCard>();
        PlacementAreaIterator coordinatesIterator = new PlacementAreaIterator(disposition, shape);
        Element complementarElement;
        if(shape.equals(Shape.ASCENDINGDIAGONAL) || shape.equals(Shape.DESCENDINGDIAGONAL)) complementarElement = element;
        else complementarElement = element.calculateComplementar();
        //iterate on the cards on the table (the way of iterating depends on the shape of the obj)
        while(coordinatesIterator.hasNext()){
            PlayableCard cardOnTable = disposition.get(coordinatesIterator.current());
            //check if already counted that card for the same obj and if the element of the reference card matches
            if(!countedCards.contains(cardOnTable) && disposition.get(coordinatesIterator.current()).getBlockedElement().equals(complementarElement)){
                found = true;
                //now check if the surroundings cards matches the shape and the element of the obj (and they're not already counted)
                for(Coordinates offset : shape.getCoordinates()){
                    if(!disposition.get(coordinatesIterator.current().sum(offset)).getBlockedElement().equals(element) && !countedCards.contains(disposition.get(coordinatesIterator.current().sum(offset)))){
                        found = false;
                        break;
                    }
                }
                //if the previous cycle has found an objective satisfied, we can count and add all the previously checked cards to the countedCards list
                if(found){
                    counter += 1;
                    for(Coordinates x : shape.getCoordinates()) countedCards.add(disposition.get(coordinatesIterator.current().sum(x)));
                }
                coordinatesIterator.next();
            }
        }
        return counter;
    }

//returns the number of artifacts "artifact" in the Placement Area
    public int getNumberArtifacts(Artifact artifact){return availableArtifacts.get(artifact);}

//returns the numbers of elements "element" in the Placement Area
    public int getNumberElements(Element element){return availableElements.get(element);}

//returns a Hashmap containing the couples (artifact, numberOfThatArtifacts)
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

//returns the number of surrounding cards (whith respect to a card that has just been placed)
    public int getNumberNearbyCards(){return numberNearbyCards;}

}
