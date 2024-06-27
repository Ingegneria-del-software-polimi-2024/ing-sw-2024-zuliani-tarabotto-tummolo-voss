package model.objective;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.enums.Artifact;
import model.enums.Element;
import model.placementArea.*;

import java.util.HashMap;




/**
 * objective based on the number of artifacts present on the placementArea, if tris = 0 then artifact variable value will
 * contain  the artifact type to count, else artifact should be null (and should be ignored), we should count
 * the  repetitions of 3 artifacts
 */
@JsonTypeName("ArtifactObjective")
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
     * This method counts the objective points based on the placementArea.
     * If tris is false, it returns twice the number of artifacts of the specified type.
     * If tris is true, it returns three times the minimum count of any artifact.
     *
     * @param placementArea the disposition to find objectives in
     * @return the number of points related to this objective
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

    /**
     * This method returns the element of the objective.
     * In this class, it always returns null.
     *
     * @return the element
     */
    @Override
    public Element getElement() {
        return null;
    }

    /**
     * This method returns the artifact of the objective.
     *
     * @return the artifact
     */
    @Override
    public Artifact getArtifact() {
        return artifact;
    }

    /**
     * This method returns the shape of the objective.
     * In this class, it always returns null.
     *
     * @return the shape
     */
    @Override
    public Shape getShape() {
        return null;
    }

}
