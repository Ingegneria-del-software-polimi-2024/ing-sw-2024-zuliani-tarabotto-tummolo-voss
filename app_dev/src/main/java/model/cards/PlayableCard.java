package model.cards;

public abstract class PlayableCard {
    private char id;
    private boolean faceSide;
    private Corner[] corners;


    public void flipCard() {
        this.faceSide = !this.faceSide;
    }

}
