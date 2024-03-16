package model.Points;

import sun.security.ec.point.Point;

public class SimplePoints implements Points {
    private int points;

    public void setPoints(int points) {
        this.points = points;
    }

    public int count() {
        return this.points;
    }
}
