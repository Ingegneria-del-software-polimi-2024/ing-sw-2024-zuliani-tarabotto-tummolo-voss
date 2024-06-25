package model.placementArea;


import model.Exceptions.CantPlaceCardException;
import model.Exceptions.KickOutOfGameException;
import model.cards.PlayableCards.PlayableCard;
import model.enums.Artifact;
import model.enums.Element;
import model.objective.Shape;

import java.util.*;

/**
 *the placement area for every player (where you put the card you are playing each turn)
 */
public class PlacementArea {
    private HashMap<Coordinates, PlayableCard> disposition; //the disposition on the table, used for search of patterns
    private HashMap<Artifact, Integer> availableArtifacts; //how many occurrences of that artifact you have, permits to rapidly check for points
    private HashMap<Element, Integer> availableElements; // as Artifacts but with elements
    private List<Coordinates> availablePlaces; //list of available places to put a card in, enable to search more rapidly where you can place cards
    private int numberNearbyCards;

    private ArrayList<PlayableCard> cardsByPlacementOrder;

    private List<Coordinates> unAvailablePlaces;

     /**
     * @return the free positions in which you can add a card
     */
    public List<Coordinates> freePositions(){return availablePlaces;} //returns the free positions


    /**
     *  class constructor
     */
    public PlacementArea() {
        disposition = new LinkedHashMap<>();
        availableArtifacts = new HashMap<>();
        availableElements = new HashMap<>();
        availablePlaces = new ArrayList<>();
        cardsByPlacementOrder = new ArrayList<PlayableCard>();
        unAvailablePlaces = new ArrayList<>();
        availablePlaces.add(new Coordinates(0,0));

        //we initialize availableElements and availableArtifacts to 0
        for(Element el : Element.values()) {
            availableElements.put(el, 0);
        }
        for(Artifact ar : Artifact.values()){
            availableArtifacts.put(ar, 0);
        }
    }

