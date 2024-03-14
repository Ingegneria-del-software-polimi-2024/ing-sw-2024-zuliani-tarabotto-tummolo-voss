package model.cards;

public abstract class PlayableCard {
    private char id;
    private boolean faceSide;
    private Corner[] corners;


    public void flipCard() {
        this.faceSide = !this.faceSide;
    }

    public void setId (char id) {
        this.id = id;
    }
    public void setFaceSide(boolean faceSide) {
        this.faceSide = faceSide;
    }
    public void setCorners (Corner[] corners) {
        this.corners = corners;
    }
}
