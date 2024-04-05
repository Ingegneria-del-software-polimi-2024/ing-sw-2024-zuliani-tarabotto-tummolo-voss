package model.placementArea;


import model.cards.PlayableCards.PlayableCard;
import model.cards.PlayableCards.StarterCard;
import model.enums.Artifact;
import model.enums.Element;
import model.objective.Shape;

import java.util.*;


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

    public PlacementArea() {
        disposition = new HashMap<>();
        availableArtifacts = new HashMap<>();
        availableElements = new HashMap<>();
        availablePlaces = new ArrayList<>();
        availablePlaces.add(new Coordinates(0,0));

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
        PlayableCard cardOnTable;
        numberNearbyCards = 0;

        //we should throw an exception if there is already a card in those coordinates
        if(disposition.containsKey(xy)){
            throw new IllegalArgumentException();
        }else{
            //update available positions and get the one we are using
            xy = updateAvailablePlaces(xy, card);

            //add card to disposition
            disposition.put(xy, card);

            //remove elements or objects in corners covered
            for(j = xy.getY()-1; j <= xy.getY()+1; j++){
                for(i = xy.getX()+1; i >= xy.getX()-1; i--){
                    if(i != xy.getX() && j != xy.getY()){
                        //if there is a card at position (i,j)...
                        coord = new Coordinates(i,j).areContainedIn(disposition.keySet());
                        if(coord != null){
                            //...I will get it
                            cardOnTable = disposition.get(coord);
                            //counting the number of nearby cards for objectives
                            numberNearbyCards ++;
                            //removing covered elements/artifacts
                            if(cardOnTable.getCorner(count) != null && !cardOnTable.getCorner(count).isEmpty()){
                                if(cardOnTable.getCorner(count).getElement() != null) availableElements.put(cardOnTable.getCorner(count).getElement(), availableElements.get(cardOnTable.getCorner(count).getElement())-1);
                                else availableArtifacts.put(cardOnTable.getCorner(count).getArtifact(), availableArtifacts.get(cardOnTable.getCorner(count).getArtifact())-1);
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
        }
        //we return the number of points given by the card if the card is facing up else we return 0(no points are given by the back of the card)
        if(card.getFaceSide()){
            return card.countPoints(this);
        } else {return 0;}

    }

    //method specific for the player starting card
    public void addCard(PlayableCard starterCard) {
        Coordinates xy = new Coordinates(0,0);//availablePlaces.get(0); //
        Coordinates coord;
        //update available positions
        coord = updateAvailablePlaces(xy, starterCard);

        //add card to disposition
        disposition.put(coord, starterCard);


        //if front face side we add the resources present in the corners and the blocked elements
        //if back face side we add the elements present in each corner of the back side
        if(starterCard.getFaceSide()) {
            addResourcesOfNewCard(starterCard);
            for (Element el : starterCard.getBlockedElements())
                availableElements.put(el, availableElements.get(el) + 1);

        } else
            for(Element el : starterCard.getBackFaceCorners())
                availableElements.put(el, availableElements.get(el) + 1);




    }

//returns the NUMBER OF COMBINATION of the shape "shape"
    public int verifyObjective(Shape shape, Element element){
        //declare and initialize
        final Coordinates ORIGIN = new Coordinates(0,0);
        Coordinates tmpCoordinates;
        boolean found;
        int counter = 0;
        ArrayList<PlayableCard> countedCards = new ArrayList<PlayableCard>();
        PlacementAreaIterator coordinatesIterator = new PlacementAreaIterator(disposition, shape);
        Element complementarElement;

        if(shape.equals(Shape.ASCENDINGDIAGONAL) || shape.equals(Shape.DESCENDINGDIAGONAL)) {
            complementarElement = element;
        }else complementarElement = element.calculateComplementar();
        //iterate on the cards on the table (the way of iterating depends on the shape of the obj)

        while(coordinatesIterator.hasNext()){
            PlayableCard cardOnTable = disposition.get(coordinatesIterator.current());

            //check if already counted that card for the same obj and if the element of the reference card matches
            if(!countedCards.contains(cardOnTable) && !coordinatesIterator.current().equals(ORIGIN) && cardOnTable.getBlockedElement().equals(complementarElement)){
                found = true;
                //now check if the surroundings cards matches the shape and the element of the obj (and they're not already counted)
                for(Coordinates offset : shape.getCoordinates()){
                    tmpCoordinates = coordinatesIterator.current().sum(offset).areContainedIn(disposition.keySet());
                    if((tmpCoordinates != null && !disposition.get(tmpCoordinates).getBlockedElement().equals(element) && !countedCards.contains(tmpCoordinates)) || (tmpCoordinates != null && tmpCoordinates.equals(ORIGIN))){
                        found = false;
                        break;
                    }

                }
                //if the previous cycle has found an objective satisfied, we can count and add all the previously checked cards to the countedCards list
                if(found){
                    counter += 1;
                    for(Coordinates x : shape.getCoordinates()){
                        tmpCoordinates = coordinatesIterator.current().sum(x).areContainedIn(disposition.keySet());
                        countedCards.add(disposition.get(tmpCoordinates));
                    }
                }

            }
            coordinatesIterator.next();
        }
        return counter;
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
    //returns the coordinates removed from the list of those available
    //in this way we avoid to over-create Coordinates type objects
    private Coordinates updateAvailablePlaces(Coordinates xy, PlayableCard card) throws IllegalArgumentException {
        Coordinates coord;
        int count = 3;
        //checking if nearby positions can be added to the availablePlaces list
        for (int j = xy.getY() - 1; j <= xy.getY() + 1; j++) {
            for (int i = xy.getX() + 1; i >= xy.getX() - 1; i--) {
                if (i != xy.getX() && j != xy.getY()) {
                    coord = new Coordinates(i, j);
                    //if the placed card has a corner there, there is no card already placed there,
                    // they are not already inside availablePlaces, the coordinates can be added to the list
                    if (card.getCorner(count) != null && coord.areContainedIn(disposition.keySet()) == null && coord.areContainedIn(availablePlaces) == null)
                        availablePlaces.add(new Coordinates(i, j));
                    count -= 1;
                }
            }
        }

        //removig the coordinates from the availablePlaces list and returning them to be used
        coord = xy.areContainedIn(availablePlaces);
        if (coord != null){
            availablePlaces.remove(coord);
            return coord;
        }
        throw new IllegalArgumentException();
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

//returns a Hashmap containing the couples (element, numberOfThatArtifacts)
    public HashMap<Element, Integer> getAllElementsNumber(){
        HashMap<Element, Integer> retCopy;
        retCopy = availableElements;
        return retCopy;
    }

//returns the number of surrounding cards (whith respect to a card that has just been placed)
    public int getNumberNearbyCards(){return numberNearbyCards;}

    public void printDisposition() {
        System.out.println("––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
        //printing elements
        System.out.println("elements: mushrooms "+this.getNumberElements(Element.mushrooms));
        System.out.println("          animals "+this.getNumberElements(Element.animals));
        System.out.println("          insects "+this.getNumberElements(Element.insects));
        System.out.println("          vegetals "+this.getNumberElements(Element.vegetals));
        //printing artifacts
        System.out.println("objects: feather "+this.getNumberArtifacts(Artifact.feather));
        System.out.println("         ink "+this.getNumberArtifacts(Artifact.ink));
        System.out.println("         paper "+this.getNumberArtifacts(Artifact.paper));
        System.out.println("***\t\t***\t\t***\t\t***\t\t***\t\t***\t\t***\t\t***\t\t***");
        for(Coordinates c : disposition.keySet()){
            System.out.println("Card: " + disposition.get(c).getId() + " || Coordinates: (" + c.getX() + ";" + c.getY() + ")");
        }
        System.out.println("––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");

    }
    public void printAvailablePlaces() {
        for(Coordinates c : availablePlaces ) {
            System.out.println("(" + c.getX() + "; " + c.getY() + ")");
        }
    }

}
