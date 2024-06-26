package model.Exceptions;

/**
 * The encoding of the source is the following (the same used in the whole code)
 * 1- Gold Deck
 * 2- Open Gold (0)
 * 3- Open Gold (1)
 * 4- Resource Deck
 * 5- Open Resources (0)
 * 6- Open Resources (1)
 */
public class EmptyCardSourceException extends Exception{
    private final int source;

    public EmptyCardSourceException(int indx){
        this.source = indx;
    }
    public int getIndx() {
        return source;
    }
}
