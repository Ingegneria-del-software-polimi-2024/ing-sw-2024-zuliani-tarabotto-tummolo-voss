package main.java.model.Points;

import main.java.model.enums.Artifact;
import main.java.model.enums.Element;
import main.java.model.placemetArea.*;

public class ResourcesPoints implements Points{

    private Element element;
    private Artifact artifact;
    private PlacementArea placementArea;

    public ResourcesPoints (Element element, Artifact artifact, PlacementArea placementArea) {
        this.element = element;
        this.artifact = artifact;
        this.placementArea = placementArea;
    }
    public int count() {
        if (element != null) {
            return this.placementArea.getNumberElements(element);
        } else {
            return this.placementArea.getNumberArtifacts(artifact);
        }
    }
}
