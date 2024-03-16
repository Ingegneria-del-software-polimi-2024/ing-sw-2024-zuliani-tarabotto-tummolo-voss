package model.cards;

public abstract class PlayableCard {
    private char id;
    private boolean faceSide;
    private Corner corners[4];

    public void flipCard() {
        this.faceSide = false;
    }
    public Corner getCorner(int index){
        //to be implemented, returns the content of the corner at position "index", returns null if doesn't exist such corner
    }
}
