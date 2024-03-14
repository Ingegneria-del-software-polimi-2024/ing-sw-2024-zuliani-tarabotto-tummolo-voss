package model.Points;

import model.enums.Artifact;
import model.enums.Element;

public class ResourcesPoints implements Points{

    private Element element;
    private Artifact artifact;

    public int count(PlacementArea placementArea) {
        if (element != null) {
            return placementArea.availableElements.get(element) + 1;
        } else {
            return placementArea.availableArtifacts.get(artifact) + 1;
        }
    }
}
