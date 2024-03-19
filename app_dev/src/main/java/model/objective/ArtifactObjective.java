package model.objective;

import model.enums.Artifact;
import model.placemetArea.*;

import java.util.HashMap;

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
