package model.Points;

import model.enums.Artifact;
import model.enums.Element;

public class ResourcesPoints implements Points{

    private Element element;
    private Artifact artifact;
    private PlacementArea placementArea;

    public int count() {
        if (element != null) {
            return this.placementArea.getNumberElements(element);
        } else {
            return this.placementArea.getNumberArtifcats(artifact);
        }
    }
}