    /**
     * adds a playable card to the placementArea of turnPlayer
     * returns the number of points granted by the playable card
     * @param xy coordinates to add a card in
     * @param card the card to add
     * @return points earned when placing the card
     * @throws CantPlaceCardException
     */
    public int addCard(Coordinates xy, PlayableCard card) throws CantPlaceCardException{
        int i, j, count = 0;
        Coordinates coord;
        PlayableCard cardOnTable;
        numberNearbyCards = 0;
        //we should throw an exception if there is already a card in those coordinates or if it can't be placed
        if(contain(disposition, xy) || !contain(availablePlaces, xy))
            throw new CantPlaceCardException(xy, card);
        //we should throw an exception if the card can't be placed due to its restrictions
        if(!canBePlaced(card))
            throw new CantPlaceCardException(xy, card);

        //update available positions and get the one we are using
        try {
            xy = updateAvailablePlaces(xy, card);
        }catch (IllegalArgumentException e){
            throw new CantPlaceCardException(xy, card);
        }
        //add card to disposition
        disposition.put(xy, card);

        //add card to the list ordered by placement time
        cardsByPlacementOrder.add(card);

        //remove elements or objects in corners covered
        for(j = xy.getY()-1; j <= xy.getY()+1; j++) {
            for (i = xy.getX() + 1; i >= xy.getX() - 1; i--) {
                if (i != xy.getX() && j != xy.getY()) {
                    //if there is a card at position (i,j)...

                    coord = new Coordinates(i, j).areContainedIn(disposition.keySet());

                    if (coord != null) {
                        //...I will get it
                        cardOnTable = disposition.get(coord);
                        //counting the number of nearby cards for objectives
                        numberNearbyCards++;
                        //if the corner is covered by another card we set isAvailable = false
                        if(cardOnTable.getCorner(count) != null){ cardOnTable.getCorner(count).setIsAvailable();}
                        //removing covered elements/artifacts
                        if (cardOnTable.getCorner(count) != null && !cardOnTable.getCorner(count).isEmpty()) {
                            if (cardOnTable.getCorner(count).getElement() != null)
                                availableElements.put(cardOnTable.getCorner(count).getElement(), availableElements.get(cardOnTable.getCorner(count).getElement()) - 1);
                            else
                                availableArtifacts.put(cardOnTable.getCorner(count).getArtifact(), availableArtifacts.get(cardOnTable.getCorner(count).getArtifact()) - 1);
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

        //we return the number of points given by the card if the card is facing up else we return 0(no points are given by the back of the card)
        if(card.getFaceSide()){
            return card.countPoints(this);
        } else {return 0;}

    }

    /**
     *
     * @param card the card being placed
     * @return TRUE if the constraints limiting the card placement of the card @card are satisfied, else returns FALSE
     */
    public boolean canBePlaced(PlayableCard card) {
        Map<Element, Integer> constraints = card.getPlacementConstraint();
        //if there are no constraints the card can be placed
        if(constraints == null) return true;
        if(!card.getFaceSide()) return true;
        //if constraints are present...
        for(Element e : constraints.keySet())
            if(constraints.get(e) > availableElements.get(e))
                return false;
        return true;
    }

    /**
     * performs the same control as the previous function except for the fact that it checks the
     * placement constraint even if the card is turned
     * @param card
     * @return
     */
    public boolean tuiCanBePlaced(PlayableCard card) {
        Map<Element, Integer> constraints = card.getPlacementConstraint();
        //if there are no constraints the card can be placed
        if(constraints == null) return true;
        //if constraints are present...
        for(Element e : constraints.keySet())
            if(constraints.get(e) > availableElements.get(e))
                return false;
        return true;
    }

    /**
     * allows the turnPlayer to place the starting card
     * @param starterCard the card to be placed
     */
    public void addCard(PlayableCard starterCard) throws KickOutOfGameException {
        Coordinates xy = new Coordinates(0,0);//availablePlaces.get(0); //
        Coordinates coord;
        //update available positions
        try {
            coord = updateAvailablePlaces(xy, starterCard);
        }catch(IllegalArgumentException e){
            throw new KickOutOfGameException();
        }
        //add card to disposition
        disposition.put(coord, starterCard);
        //add card to the list ordered by placement time
        cardsByPlacementOrder.add(starterCard);


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

    /**
     * Counts the occurrences of a shape objective. Returns zero if an error occurs during the computation
     * @param shape the shape of the objective to be verified
     * @param element the list containing the elements in order corresponding to the cards of the objective
     * @return the occurrences of the shape "shape"
     */
    public int verifyObjective(Shape shape, List<Element> element){
        //if i don't have cards it's useless to count
        if(disposition.keySet().isEmpty())
            return 0;
        //declare and initialize
        final Coordinates ORIGIN = new Coordinates(0,0);
        Coordinates tmpCoordinates;
        boolean found;
        int counter = 0;
        ArrayList<PlayableCard> countedCards = new ArrayList<PlayableCard>();
        PlacementAreaIterator coordinatesIterator = new PlacementAreaIterator(disposition, shape);

        //iterate on the cards on the table (the way of iterating depends on the shape of the obj)
        while(coordinatesIterator.hasNext()){

            PlayableCard cardOnTable = disposition.get(coordinatesIterator.current());

            //check if already counted that card for the same obj and if the element of the reference card matches
            if(cardOnTable != null && !countedCards.contains(cardOnTable) && !coordinatesIterator.current().equals(ORIGIN) && cardOnTable.getBlockedElement().equals(element.get(0))){
                found = true;
                //now check if the surroundings cards matches the shape and the element of the obj (and they're not already counted)
                int i = 0;
                for(Coordinates offset : shape.getCoordinates()){
                    i += 1;
                    tmpCoordinates = coordinatesIterator.current().sum(offset).areContainedIn(disposition.keySet());

                    if(tmpCoordinates == null || tmpCoordinates.equals(ORIGIN) || !disposition.get(tmpCoordinates).getBlockedElement().equals(element.get(i)) || countedCards.contains(disposition.get(tmpCoordinates))){
                        found = false;
                        break;
                    }

                }

                //if the previous cycle has found an objective satisfied, we can count and add all the previously
                // checked cards to the countedCards list
                if(found){
                    counter += 1;

                    for(Coordinates x : shape.getCoordinates()){

                        tmpCoordinates = coordinatesIterator.current().sum(x).areContainedIn(disposition.keySet());
                        if(tmpCoordinates!= null)
                            countedCards.add(disposition.get(tmpCoordinates));
                        else return 0;
                    }
                }

            }
            coordinatesIterator.next();
        }
        return counter;
    }

    /**
     * when a new card is placed we call this method to update the number of available resources
     * @param card the card being placed
     */
    private void addResourcesOfNewCard(PlayableCard card ) {
        //add elements and objects of newly placed card
        for(int count = 0; count < 4; count++){
            if(card.getCorner(count) != null){
                if(card.getCorner(count).getElement() != null) {availableElements.put(card.getCorner(count).getElement(), availableElements.get(card.getCorner(count).getElement())+1);}
                else if(card.getCorner(count).getArtifact() != null) {availableArtifacts.put(card.getCorner(count).getArtifact(), availableArtifacts.get(card.getCorner(count).getArtifact())+1);}
            }
        }
    }

    /**
     * after placing a card we update the available places to put a new card
     * returns the coordinates removed from the list of those available
     * in this way we avoid to over-create Coordinates type objects
     * @param xy the position occupied by the newly placed card
     * @param card the card being placed
     * @return xy if the operation ended as expected
     * @throws IllegalArgumentException if the coordinates xy are not contained in the availablePlaces list
     */
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
                    if(card.getCorner(count) == null){ //if a card has a null corner then that position will automatically be unavailable
                        unAvailablePlaces.add(new Coordinates(i, j));
                        //if the position was previously added to the available ones, we now remove it and put it in unavailable
                        if(coord.areContainedIn(availablePlaces) != null) availablePlaces.remove(coord.areContainedIn(availablePlaces));
                    }
                    else if (card.getCorner(count) != null && coord.areContainedIn(disposition.keySet()) == null && coord.areContainedIn(availablePlaces) == null && coord.areContainedIn(unAvailablePlaces) == null)
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

    public Coordinates testWrapper(Coordinates xy, PlayableCard card) throws IllegalArgumentException {
        return updateAvailablePlaces(xy, card);
    }





    ///////////////// GETTER METHODS ////////////////////////////////////////////////////////////

    /**
     *
     * @return an array list of playable cards ordered by their placement order
     */
    public List<PlayableCard> getCardsByPlacementOrder() {
        return (List<PlayableCard>) cardsByPlacementOrder.clone();
    }

    /**
     *
     * @param artifact the type of artifact to count
     * @return the number of artifacts "artifact" in the Placement Are
     */
    public int getNumberArtifacts(Artifact artifact){return availableArtifacts.get(artifact);}

    /**
     *
     * @param element the type of element to count
     * @return the numbers of elements "element" in the Placement Area
     */
    public int getNumberElements(Element element){return availableElements.get(element);}

    /**
     * returns the number of every artifact visible in the PlacementArea
     * @return a Hashmap containing the couples (artifact, numberOfThatArtifacts)
     */
    public HashMap<Artifact, Integer> getAllArtifactsNumber(){
        HashMap<Artifact, Integer> retCopy;
        retCopy = new HashMap<Artifact, Integer>(availableArtifacts);
        return retCopy;
    }

    /**
     * returns the number of every element visible in the PlacementArea
     * @return the Hashmap containing the couples (element, numberOfThatElements)
     */
    public HashMap<Element, Integer> getAllElementsNumber(){
        HashMap<Element, Integer> retCopy;
        retCopy = availableElements;
        return retCopy;
    }

    /**
     * counts the number of corners covered by the newly placed card
     * @return the number of surrounding cards (whith respect to a card that has just been placed)
     */
    public int getNumberNearbyCards(){return numberNearbyCards;}




    ///////////////// FOR TESTING PURPOSES ONLY /////////////////////////////////////////////////
//    public void printAvailablePlaces() {
//        for(Coordinates c : availablePlaces ) {
//            System.out.println("(" + c.getX() + "; " + c.getY() + ")");
//        }
//    }
//    public void printDisposition() {
//        System.out.println("––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
//        //printing elements
//        System.out.println("elements: mushrooms " + this.getNumberElements(Element.mushrooms));
//        System.out.println("          animals " + this.getNumberElements(Element.animals));
//        System.out.println("          insects " + this.getNumberElements(Element.insects));
//        System.out.println("          vegetals " + this.getNumberElements(Element.vegetals));
//        //printing artifacts
//        System.out.println("objects: feather " + this.getNumberArtifacts(Artifact.feather));
//        System.out.println("         ink " + this.getNumberArtifacts(Artifact.ink));
//        System.out.println("         paper " + this.getNumberArtifacts(Artifact.paper));
//        System.out.println("***\t\t***\t\t***\t\t***\t\t***\t\t***\t\t***\t\t***\t\t***");
//        for (Coordinates c : disposition.keySet()) {
//            System.out.println("Card: " + disposition.get(c).getId() + " || Coordinates: (" + c.getX() + ";" + c.getY() + ")");
//        }
//        System.out.println("––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
//    }

    //TODO this MUST BE SET PROTECTED
    public HashMap<Coordinates, PlayableCard> getDisposition() { return disposition;}
    public HashMap<Artifact, Integer> getAvailableArtifacts() {return availableArtifacts;}
    public HashMap<Element, Integer> getAvailableElements() {return availableElements;}
    //TODO: convert all List to ArrayList
    public List<Coordinates> getAvailablePlaces() {return availablePlaces;}

    private boolean contain(HashMap<Coordinates, PlayableCard> disposition, Coordinates coord){
        for(Coordinates x : disposition.keySet()){
            if (x.equals(coord))
                return true;
        }
        return false;
    }
    private boolean contain(List<Coordinates> list, Coordinates coordinates){
        for (Coordinates x : list){
            if(x.equals(coordinates))
                return true;
        }
        return false;
    }
}
