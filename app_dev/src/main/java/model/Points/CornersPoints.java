package main.java.model.Points;


import main.java.model.placemetArea.PlacementArea;

public class CornersPoints {
    private PlacementArea placementArea;
    public int count () {
        return this.placementArea.getNumberNearbyCards();
    }
}
