package model.objective;

import model.enums.Artifact;
import model.placementArea.*;

import java.util.HashMap;

// objective based on the number of artifacts present on the placementArea, if tris = 0 then artifact variable value will
// contain  the artifact type to count, else artifact should be null (and should be ignored), we should count
// the  repetitions of 3 artifacts
public class ArtifactObjective  implements Objective{

    Artifact artifact;
    boolean tris;
    public int countObjectivePoints(PlacementArea placementArea){
        HashMap<Artifact, Integer> map;
        if(!tris) {
            return 2*(placementArea.getNumberArtifacts(artifact)/2);
        }else{
            map = placementArea.getAllArtifactsNumber();
            return 3 * map.values().stream().min((a, b)-> a<b? a : b).orElse(0);
        }
    }

}
