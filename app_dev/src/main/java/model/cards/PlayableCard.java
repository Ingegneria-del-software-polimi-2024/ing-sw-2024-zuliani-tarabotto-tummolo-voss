package model.cards;

public abstract class PlayableCard {
    private char id;
    private boolean faceSide;
    private Corner[4] corners;

    public void flipCard() {
        this.faceSide = false;
    }
}
