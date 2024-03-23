package model.placementArea;

import model.cards.PlayableCards.PlayableCard;
import model.cards.PlayableCards.StarterCard;
import model.enums.Artifact;
import model.enums.Element;
//import model.objective.Shape;

import java.util.ArrayList;
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

    public PlacementArea() {
        disposition = new HashMap<>();
        availableArtifacts = new HashMap<>();
        availableElements = new HashMap<>();
        availablePlaces = new ArrayList<>();
        Coordinates oo;
        availablePlaces.add(oo = new Coordinates(0,0));

        //we initialize availableElements and availableArtifacts to 0
        for(Element el : Element.values()) {
            availableElements.put(el, 0);
        }
        for(Artifact ar : Artifact.values()){
            availableArtifacts.put(ar, 0);
        }
    }



    //adds a playable card to the placementArea
    //returns the number of points granted by the playable card
    public int addCard(Coordinates xy, PlayableCard card) throws IllegalArgumentException{
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

                            if (placedCard.getCorner(count) != null) {
                                //increments the number of cards covered by the placed card
                                numberNearbyCards ++;
                            }
                            if(placedCard.getCorner(count) != null && !placedCard.getCorner(count).isEmpty()){
                                if(placedCard.getCorner(count).getElement() != null) availableElements.put(placedCard.getCorner(count).getElement(), availableElements.get(placedCard.getCorner(count).getElement())-1);
                                else availableArtifacts.put(placedCard.getCorner(count).getArtifact(), availableArtifacts.get(placedCard.getCorner(count).getArtifact())-1);

                            }
                        }
                        count += 1;
                    }
                }
            }
            //if front face side then we check for new resources in the corners of the card
            //if back face side we add the only blocked element contained in the center of the card
            if(card.getFaceSide()){
                addResourcesOfNewCard(card);
            } else {availableElements.put(card.getBlockedElement(), availableElements.get(card.getBlockedElement()) + 1);}


            //update available positions
            updateAvailablePlaces(xy, card);
        }
        //we return the number of points given by the card if the card is facing up else we return 0(no points are given by the back of the card)
        if(card.getFaceSide()){
            return card.countPoints(this);
        } else {return 0;}

    }

    //method specific for the player starting card
    public void addCard(PlayableCard starterCard) {
        int i, j, count = 0;
        Coordinates xy = new Coordinates(0,0);
        Coordinates coord;
        //add card to disposition
        disposition.put(xy, starterCard);

        //update available positions
        updateAvailablePlaces(xy, starterCard);

        //if front face side we add the resources present in the corners and the blocked elements
        //if back face side we add the elements present in each corner of the back side
        if(starterCard.getFaceSide()) {
            addResourcesOfNewCard(starterCard);
            for (Element el : starterCard.getBlockedElements()) {
                availableElements.put(el, availableElements.get(el) + 1);
            }
        } else {
            for(Element el : starterCard.getBackFaceCorners()) {
                availableElements.put(el, availableElements.get(el) + 1);
            }
        }

    }

    //when a new card is placed we call this method to update the number of available resources
    private void addResourcesOfNewCard(PlayableCard card ) {
        //add elements and objects of newly placed card
        for(int count = 0; count < 4; count++){
            if(card.getCorner(count) != null){
                if(card.getCorner(count).getElement() != null) {availableElements.put(card.getCorner(count).getElement(), availableElements.get(card.getCorner(count).getElement())+1);}
                else if(card.getCorner(count).getArtifact() != null) {availableArtifacts.put(card.getCorner(count).getArtifact(), availableArtifacts.get(card.getCorner(count).getArtifact())+1);}
            }
        }
    }
    //after placing a card we update the available places to put a new card
    private void updateAvailablePlaces(Coordinates xy, PlayableCard card) {
        //update available positions
        availablePlaces.remove(xy);
        int count = 3;
        for(int j = xy.getY()-1; j <= xy.getY()+1; j++) {
            for (int i = xy.getX() + 1; i >= xy.getX() - 1; i--) {
                if (i != xy.getX() && j != xy.getY()) {
                    Coordinates coord = new Coordinates(i, j);
                    if (!disposition.containsKey(coord) && card.getCorner(count) != null) availablePlaces.add(coord);
                }
                count -= 1;
            }
        }
    }


//returns the number of artifacts "artifact" in the Placement Area
    public int getNumberArtifacts(Artifact artifact){return availableArtifacts.get(artifact);}

//returns the numbers of elements "element" in the Placement Area
    public int getNumberElements(Element element){return availableElements.get(element);}


    //returns the number of corners covered by the last placed card
    public int getNumberNearbyCards(){
        return numberNearbyCards;
    }

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

}
