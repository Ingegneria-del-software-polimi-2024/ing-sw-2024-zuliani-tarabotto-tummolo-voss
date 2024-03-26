package model.placementArea;

import java.util.Collection;

/*
the class coordinates facilitates the use of a cartesian plane as model for the Placement Area of the players
*/
public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int a, int b){
        x = a;
        y = b;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

//sums (x, y) + (a, b)=(x+a, y+b)
    public Coordinates sum(Coordinates a){return new Coordinates(x+a.getX(), y+a.getY());}
    public boolean equals(Coordinates xy){
        return xy.getX() == x && xy.getY() == y;
    }

    //returns the coordinate which has highest y value or in alternative lower x (top-left)
    public Coordinates topLeftFirst(Coordinates a){
        if(y > a.getY()) return this;
        else if (y == a.getY()) {
            if(x < a.getX()) return this;
            else return a;
        }
        else return a;
    }

    //returns the coordinate which has the highest y value or in alternative higher x (top-right)
    public Coordinates topRightFirst(Coordinates a){
        if(y > a.getY()) return this;
        else if (y == a.getY()) {
            if(x > a.getX()) return this;
            else return a;
        }
        else return a;
    }

    //returns the coordinate which has the lowest y value or in alternative higher x (bottom-right)
    public Coordinates bottomRightFirst(Coordinates a){
        if(y < a.getY()) return this;
        else if (y == a.getY()) {
            if(x > a.getX()) return this;
            else return a;
        }
        else return a;
    }

    //returns the coordinate which has the lowest y value or in alternative lowe x (bottom-left)
    public Coordinates bottomLeftFirst(Coordinates a){
        if(y < a.getY()) return this;
        else if (y == a.getY()) {
            if(x < a.getX()) return this;
            else return a;
        }
        else return a;
    }


    //returns the exact object of the collection equal to the coordinates if present, else returns null
    public Coordinates areContainedIn(Collection<Coordinates> collection){
        for(Coordinates c : collection)
            if(this.equals(c))
                return c;
        return null;
    }
}
