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
    /**
     * The source of the exception
     */
    private final int source;

    /**
     * Constructs a new EmptyCardSourceException with the specified source.
     * @param indx The source of the exception.
     */
    public EmptyCardSourceException(int indx){
        this.source = indx;
    }

    /**
     * @return The source of the exception.
     */
    public int getIndx() {
        return source;
    }
}
