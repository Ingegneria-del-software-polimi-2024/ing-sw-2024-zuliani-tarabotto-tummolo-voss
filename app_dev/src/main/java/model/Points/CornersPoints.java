package model.Points;

public class CornersPoints {
    private PlacementArea placementArea;
    public int count () {
        return this.placementArea.getNumberNearbyCards();
    }
}
