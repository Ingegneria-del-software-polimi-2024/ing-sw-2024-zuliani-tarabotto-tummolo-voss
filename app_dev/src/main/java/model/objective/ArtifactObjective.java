package model.objective;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.*;

import java.util.HashMap;


/**
 * The type Artifact objective.
 */
@JsonTypeName("ArtifactObjective")
/**
 * objective based on the number of artifacts present on the placementArea, if tris = 0 then artifact variable value will
 * contain  the artifact type to count, else artifact should be null (and should be ignored), we should count
 * the  repetitions of 3 artifacts
 */
public class ArtifactObjective  implements Objective{
    /**
     * The Artifact.
     */
    @JsonProperty("artifact")
    Artifact artifact;
    /**
     * The Tris.
     */
    @JsonProperty("tris")
    boolean tris;

    /**
     *
     * @param placementArea the disposition to find objectives in
     * @return the number of points rellated to this objective
     */
    public int countObjectivePoints(PlacementArea placementArea){
        HashMap<Artifact, Integer> map;
        if(!tris) {
            return 2*(placementArea.getNumberArtifacts(artifact)/2);
        }else{
            map = placementArea.getAllArtifactsNumber();

            return 3 * map.values().stream().reduce((a, b) -> a<b? a : b ).orElse(0);
        }
    }

    @Override
    public void printObjective() {
        System.out.println("type: ArtifactObjective");
        if (this.artifact != null) System.out.println(this.artifact.toString());
        System.out.println("tris: " + this.tris);
    }

    @Override
    public Element getElement() {
        return null;
    }

    @Override
    public Artifact getArtifact() {
        return artifact;
    }

    @Override
    public Shape getShape() {
        return null;
    }

}
