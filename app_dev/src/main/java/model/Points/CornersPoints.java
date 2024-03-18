package main.java.model.Points;
import main.java.model.enums.Artifact;
import main.java.model.enums.Element;
import main.java.model.placemetArea.PlacementArea;

public class CornersPoints implements Points {
    private PlacementArea placementArea;

    public CornersPoints (PlacementArea placementArea) {
        this.placementArea = placementArea;
    }
    public int count () {
        return this.placementArea.getNumberNearbyCards();
    }
}
