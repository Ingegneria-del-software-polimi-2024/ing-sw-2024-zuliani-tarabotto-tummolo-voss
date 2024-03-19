package model.PointsStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.enums.*;
import model.placementArea.*;

@JsonTypeName("ResourcePoints")
public class ResourcePoints implements Points {
    private int points;
    private PlacementArea placementArea;
    @Override
    public int count() {
        if (element != null) {
            return this.placementArea.getNumberElements(element);
        } else {
            return this.placementArea.getNumberArtifacts(artifact);
        }
    }


    @JsonProperty("element")
    Element element;
    @JsonProperty("artifact")
    Artifact artifact;


    @Override
    public int getPoints() {
        return points;
    }

    public Artifact getArtifacts() {
        return artifact;
    }
}
