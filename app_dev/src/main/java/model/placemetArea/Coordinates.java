package main.java.model.placemetArea;

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

}
