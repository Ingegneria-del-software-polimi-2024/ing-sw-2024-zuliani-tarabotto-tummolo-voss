package main.model.Points;

public class SimplePoints implements Points {
    private int points;

    public void setPoints(int points) {
        this.points = points;
    }

    public int count() {
        return this.points;
    }
}
