package main.java.model.cards;

public abstract class PlayableCard implements Card{
    private char id;
    private boolean faceSide;
    private Corner[] corners;


    public void flipCard() {
        this.faceSide = !this.faceSide;
    }

    public Corner getCorner(int index) {
        return corners[index];
    }

    public abstract int countPoints();


}
