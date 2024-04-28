package model.Exceptions;

public class EmptyCardSourceException extends Exception{
    public EmptyCardSourceException(String s)
    {
        // Call constructor of parent Exception
        super(s);
    }
}
