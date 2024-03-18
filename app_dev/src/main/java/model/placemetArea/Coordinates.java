package model.placemetArea;

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

    public boolean equals(Coordinates xy){
        return xy.getX() == x && xy.getY() == y;
    }

    //returns the coordinate which has highest y value or in alternative lower x (from top-left)
    public Coordinates compare(Coordinates a){
        if(y > a.getY()) return this;
        else if (y == a.getY()) {
            if(x < a.getX()) return this;
            else return a;
        }
        else return a;
    }
}
