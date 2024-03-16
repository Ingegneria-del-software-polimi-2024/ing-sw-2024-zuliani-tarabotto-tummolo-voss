package model.deckFactory;

///////////// CLASS ///////////
public abstract class PlayableCard {
    private char id;
    private Corner[4] corners;
    private boolean frontFace;

    public void flipCard() {
        this.frontFace = false;
    }

    ;
}
