package model.placementArea;

import java.io.Serializable;
import java.util.Collection;

/**
 * the class coordinates facilitates the use of a cartesian plane as model for the Placement Area of the players
 */
public class Coordinates implements Serializable {
    private final int x;
    private final int y;

    /**
     * constructor of the coordinate (a,b)
     * @param a x value
     * @param b y value
     */
    public Coordinates(int a, int b){
        x = a;
        y = b;
    }

    /**
     * gets x value of (x,y) coordinate
     * @return x
     */
    public int getX(){
        return x;
    }

    /**
     * gets y value of (x,y) coordinate
     * @return y
     */
    public int getY(){
        return y;
    }

    /**
     * sums (x, y) + (a, b)=(x+a, y+b)
     * @param a the coordinate to sum
     * @return this+a
     */
    public Coordinates sum(Coordinates a){return new Coordinates(x+a.getX(), y+a.getY());}
    public boolean equals(Coordinates xy){
        return xy.getX() == x && xy.getY() == y;
    }

    /**
     * selects the top-left coordinate
     * @param a the coordinate to compare with
     * @return the coordinate which has highest y value or in alternative lower x (top-left)
     */
    public Coordinates topLeftFirst(Coordinates a){
        if(y > a.getY()) return this;
        else if (y == a.getY()) {
            if(x < a.getX()) return this;
            else return a;
        }
        else return a;
    }

    /**
     * selects the top-right coordinate
     * @param a the coordinate to compare with
     * @return the coordinate which has the highest y value or in alternative higher x (top-right)
     */
    public Coordinates topRightFirst(Coordinates a){
        if(y > a.getY()) return this;
        else if (y == a.getY()) {
            if(x > a.getX()) return this;
            else return a;
        }
        else return a;
    }

    /**
     * selects the top-right coordinate
     * @param a the coordinate to compare with
     * @return the coordinate which has the lowest y value or in alternative higher x (bottom-right)
     */
    public Coordinates bottomRightFirst(Coordinates a){
        if(y < a.getY()) return this;
        else if (y == a.getY()) {
            if(x > a.getX()) return this;
            else return a;
        }
        else return a;
    }

    /**
     * selects the bottom-left coordinate
     * @param a the coordinate to compare with
     * @return the coordinate which has the lowest y value or in alternative lowe x (bottom-left)
     */
    public Coordinates bottomLeftFirst(Coordinates a){
        if(y < a.getY()) return this;
        else if (y == a.getY()) {
            if(x < a.getX()) return this;
            else return a;
        }
        else return a;
    }


    /**
     * returns the element present in the collection if equal to this
     * @param collection set to search in this element
     * @return element of the collection
     */
    public Coordinates areContainedIn(Collection<Coordinates> collection){
        for(Coordinates c : collection)
            if(this.equals(c))
                return c;
        return null;
    }
}
