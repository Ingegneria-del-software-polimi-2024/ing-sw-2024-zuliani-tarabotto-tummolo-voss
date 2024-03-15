package main.model.Points;


import main.model.placemetArea.PlacementArea;

public class CornersPoints {
    private PlacementArea placementArea;
    public int count () {
        return this.placementArea.getNumberNearbyCards();
    }
}
